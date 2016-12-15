package jdbc.drivers;

import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;

import jdbc.connections.DBConnection;
import jdbc.drivers.util.ProtocolConstants;

public class DBDriver implements Driver {

	private final ArrayList<DBConnection> connections;
	static {
		try {
			DriverManager.registerDriver(new DBDriver());
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
	public DBDriver() {
		connections = new ArrayList<>();
	}
	@Override
	public boolean acceptsURL(final String url) throws SQLException {
		return isValidURL(url);
	}

	@Override
	public Connection connect(final String url, final Properties info) throws SQLException {
		if (!isValidURL(url)) {
			return null;
		}
		final File directory = (File) info.get("path");
		if (directory == null) {
			throw new SQLException();
		}
		final String directoryPath = directory.getAbsolutePath();
		final DBConnection connection = new DBConnection(url,
				directoryPath);
		connections.add(connection);
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
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		throw new UnsupportedOperationException();

	}


	@Override
	public boolean jdbcCompliant() {
		throw new UnsupportedOperationException();
	}

}
