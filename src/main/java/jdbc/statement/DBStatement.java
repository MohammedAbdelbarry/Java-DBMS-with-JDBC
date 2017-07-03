package jdbc.statement;

import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jdbc.connections.DBConnection;
import jdbc.results.DataResultSet;
import jdbc.results.util.SelectOutputConverter;
import jdbms.sql.DBMSConnector;
import jdbms.sql.data.query.SelectQueryOutput;

/**
 * A JDBC Statement Implementation.
 * @author Hisham Osama
 */
public final class DBStatement implements Statement {
    private static final String CLOSED_MESSAGE
            = "Couldn't %s Because"
            + " The Statement is Closed";
    private static final String SYNTAX_ERROR_MESSAGE
            = "Command: \"%s\" is Not a Valid SQL %s";
    private static final String EXECUTED_SUCCESSFULLY
            = "SQL %s: \"%s\" Was Executed Successfully";
    private static final String QUERY_RESULT
            = " %d Rows Were Returned";
    private static final String UPDATE_RESULT
            = " %d Rows Were Updated";
    private DBMSConnector dbmsConnector;
    private Queue<String> commands;
    private boolean isClosed;
    private int currentResult;
    private final DBConnection connection;
    private DataResultSet resultSet;
    private final Logger logger;

    public DBStatement(final
                       DBMSConnector connector,
                       final DBConnection connection) {
        logger = LogManager.getLogger(DBStatement.class);
        this.dbmsConnector = connector;
        this.connection = connection;
        commands = new LinkedList<>();
        isClosed = false;
        currentResult = -1;
        resultSet = new DataResultSet(this);
        logger.debug("Statement Created Successfully");
    }

    @Override
    public void addBatch(final String sql)
            throws SQLException {
        logger.debug("Requested Adding Batch");
        if (isClosed) {
            throwClosedStatementException("Add Batch");
        }
        logger.debug("Batch Added: " + sql);
        commands.add(sql);
    }

    @Override
    public void clearBatch()
            throws SQLException {
        logger.debug("Requested Clearing Batch");
        if (isClosed) {
            throwClosedStatementException("Clear Batch");
        }
        logger.debug("Batch Was Cleared");
        commands.clear();
    }

    @Override
    public void close() throws SQLException {
        logger.debug("Statement Was Closed");
        isClosed = true;
        commands = null;
        dbmsConnector = null;
        if (resultSet != null) {
            resultSet.close();
        }
        resultSet = null;
    }

    @Override
    public boolean execute(final String sql)
            throws SQLException {
        logger.debug("Requested"
                + " Executing SQL Command");
        if (isClosed) {
            throwClosedStatementException("Execute"
                    + " SQL Command");
        }
        if (dbmsConnector.interpretQuery(sql)) {
            resultSet = new DataResultSet(this);
            final SelectOutputConverter converter
                    = new SelectOutputConverter();
            final SelectQueryOutput output;
            try {
                output = dbmsConnector.
                        executeQuery(sql);
            } catch (final SQLException e) {
                logger.error("Failed to "
                                + "Execute Query \"" + sql
                                + "\". Cause: "
                                + e.getMessage(), e,
                        e.getCause());
                throw e;
            }
            logSuccessfulQuery(sql,
                    output.getData().size());
            converter.convert(resultSet,
                    output);
            currentResult = -1;
            return !output.getData().isEmpty();
        } else if (dbmsConnector.
                interpretUpdate(sql)) {
            try {
                currentResult = dbmsConnector.
                        executeUpdate(sql);
            } catch (final SQLException e) {
                logger.error("Failed to Execute"
                                + " Update \"" + sql
                                + "\". Cause: "
                                + e.getMessage(), e,
                        e.getCause());
                throw e;
            }
            logSuccessfulUpdate(sql,
                    currentResult);
            return false;
        } else {
            logger.error(String.format(
                    SYNTAX_ERROR_MESSAGE,
                    sql, "Command"));
            throw new SQLException("Syntax"
                    + " Error");
        }
    }

    @Override
    public int[] executeBatch()
            throws SQLException {
        logger.debug("Requested Executing"
                + " SQL Batch");
        if (isClosed) {
            throwClosedStatementException(
                    "Execute Batch");
        }
        final int size = commands.size();
        final int[] updateCounts = new int[size];
        for (int i = 0; i < size; i++) {
            final String sql = commands.peek();
            if (dbmsConnector.interpretUpdate(sql)) {
                commands.poll();
                try {
                    updateCounts[i]
                            = dbmsConnector.executeUpdate(sql);
                } catch (final SQLException e) {
                    logger.error("Failed to"
                                    + " Execute Update \""
                                    + sql
                                    + "\". Cause: "
                                    + e.getMessage(), e
                            , e.getCause());
                    throw e;
                }
                currentResult = updateCounts[i];
                logSuccessfulUpdate(sql, currentResult);
            } else if (dbmsConnector.
                    interpretQuery(
                            commands.peek())) {
                resultSet = new DataResultSet(this);
                final SelectOutputConverter
                        converter
                        = new SelectOutputConverter();
                commands.poll();
                final SelectQueryOutput output;
                try {
                    output = dbmsConnector.
                            executeQuery(sql);
                } catch (final SQLException e) {
                    logger.error("Failed to"
                                    + " Execute Query \""
                                    + sql
                                    + "\". Cause: "
                                    + e.getMessage(), e,
                            e.getCause());
                    throw e;
                }
                converter.convert(resultSet,
                        output);
                logSuccessfulQuery(sql,
                        output.getData().size());
                updateCounts[i] = SUCCESS_NO_INFO;
                currentResult = -1;
            } else {
                logger.error(String.format(
                        SYNTAX_ERROR_MESSAGE,
                        sql,
                        "Command"));
                throw new BatchUpdateException(
                        "Syntax Error",
                        updateCounts);
            }
        }
        logger.debug("Batch"
                + " Executed "
                + "Successfully: "
                + Arrays.toString(updateCounts));
        return updateCounts;
    }

