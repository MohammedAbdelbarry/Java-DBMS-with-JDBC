package jdbms.sql;

import java.sql.SQLException;

import jdbms.sql.data.SQLData;
import jdbms.sql.data.query.SelectQueryOutput;
import jdbms.sql.errors.util.ErrorMessages;
import jdbms.sql.exceptions.AllColumnsDroppingException;
import jdbms.sql.exceptions.ColumnAlreadyExistsException;
import jdbms.sql.exceptions.ColumnListTooLargeException;
import jdbms.sql.exceptions.ColumnNotFoundException;
import jdbms.sql.exceptions.DatabaseAlreadyExistsException;
import jdbms.sql.exceptions.DatabaseNotFoundException;
import jdbms.sql.exceptions.FailedToDeleteDatabaseException;
import jdbms.sql.exceptions.FailedToDeleteTableException;
import jdbms.sql.exceptions.FileFormatNotSupportedException;
import jdbms.sql.exceptions.InvalidDateFormatException;
import jdbms.sql.exceptions.RepeatedColumnException;
import jdbms.sql.exceptions.TableAlreadyExistsException;
import jdbms.sql.exceptions.TableNotFoundException;
import jdbms.sql.exceptions.TypeMismatchException;
import jdbms.sql.exceptions.ValueListTooLargeException;
import jdbms.sql.exceptions.ValueListTooSmallException;
import jdbms.sql.parsing.parser.StringNormalizer;
import jdbms.sql.parsing.statements.InitialStatement;
import jdbms.sql.parsing.statements.util.InitialStatementFactory;
import jdbms.sql.util.ClassRegisteringHelper;

/**
 * The interface between the DBMS
 * and the jdbc.
 * @author Mohammed Abdelbarry
 */
public class DBMSConnector {
    /**
     * An object that represents the
     * error messages to be printed to the
     * console.
     */
    private final ErrorMessages errorMessages;
    /**
     * The {@link SQLData}
     */
    private SQLData data;

    /**
     * Constructs a new
     * @param fileType The type of the back-end parser.
     * @param filePath The path of the user's data.
     * @throws SQLException If the type of the back-end parser was not
     *                      supported.
     */
    public DBMSConnector(final String fileType, final String filePath)
            throws SQLException {
        ClassRegisteringHelper.registerInitialStatements();
        try {
            data = new SQLData(fileType, filePath);
        } catch (final FileFormatNotSupportedException e) {
            throw new SQLException(e.getMessage());
        }
        errorMessages = new ErrorMessages();
    }

    /**
     * Executes an SQL update.
     * @param sql The sql update to be executed.
     * @return The update count of the sql update.
     * @throws SQLException If the sql command is not a valid SQL update or if
     *                      the command is semantically wrong.
     */
    public int executeUpdate(final String sql)
            throws SQLException {
        final InitialStatement statement = parse(sql);
        if (statement.getNumberOfUpdates() == -1) {
            throw new SQLException(String.format(errorMessages.
                    getStatementIsNot(), sql, "a Query"));
        }
        act(statement);
        return statement.getNumberOfUpdates();
    }

    /**
     * Executes an SQL Query.
     * @param sql The sql query to be executed.
     * @return The {@link SelectQueryOutput} representing the output of the
     * select query.
     * @throws SQLException If the sql command is not a valid SQL query or if
     *                      the command is semantically wrong.
     */
    public SelectQueryOutput executeQuery(final String sql)
            throws SQLException {
        final InitialStatement statement = parse(sql);
        if (statement.getNumberOfUpdates() != -1) {
            throw new SQLException(String.format(errorMessages.
                    getStatementIsNot(), sql, "an Update"));
        }
        act(statement);
        return statement.getQueryOutput();
    }

