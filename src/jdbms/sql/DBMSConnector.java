package jdbms.sql;

import java.sql.SQLException;

import jdbms.sql.data.SQLData;
import jdbms.sql.data.query.SelectQueryOutput;
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

public class DBMSConnector {
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
		if (statement.getQueryOutput() != null) {
			throw new SQLException();
		}
		return statement.getNumberOfUpdates();
	}
	public SelectQueryOutput executeQuery(final String sql)
			throws SQLException {
		final InitialStatement statement = parse(sql);
		if (statement.getQueryOutput() == null) {
			throw new SQLException();
		}
		return statement.getQueryOutput();
	}
	public boolean interpretUpdate(final String sql) {
		try {
			final InitialStatement statement = parse(sql);
			if (statement.getQueryOutput() != null) {
				return false;
			}
		} catch (final SQLException e) {
			return false;
		}
		return true;
	}
	public boolean interpretQuery(final String sql) {
		try {
			final InitialStatement statement = parse(sql);
			if (statement.getQueryOutput() == null) {
				return false;
			}
		} catch (final SQLException e) {
			return false;
		}
		return true;	}

	private InitialStatement parse(final String sql)
			throws SQLException {
		final String normalizedInput = normalizeInput(sql);
		if (normalizedInput == null) {
			throw new SQLException();//SYNTAX_ERROR
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
				try {
					statement.act(data);
					return statement;
				} catch (final ColumnNotFoundException e) {
					throw new SQLException();
				} catch (final TypeMismatchException e) {
					throw new SQLException();
				} catch (final TableNotFoundException e) {
					throw new SQLException();
				} catch (final ColumnAlreadyExistsException e) {
					throw new SQLException();
				} catch (final RepeatedColumnException e) {
					throw new SQLException();
				} catch (final ColumnListTooLargeException e) {
					throw new SQLException();
				} catch (final ValueListTooLargeException e) {
					throw new SQLException();
				} catch (final ValueListTooSmallException e) {
					throw new SQLException();
				} catch (final TableAlreadyExistsException e) {
					throw new SQLException();
				} catch (final DatabaseAlreadyExistsException e) {
					throw new SQLException();
				} catch (final DatabaseNotFoundException e) {
					throw new SQLException();
				} catch (final FailedToDeleteDatabaseException e) {
					throw new SQLException();
				} catch (final FailedToDeleteTableException e) {
					throw new SQLException();
				} catch (final InvalidDateFormatException e) {
					throw new SQLException();
				}  catch (final Exception e) {
					throw new SQLException();
				}
			}
		}
		throw new SQLException();
	}
	private String normalizeInput(final String sql) {
		final StringNormalizer normalizer = new StringNormalizer();
		String normalizedOutput;
		try {
			normalizedOutput = normalizer.normalizeCommand(sql);
			return normalizedOutput;
		} catch (final Exception e) {
			return null;
		}
	}
}