    @Override
    public ResultSet executeQuery(
            final String sql)
            throws SQLException {
        logger.debug("Requested"
                + " Executing SQL"
                + " Query");
        if (isClosed) {
            throwClosedStatementException(
                    "Execute Query");
        }
        if (!dbmsConnector.
                interpretQuery(sql)) {
            throwSyntaxErrorException(
                    "Query", sql);
        } else {
            currentResult = -1;
            resultSet
                    = new DataResultSet(this);
            final SelectOutputConverter converter
                    = new SelectOutputConverter();
            final SelectQueryOutput output;
            try {
                output = dbmsConnector.
                        executeQuery(sql);
            } catch (final SQLException e) {
                logger.error("Failed to"
                                + " Execute Query \""
                                + sql
                                + "\". Cause: "
                                + e.getMessage(), e,
                        e.getCause());
                throw e;
            }
            converter.convert(resultSet,
                    output);
            logSuccessfulQuery(sql,
                    output.getData().size());
            return resultSet;
        }
        return null;
    }

    @Override
    public int executeUpdate(final String
                                     sql) throws SQLException {
        logger.debug("Requested Executing"
                + " SQL Update");
        if (isClosed) {
            throwClosedStatementException(
                    "Execute Update");
        }

        if (!dbmsConnector.interpretUpdate(sql)) {
            throwSyntaxErrorException("Update",
                    sql);
        } else {
            try {
                currentResult = dbmsConnector.
                        executeUpdate(sql);
            } catch (final SQLException e) {
                logger.error("Failed to"
                                + " Execute Update \""
                                + sql
                                + "\". Cause: "
                                + e.getMessage(),
                        e,
                        e.getCause());
                throw e;
            }
            logSuccessfulUpdate(sql,
                    currentResult);
            return currentResult;
        }
        return 0;
    }

    @Override
    public Connection getConnection()
            throws SQLException {

        if (isClosed) {
            throwClosedStatementException(
                    "Get Connection");
        }
        return connection;
    }

    @Override
    public ResultSet getResultSet()
            throws SQLException {

        if (isClosed) {
            throwClosedStatementException(
                    "Get ResultSet");
        }
        return this.resultSet;
    }

    @Override
    public int getUpdateCount()
            throws SQLException {
        if (isClosed) {
            throwClosedStatementException(
                    "Get Update Count");
        }
        return currentResult;
    }

    @Override
    public boolean isClosed()
            throws SQLException {
        return isClosed;
    }

    private void throwClosedStatementException(
            final String action)
            throws SQLException {
        logger.info(String.format(
                CLOSED_MESSAGE, action));
        throw new SQLException(
                String.format(CLOSED_MESSAGE,
                        action));
    }

    private void logSuccessfulQuery(final
                                    String sql,
                                    final int rows) {
        logger.debug(String.format(
                EXECUTED_SUCCESSFULLY,
                "Query", sql)
                + String.format(QUERY_RESULT,
                rows));
    }

    private void logSuccessfulUpdate(final String
                                             sql, final int rows) {
        logger.debug(String.format(
                EXECUTED_SUCCESSFULLY,
                "Update", sql)
                + String.format(
                UPDATE_RESULT,
                rows));
    }

    private void throwSyntaxErrorException(final
                                           String type,
                                           final String sql)
            throws SQLException {
        logger.error(String.format(
                SYNTAX_ERROR_MESSAGE,
                sql, type));
        throw new SQLException("Syntax"
                + " Error");
    }

    @Override
    public boolean isWrapperFor(final
                                Class<?> arg0) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T unwrap(final
                        Class<T> arg0)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void cancel()
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clearWarnings()
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void closeOnCompletion()
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean execute(final String
                                   arg0, final int arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean execute(final String
                                   arg0, final int[] arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean execute(final String
                                   arg0, final String[] arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int executeUpdate(final String
                                     arg0, final int arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int executeUpdate(final String
                                     arg0, final int[] arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int executeUpdate(final String
                                     arg0, final
                             String[] arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getFetchDirection()
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getFetchSize()
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public ResultSet getGeneratedKeys()
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getMaxFieldSize()
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getMaxRows()
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getMoreResults()
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getMoreResults(
            final int arg0)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getQueryTimeout()
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getResultSetConcurrency()
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getResultSetHoldability()
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getResultSetType()
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public SQLWarning getWarnings()
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isCloseOnCompletion()
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isPoolable()
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setCursorName(final
                              String arg0)
            throws SQLException {
        throw new UnsupportedOperationException();

    }

    @Override
    public void setEscapeProcessing(final
                                    boolean arg0)
            throws SQLException {
        throw new UnsupportedOperationException();

    }

    @Override
    public void setFetchDirection(final
                                  int arg0)
            throws SQLException {
        throw new UnsupportedOperationException();

    }

    @Override
    public void setFetchSize(final
                             int arg0)
            throws SQLException {
        throw new UnsupportedOperationException();

    }

    @Override
    public void setMaxFieldSize(final
                                int arg0)
            throws SQLException {
        throw new UnsupportedOperationException();

    }

    @Override
    public void setMaxRows(final
                           int arg0)
            throws SQLException {
        throw new UnsupportedOperationException();

    }

    @Override
    public void setPoolable(final
                            boolean arg0)
            throws SQLException {
        throw new UnsupportedOperationException();

    }

    @Override
    public void setQueryTimeout(
            final int arg0)
            throws SQLException {
        throw new UnsupportedOperationException();
    }
}