    /**
     * Checks if the SQL parser can
     * interpret the given sql command
     * as a valid SQL Update.
     * @param sql The input SQL commmand to be checked.
     * @return True if the given SQL command is a valid SQL update and false
     * otherwise.
     */
    public boolean interpretUpdate(final String sql) {
        try {
            final InitialStatement statement = parse(sql);
            if (statement.getNumberOfUpdates() == -1) {
                return false;
            }
        } catch (final Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Checks if the SQL parser can
     * interpret the given sql command
     * as a valid SQL query.
     * @param sql The input SQL commmand to be checked.
     * @return True if the given SQL command is a valid SQL query and false
     * otherwise.
     */
    public boolean interpretQuery(final String sql) {
        try {
            final InitialStatement statement = parse(sql);
            if (statement.getNumberOfUpdates() != -1) {
                return false;
            }
        } catch (final Exception e) {
            return false;
        }
        return true;
    }

    private InitialStatement parse(String sql)
            throws SQLException {
        if (!sql.trim().endsWith(";")) {
            sql = sql.trim() + ";";
        }
        final String normalizedInput = normalizeInput(sql);
        if (normalizedInput == null) {
            throw new SQLException(errorMessages
                    .getSyntaxError());
        }
        for (final String key : InitialStatementFactory.
                getInstance().getRegisteredStatements()) {
            final InitialStatement statement =
                    InitialStatementFactory.
                            getInstance().createStatement(key);
            boolean interpreted;
            try {
                interpreted = statement.interpret(normalizedInput);
            } catch (final Exception e) {
                continue;
            }
            if (interpreted) {
                return statement;
            }
        }
        throw new SQLException(errorMessages.getSyntaxError());
    }

    private String normalizeInput(final String sql)
            throws SQLException {
        final StringNormalizer normalizer = new StringNormalizer();
        String normalizedOutput;
        try {
            normalizedOutput = normalizer.normalizeCommand(sql);
            return normalizedOutput;
        } catch (final Exception e) {
            throw new SQLException(
                    errorMessages.getSyntaxError());
        }
    }

    private void act(final InitialStatement statement)
            throws SQLException {
        try {
            statement.act(data);
        } catch (final ColumnNotFoundException e) {
            throw new SQLException(
                    String.format(errorMessages.getNotFound(),
                            "Column", e.getMessage()), e.getCause());
        } catch (final TypeMismatchException e) {
            throw new SQLException(errorMessages.getTypeMismatch(), e);
        } catch (final TableNotFoundException e) {
            throw new SQLException(String.format(
                    errorMessages.getNotFound(),
                    "Table", e.getMessage()), e);
        } catch (final ColumnAlreadyExistsException e) {
            throw new SQLException(String.format(errorMessages.
                            getAlreadyExists(),
                    "Column", e.getMessage()), e);
        } catch (final RepeatedColumnException e) {
            throw new SQLException(errorMessages.getRepeatedColumns(), e);
        } catch (final ColumnListTooLargeException e) {
            throw new SQLException(String.format(errorMessages.
                            getColumnList(),
                    errorMessages.getTooLarge()),
                    e);
        } catch (final ValueListTooLargeException e) {
            throw new SQLException(String.format(errorMessages.
                    getValueList(), errorMessages.getTooSmall()), e);
        } catch (final ValueListTooSmallException e) {
            throw new SQLException(String.format(errorMessages.
                    getValueList(), errorMessages.getTooLarge()), e);
        } catch (final TableAlreadyExistsException e) {
            throw new SQLException(String.format(errorMessages.
                            getAlreadyExists(),
                    "Table", e.getMessage()), e);
        } catch (final DatabaseAlreadyExistsException e) {
            throw new SQLException(String.format(errorMessages.
                            getAlreadyExists(),
                    "Database", e.getMessage()), e);
        } catch (final DatabaseNotFoundException e) {
            throw new SQLException(String.format(
                    errorMessages.getNotFound(),
                    "Database", e.getMessage()), e);
        } catch (final FailedToDeleteDatabaseException e) {
            throw new SQLException(String.format(errorMessages.
                            getFailedToDelete(), "Database",
                    e.getMessage()), e);
        } catch (final FailedToDeleteTableException e) {
            throw new SQLException(String.format(errorMessages.
                            getFailedToDelete(), "Table",
                    e.getMessage()), e);
        } catch (final InvalidDateFormatException e) {
            throw new SQLException(String.format(errorMessages.
                            getInvalidDate(),
                    e.getMessage()), e);
        } catch (final AllColumnsDroppingException e) {
            throw new SQLException(errorMessages.getDroppingAllColumnsError(), e);
        } catch (final Exception e) {
            throw new SQLException(errorMessages.getInternalError(), e);
        }
    }
}
