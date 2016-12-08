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
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import jdbc.drivers.util.ProtocolConstants;

public class ConnectionHub implements Connection{

	private String url;

	public ConnectionHub(String url) {
		this.url = url;
	}
	
	@Override
	public void close() throws SQLException {
		
	}

	private String getProtocolName(String url) {
		int start = url.indexOf(ProtocolConstants.SEPARATOR);
		String subURL = url.substring(start + 1, url.length());
		return subURL.substring(0, subURL.indexOf(ProtocolConstants.SEPARATOR));
	}

	@Override
	public Statement createStatement() throws SQLException {
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void abort(Executor arg0) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void clearWarnings() throws SQLException {
		throw new UnsupportedOperationException();
		
	}


	@Override
	public void commit() throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public Array createArrayOf(String arg0, Object[] arg1) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public Blob createBlob() throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public Clob createClob() throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public NClob createNClob() throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public SQLXML createSQLXML() throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public Statement createStatement(int arg0, int arg1) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public Statement createStatement(int arg0, int arg1, int arg2) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public Struct createStruct(String arg0, Object[] arg1) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public boolean getAutoCommit() throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public String getCatalog() throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public Properties getClientInfo() throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public String getClientInfo(String arg0) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public int getHoldability() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public DatabaseMetaData getMetaData() throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public int getNetworkTimeout() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getSchema() throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public int getTransactionIsolation() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, Class<?>> getTypeMap() throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public boolean isClosed() throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public boolean isReadOnly() throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public boolean isValid(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public String nativeSQL(String arg0) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public CallableStatement prepareCall(String arg0) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public CallableStatement prepareCall(String arg0, int arg1, int arg2) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public CallableStatement prepareCall(String arg0, int arg1, int arg2, int arg3) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public PreparedStatement prepareStatement(String arg0) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public PreparedStatement prepareStatement(String arg0, int arg1) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public PreparedStatement prepareStatement(String arg0, int[] arg1) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public PreparedStatement prepareStatement(String arg0, String[] arg1) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public PreparedStatement prepareStatement(String arg0, int arg1, int arg2) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public PreparedStatement prepareStatement(String arg0, int arg1, int arg2, int arg3) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void releaseSavepoint(Savepoint arg0) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void rollback() throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void rollback(Savepoint arg0) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void setAutoCommit(boolean arg0) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void setCatalog(String arg0) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void setClientInfo(Properties arg0) throws SQLClientInfoException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void setClientInfo(String arg0, String arg1) throws SQLClientInfoException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void setHoldability(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void setNetworkTimeout(Executor arg0, int arg1) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void setReadOnly(boolean arg0) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public Savepoint setSavepoint() throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public Savepoint setSavepoint(String arg0) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void setSchema(String arg0) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void setTransactionIsolation(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void setTypeMap(Map<String, Class<?>> arg0) throws SQLException {
		throw new UnsupportedOperationException();
		
	}

	
}
