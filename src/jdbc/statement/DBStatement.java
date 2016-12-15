package jdbc.statement;

import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jdbc.connections.DBConnection;
import jdbc.results.DataResultSet;
import jdbc.results.util.SelectOutputConverter;
import jdbms.sql.DBMSConnector;
import jdbms.sql.data.query.SelectQueryOutput;

public class DBStatement implements Statement {
	private static final String CLOSED_MESSAGE
	= "Couldn't %s Because"
			+ " The Statement is Closed";
	private static final String  SYNTAX_ERROR_MESSAGE
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
	public DBStatement(final DBMSConnector connector,
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
	public void addBatch(final String sql) throws SQLException {
		logger.debug("Requested Adding Batch");
		if (isClosed) {
			logger.info(String.format(CLOSED_MESSAGE, "Add Batch"));
			throw new SQLException(String.format(CLOSED_MESSAGE, "Add Batch"));
		}
		logger.debug("Batch Added: " + sql);
		commands.add(sql);
	}

	@Override
	public void clearBatch() throws SQLException {
		logger.debug("Requested Clearing Batch");
		if (isClosed) {
			throw new SQLException(String.format(CLOSED_MESSAGE, "Clear Batch"));
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
	public boolean execute(final String sql) throws SQLException {
		logger.debug("Requested Executing SQL Command");
		if (isClosed) {
			logger.info(String.format(CLOSED_MESSAGE, "Execute SQL"));
			throw new SQLException(String.format(CLOSED_MESSAGE,
					"Execute SQL"));
		}
		if (dbmsConnector.interpretQuery(sql)) {
			resultSet = new DataResultSet(this);
			final SelectOutputConverter converter = new SelectOutputConverter();
			final SelectQueryOutput output = dbmsConnector.executeQuery(sql);
			logger.debug(String.format(EXECUTED_SUCCESSFULLY, "Query", sql)
					+ String.format(QUERY_RESULT, output.getData().size()));
			converter.convert(resultSet, output);
			currentResult = -1;
			if (output.getData().isEmpty()) {
				return false;
			}
			return true;
		} else if (dbmsConnector.interpretUpdate(sql)) {
			currentResult = dbmsConnector.executeUpdate(sql);
			logger.debug(String.format(EXECUTED_SUCCESSFULLY, "Update", sql)
					+ String.format(UPDATE_RESULT, currentResult));
			return false;
		} else {
			logger.error(String.format(SYNTAX_ERROR_MESSAGE, sql, "Command"));
			throw new SQLException("Syntax Error");
		}
	}

	@Override
	public int[] executeBatch() throws SQLException {
		logger.debug("Requested Executing SQL Batch");
		if (isClosed) {
			throw new SQLException();
		}

		final int size = commands.size();
		final int[] updateCounts = new int[size];
		for (int i = 0; i < size; i++) {
			final String sql = commands.peek();
			if (dbmsConnector.interpretUpdate(sql)) {
				commands.poll();
				updateCounts[i] = dbmsConnector.executeUpdate(sql);
				currentResult = updateCounts[i];
				logger.debug(String.format(EXECUTED_SUCCESSFULLY, "Update", sql)
						+ String.format(UPDATE_RESULT, currentResult));
			} else if (dbmsConnector.interpretUpdate(commands.peek())) {
				resultSet = new DataResultSet(this);
				final SelectOutputConverter converter = new SelectOutputConverter();
				commands.poll();
				final SelectQueryOutput output = dbmsConnector.executeQuery(sql);
				logger.debug(String.format(EXECUTED_SUCCESSFULLY, "Query", sql)
						+ String.format(QUERY_RESULT, output.getData().size()));
				converter.convert(resultSet, output);
				updateCounts[i] = SUCCESS_NO_INFO;
				currentResult = -1;
			} else {
				logger.error(String.format(SYNTAX_ERROR_MESSAGE, sql,
						"Command"));
				throw new BatchUpdateException("Syntax Error", updateCounts);
			}
		}
		return updateCounts;
	}

	@Override
	public ResultSet executeQuery(final String sql) throws SQLException {
		logger.debug("Requested Executing SQL Query");
		if (isClosed) {
			logger.info(String.format(CLOSED_MESSAGE, "Execute SQL"));
			throw new SQLException(String.format(CLOSED_MESSAGE,
					"Execute SQL"));
		}

		if (!dbmsConnector.interpretQuery(sql)) {
			logger.error(String.format(SYNTAX_ERROR_MESSAGE, sql, "Query"));
			throw new SQLException("Syntax Error");
		} else {
			currentResult = -1;
			resultSet = new DataResultSet(this);
			final SelectOutputConverter converter = new SelectOutputConverter();
			final SelectQueryOutput output = dbmsConnector.executeQuery(sql);
			converter.convert(resultSet, output);
			logger.debug(String.format(EXECUTED_SUCCESSFULLY, "Query", sql)
					+ String.format(QUERY_RESULT, output.getData().size()));
			return resultSet;
		}
	}

	@Override
	public int executeUpdate(final String sql) throws SQLException {
		logger.debug("Requested Executing SQL Update");
		if (isClosed) {
			throwException("Execute Update");
		}

		if (!dbmsConnector.interpretUpdate(sql)) {
			logger.error(String.format(SYNTAX_ERROR_MESSAGE, sql, "Update"));
			throw new SQLException("Syntax Error");
		} else {
			currentResult = dbmsConnector.executeUpdate(sql);
			logger.debug(String.format(EXECUTED_SUCCESSFULLY, "Update", sql)
					+ String.format(UPDATE_RESULT, currentResult));
			return currentResult;
		}
	}

	@Override
	public Connection getConnection() throws SQLException {

		if (isClosed) {
			throwException("Get Connection");
		}
		return connection;
	}

	@Override
	public ResultSet getResultSet() throws SQLException {

		if (isClosed) {
			throwException("Get ResultSet");
		}
		return this.resultSet;
	}

	@Override
	public int getUpdateCount() throws SQLException {
		if (isClosed) {
			throwException("Get Update Count");
		}
		return currentResult;
	}
	@Override
	public boolean isClosed() throws SQLException {
		return isClosed;
	}
	private void throwException(final String action) throws SQLException {
		logger.info(String.format(CLOSED_MESSAGE, action));
		throw new SQLException(String.format(CLOSED_MESSAGE,
				action));
	}

	@Override
	public boolean isWrapperFor(final Class<?> arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T unwrap(final Class<T> arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void cancel() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clearWarnings() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void closeOnCompletion() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean execute(final String arg0, final int arg1) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean execute(final String arg0, final int[] arg1) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean execute(final String arg0, final String[] arg1) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int executeUpdate(final String arg0, final int arg1) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int executeUpdate(final String arg0, final int[] arg1) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int executeUpdate(final String arg0, final String[] arg1) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getFetchDirection() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getFetchSize() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public ResultSet getGeneratedKeys() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getMaxFieldSize() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getMaxRows() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean getMoreResults() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean getMoreResults(final int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getQueryTimeout() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getResultSetConcurrency() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getResultSetHoldability() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getResultSetType() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isCloseOnCompletion() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isPoolable() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setCursorName(final String arg0) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setEscapeProcessing(final boolean arg0) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setFetchDirection(final int arg0) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setFetchSize(final int arg0) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setMaxFieldSize(final int arg0) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setMaxRows(final int arg0) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setPoolable(final boolean arg0) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setQueryTimeout(final int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}
}