package jdbms.sql;

import java.sql.SQLException;

import jdbms.sql.data.SQLData;
import jdbms.sql.data.query.SelectQueryOutput;
import jdbms.sql.errors.util.ErrorMessages;
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
import jdbms.sql.util.HelperClass;

public class DBMSConnector {
	private final ErrorMessages errorMessages;
	private SQLData data;
	public DBMSConnector(final String fileType, final String filePath)
			throws SQLException {
		HelperClass.registerInitialStatements();
		try {
			data = new SQLData(fileType, filePath);
		} catch (final FileFormatNotSupportedException e) {
			throw new SQLException(e.getMessage());
		}
		errorMessages = new ErrorMessages();
	}
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
		throw new SQLException();
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
		}  catch (final Exception e) {
			throw new SQLException(errorMessages.getInternalError());
		}
	}
}
