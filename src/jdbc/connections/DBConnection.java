package jdbc.connections;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jdbc.drivers.util.ProtocolConstants;
import jdbc.statement.DBStatement;
import jdbms.sql.DBMSConnector;
/**
 * A JDBC Connection Implementation.
 * @author Ahmed Moustafa El-Naggar
 */
public final class DBConnection
implements Connection {
	private final Logger logger;
	private final DBMSConnector connector;
	private final ArrayList<DBStatement>
	statements;
	private boolean isClosed;
	private final ProtocolConstants
	constants;
	public DBConnection(final String
			url, final String path)
					throws SQLException {
		logger
		= LogManager.
		getLogger(
				DBConnection.class);
		logger.debug(
				"Connection Started:"
						+ " URL("
						+ url
						+ ") File Path("
						+ path
						+ ")");
		constants
		= new ProtocolConstants();
		connector
		= new DBMSConnector(getProtocolName(url), path);
		statements
		= new ArrayList<>();
		isClosed
		= false;
	}

	private String
	getProtocolName(
			final String url) {
		final int start
		= url.indexOf(
				constants.
				getSeparator());
		if (start == -1) {
			return "";
		}
		final String subURL
		= url.substring(
				start + 1,
				url.length());
		return subURL.substring(0,
				subURL.indexOf(
						constants.
						getSeparator()));
	}

	@Override
	public Statement createStatement()
			throws SQLException {
		logger.debug("Statement"
				+ " Requested");
		if (isClosed()) {
			final SQLException ex
			= new SQLException("Connection"
					+ " is Closed");
			logger.error("Failed to"
					+ " Create Statement", ex);
			throw ex;
		}
		final DBStatement newStatement
		= new DBStatement(connector, this);
		statements.add(newStatement);
		return newStatement;
	}

	@Override
	public void close()
			throws SQLException {
		isClosed = true;
		for (final DBStatement
				statement : statements) {
			if (statement != null) {
				statement.close();
			}
		}
		statements.clear();
		logger.debug("Connection"
				+ " Closed");
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
	public void abort(final
			Executor arg0)
					throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void clearWarnings()
			throws SQLException {
		throw new UnsupportedOperationException();

	}


	@Override
	public void commit()
			throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public Array createArrayOf(
			final String arg0,
			final Object[] arg1)
					throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public Blob createBlob()
			throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public Clob createClob()
			throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public NClob createNClob()
			throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public SQLXML createSQLXML()
			throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public Statement createStatement(
			final int arg0,
			final int arg1)
					throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public Statement createStatement(
			final int arg0,
			final int arg1,
			final int arg2)
					throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public Struct createStruct(final
			String arg0,
			final Object[] arg1)
					throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean getAutoCommit()
			throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public String getCatalog()
			throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public Properties getClientInfo()
			throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public String getClientInfo(final
			String arg0)
					throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public int getHoldability()
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public DatabaseMetaData getMetaData()
			throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public int getNetworkTimeout()
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getSchema()
			throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public int getTransactionIsolation()
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, Class<?>>
	getTypeMap() throws
	SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public SQLWarning
	getWarnings()
			throws SQLException {
		throw new UnsupportedOperationException();

	}


	@Override
	public boolean isReadOnly()
			throws
			SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean isValid(
			final int arg0)
					throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public String nativeSQL(final
			String arg0)
					throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public CallableStatement
	prepareCall(final
			String arg0)
					throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public CallableStatement
	prepareCall(final
			String arg0,
			final int arg1,
			final int arg2)
					throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public CallableStatement
	prepareCall(final String
			arg0, final
			int arg1,
			final int arg2,
			final int arg3)
					throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public PreparedStatement
	prepareStatement(
			final String
			arg0) throws
	SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public PreparedStatement
	prepareStatement(final
			String arg0,
			final int arg1)
					throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public PreparedStatement
	prepareStatement(final
			String arg0,
			final int[]
					arg1) throws
	SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public PreparedStatement
	prepareStatement(final
			String arg0,
			final String[]
					arg1) throws
	SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public PreparedStatement
	prepareStatement(final String
			arg0, final int arg1,
			final int arg2)
					throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public PreparedStatement
	prepareStatement(final String arg0,
			final int arg1, final int
			arg2, final int arg3)
					throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void releaseSavepoint(final
			Savepoint arg0)
					throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void rollback()
			throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void rollback(final
			Savepoint arg0)
					throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setAutoCommit(
			final boolean arg0)
					throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setCatalog(final
			String arg0)
					throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setClientInfo(
			final Properties arg0)
					throws SQLClientInfoException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setClientInfo(final
			String arg0, final
			String arg1)
					throws
					SQLClientInfoException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setHoldability(
			final int arg0)
					throws
					SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setNetworkTimeout(
			final Executor arg0,
			final int arg1)
					throws
					SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setReadOnly(final
			boolean arg0)
					throws
					SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public Savepoint
	setSavepoint()
			throws
			SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public Savepoint
	setSavepoint(final
			String arg0)
					throws
					SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setSchema(
			final String
			arg0) throws
	SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setTransactionIsolation(
			final int arg0)
					throws
					SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setTypeMap(final
			Map<String, Class<?>>
	arg0) throws SQLException {
		throw new UnsupportedOperationException();

	}
}
