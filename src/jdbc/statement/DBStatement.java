package jdbc.statement;

import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Queue;

import jdbc.connections.DBConnection;
import jdbc.results.DataResultSet;
import jdbc.results.util.SelectOutputConverter;
import jdbms.sql.DBMSConnector;
import jdbms.sql.data.query.SelectQueryOutput;

public class DBStatement implements Statement {

	private final DBMSConnector dbmsConnector;
	private final Queue<String> commands;
	private boolean isClosed;
	private int currentResult;
	private final DBConnection connection;
	private DataResultSet resultSet;

	public DBStatement(final DBMSConnector connector, final DBConnection connection) {
		this.dbmsConnector = connector;
		this.connection = connection;
		commands = new LinkedList<>();
		isClosed = false;
		currentResult = -1;
		resultSet = new DataResultSet(this);
	}

	@Override
	public void addBatch(final String sql) throws SQLException {

		if (isClosed) {
			throw new SQLException();
		}
		commands.add(sql);
	}

	@Override
	public void clearBatch() throws SQLException {

		if (isClosed) {
			throw new SQLException();
		}
		commands.clear();
	}

	@Override
	public void close() throws SQLException {
		isClosed = true;
	}

	@Override
	public boolean execute(final String sql) throws SQLException {

		if (isClosed) {
			throw new SQLException();
		}

		if (dbmsConnector.interpretQuery(sql)) {
			resultSet = new DataResultSet(this);
			final SelectOutputConverter converter = new SelectOutputConverter();
			final SelectQueryOutput output = dbmsConnector.executeQuery(sql);
			converter.convert(resultSet, output);
			currentResult = -1;
			if (output.getData().isEmpty()) {
				return false;
			}
			return true;
		} else if (dbmsConnector.interpretUpdate(sql)) {
			currentResult = dbmsConnector.executeUpdate(sql);
			return false;
		} else {
			throw new SQLException();
		}
	}

	@Override
	public int[] executeBatch() throws SQLException {

		if (isClosed) {
			throw new SQLException();
		}

		final int size = commands.size();
		final int[] updateCounts = new int[size];
		for (int i = 0; i < size; i++) {
			if (dbmsConnector.interpretUpdate(commands.peek())) {
				updateCounts[i] = dbmsConnector.executeUpdate(commands.poll());
				currentResult = updateCounts[i];
			} else if (dbmsConnector.interpretUpdate(commands.peek())) {
				resultSet = new DataResultSet(this);
				final SelectOutputConverter converter = new SelectOutputConverter();
				converter.convert(resultSet, dbmsConnector.executeQuery(commands.peek()));
				updateCounts[i] = SUCCESS_NO_INFO;
				currentResult = -1;
			} else {
				throw new BatchUpdateException();
			}
		}
		return updateCounts;
	}

	@Override
	public ResultSet executeQuery(final String sql) throws SQLException {

		if (isClosed) {
			throw new SQLException();
		}

		if (!dbmsConnector.interpretQuery(sql)) {
			throw new SQLException();
		} else {
			currentResult = -1;
			resultSet = new DataResultSet(this);
			final SelectOutputConverter converter = new SelectOutputConverter();
			converter.convert(resultSet, dbmsConnector.executeQuery(sql));
			return resultSet;
		}
	}

	@Override
	public int executeUpdate(final String sql) throws SQLException {

		if (isClosed) {
			throw new SQLException();
		}

		if (!dbmsConnector.interpretUpdate(sql)) {
			throw new SQLException();
		} else {
			currentResult = dbmsConnector.executeUpdate(sql);
			return currentResult;
		}
	}

	@Override
	public Connection getConnection() throws SQLException {

		if (isClosed) {
			throw new SQLException();
		}
		return connection;
	}

	@Override
	public ResultSet getResultSet() throws SQLException {

		if (isClosed) {
			throw new SQLException();
		}
		return this.resultSet;
	}

	@Override
	public int getUpdateCount() throws SQLException {

		if (isClosed) {
			throw new SQLException();
		}
		return currentResult;
	}

	@Override
	public boolean isClosed() throws SQLException {
		return isClosed;
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