package jdbc.drivers;

import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jdbc.connections.DBConnection;
import jdbc.drivers.util.ProtocolConstants;

public class DBDriver implements Driver {
	private final Logger logger;
	private final static int CONNECTIONS_MAX = 10000;
	private static int noOfConnections = 0;
	static {
		try {
			DriverManager.registerDriver(new DBDriver());
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
	public DBDriver() {
		logger = LogManager.getLogger(DBDriver.class);
		logger.debug("Driver Started");
	}
	@Override
	public boolean acceptsURL(final String url) throws SQLException {
		return isValidURL(url);
	}

	@Override
	public Connection connect(final String url,
			final Properties info) throws SQLException {
		if (!isValidURL(url)) {
			return null;
		}
		if (!isBelowMaxNoOfConnections()) {
			final SQLException ex
			= new SQLException("Connections Overloaded");
			logger.error("Current number of connections exceeded "
					+ CONNECTIONS_MAX, ex);
		}
		noOfConnections++;
		final File directory = (File) info.get("path");
		if (directory == null) {
			final SQLException ex
			= new SQLException("Null Directory");
			logger.error("Failed to Connect to URL("
					+ url + ")", ex);
			throw ex;
		}
		logger.debug("Connection Requested: URL(" +
				url + ") File Path(" + directory.getAbsolutePath() + ")");
		final String directoryPath = directory.getAbsolutePath();
		final DBConnection connection = new DBConnection(url,
				directoryPath);
		return connection;
	}

	private boolean isValidURL(final String url) {
		final ProtocolConstants constants = new ProtocolConstants();
		if (!url.startsWith(constants.getUrlPrefix())
				|| !url.endsWith(constants.getUrlSuffix())) {
			return false;
		} else {
			final String protocol = url.substring(
					url.indexOf(constants.getSeparator()) + 1,
					url.lastIndexOf(constants.getSeparator()));
			return isValidProtocol(protocol);
		}
	}

	private boolean isValidProtocol(final String protocol) {
		final ProtocolConstants constants = new ProtocolConstants();
		final boolean exists = constants.getSupportedProtocols().contains(protocol);
		return exists;
	}

	private boolean isBelowMaxNoOfConnections() {
		return noOfConnections < CONNECTIONS_MAX;
	}

	@Override
	public DriverPropertyInfo[] getPropertyInfo(final String url, final Properties info) throws SQLException {
		final DriverPropertyInfo[] properties = new DriverPropertyInfo[info.size()];
		int i = 0;
		for (final Object property : info.keySet()) {
			properties[i].name = (String) property;
			properties[i].value = (String)info.get(property);
			i++;
		}
		return properties;
	}

	@Override
	public int getMajorVersion() {
		throw new UnsupportedOperationException();

	}

	@Override
	public int getMinorVersion() {
		throw new UnsupportedOperationException();

	}

	@Override
	public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
		throw new UnsupportedOperationException();

	}


	@Override
	public boolean jdbcCompliant() {
		throw new UnsupportedOperationException();
	}

}
