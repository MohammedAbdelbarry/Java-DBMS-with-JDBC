package jdbc.statement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Queue;

import jdbms.sql.DBMSConnector;

public class DBStatement implements Statement {
	private boolean isClosed = false;
	private final Queue<String> commands
	= new LinkedList<>();
	private final DBMSConnector dbmsConnector;

	public DBStatement(final
			DBMSConnector connector) {
		this.dbmsConnector = connector;
	}

	@Override
	public void addBatch(final String sql)
			throws SQLException {
		commands.add(sql);
	}

	@Override
	public void clearBatch()
			throws SQLException {
		commands.clear();
	}

	@Override
	public void close() throws SQLException {
		isClosed = true;
	}

	@Override
	public boolean execute(final String sql)
			throws SQLException {
		if (dbmsConnector.interpretQuery(sql)) {
			dbmsConnector.executeQuery(sql);
			return true;
		} else if (dbmsConnector.interpretUpdate(sql)) {
			dbmsConnector.executeUpdate(sql);
			return false;
		} else {
			throw new SQLException();
		}
	}

	@Override
	public int[] executeBatch()
			throws SQLException {
		final int[] updateCounts;
		final int size = commands.size();
		for (int i = 0; i < size; i++) {

		}
		return null;
	}

	@Override
	public ResultSet executeQuery(final
			String arg0) throws SQLException {
		if (!dbmsConnector.interpretQuery(arg0)) {
			throw new SQLException();
		} else {
			// RETURN RESULT SET
			return null;
		}
	}

	@Override
	public int executeUpdate(final
			String sql) throws SQLException {
		if (!dbmsConnector.interpretUpdate(sql)) {
			throw new SQLException();
		} else {
			return dbmsConnector.executeUpdate(sql);
		}
	}

	@Override
	public Connection getConnection()
			throws SQLException {
		return null;
	}

	@Override
	public ResultSet getResultSet()
			throws SQLException {
		return null;
	}

	@Override
	public int getUpdateCount()
			throws SQLException {
		return 0;
	}

	@Override
	public boolean isClosed()
			throws SQLException {
		return isClosed;
	}

	@Override
	public boolean isWrapperFor(final
			Class<?> arg0)
					throws SQLException {
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
	public boolean execute(final String arg0
			, final String[] arg1)
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
	public int executeUpdate(final
			String arg0, final
			String[] arg1) throws SQLException {
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
	public boolean getMoreResults(final int arg0)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getQueryTimeout() throws SQLException {
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
	public boolean isPoolable() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setCursorName(final String arg0)
			throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setEscapeProcessing(final boolean
			arg0) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setFetchDirection(final int
			arg0) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setFetchSize(final int
			arg0) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setMaxFieldSize(final int
			arg0) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setMaxRows(final int
			arg0) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setPoolable(final boolean
			arg0) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setQueryTimeout(final int
			arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}
}