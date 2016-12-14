package jdbms.sql;

import java.sql.SQLException;
import java.util.Formatter;

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
	static {
		HelperClass.registerInitialStatements();
	}
	private SQLData data;
	public DBMSConnector(final String fileType, final String filePath)
			throws SQLException {
		try {
			data = new SQLData(fileType, filePath);
		} catch (final FileFormatNotSupportedException e) {
			throw new SQLException(e.getMessage());
		}
	}
	public int executeUpdate(final String sql)
			throws SQLException {
		final InitialStatement statement = parse(sql);
		if (statement.getNumberOfUpdates() == -1) {
			throw new SQLException(String.format(ErrorMessages.
					STATEMENT_IS_NOT, sql, "a Query"));
		}
		act(statement);
		return statement.getNumberOfUpdates();
	}
	public SelectQueryOutput executeQuery(final String sql)
			throws SQLException {
		final InitialStatement statement = parse(sql);
		if (statement.getNumberOfUpdates() != -1) {
			throw new SQLException(String.format(ErrorMessages.
					STATEMENT_IS_NOT, sql, "an Update"));
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
			throw new SQLException(ErrorMessages
					.SYNTAX_ERROR);
		}
		for (final String key : InitialStatementFactory.
				getInstance().getRegisteredStatements()) {
			final InitialStatement statement =
					InitialStatementFactory.
					getInstance().createStatement(key);
			boolean interpreted = false;
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
					ErrorMessages.SYNTAX_ERROR);
		}
	}
	private void act(final InitialStatement statement)
			throws SQLException {
		try {
			statement.act(data);
		} catch (final ColumnNotFoundException e) {
			throw new SQLException(
					String.format(ErrorMessages.NOT_FOUND,
					"Column", e.getMessage()), e.getCause());
		} catch (final TypeMismatchException e) {
			throw new SQLException(ErrorMessages.TYPE_MISMATCH, e);
		} catch (final TableNotFoundException e) {
			throw new SQLException(String.format(
					ErrorMessages.NOT_FOUND,
					"Table", e.getMessage()), e);
		} catch (final ColumnAlreadyExistsException e) {
			throw new SQLException(String.format(ErrorMessages.
					ALREADY_EXISTS,
					"Column", e.getMessage()), e);
		} catch (final RepeatedColumnException e) {
			throw new SQLException(ErrorMessages.REPEATED_COLUMNS, e);
		} catch (final ColumnListTooLargeException e) {
			throw new SQLException(String.format(ErrorMessages.
					COLUMN_LIST,
					ErrorMessages.TOO_LARGE),
					e);
		} catch (final ValueListTooLargeException e) {
			throw new SQLException(String.format(ErrorMessages.
					VALUE_LIST, ErrorMessages.TOO_SMALL), e);
		} catch (final ValueListTooSmallException e) {
			throw new SQLException(String.format(ErrorMessages.
					VALUE_LIST, ErrorMessages.TOO_LARGE), e);
		} catch (final TableAlreadyExistsException e) {
			throw new SQLException(String.format(ErrorMessages.
					ALREADY_EXISTS,
					"Talbe", e.getMessage()), e);
		} catch (final DatabaseAlreadyExistsException e) {
			throw new SQLException(String.format(ErrorMessages.
					ALREADY_EXISTS,
					"Database", e.getMessage()), e);
		} catch (final DatabaseNotFoundException e) {
			throw new SQLException(String.format(
					ErrorMessages.NOT_FOUND,
					"Database", e.getMessage()), e);
		} catch (final FailedToDeleteDatabaseException e) {
			throw new SQLException(String.format(ErrorMessages.
					FAILED_TO_DELETE, "Database",
					e.getMessage()), e);
		} catch (final FailedToDeleteTableException e) {
			throw new SQLException(String.format(ErrorMessages.
					FAILED_TO_DELETE, "Table",
					e.getMessage()), e);
		} catch (final InvalidDateFormatException e) {
			throw new SQLException(String.format(ErrorMessages.
					INVALID_DATE,
					e.getMessage()), e);
		}  catch (final Exception e) {
			throw new SQLException(ErrorMessages.INTERNAL_ERROR);
		}
	}
}
