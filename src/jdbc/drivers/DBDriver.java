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
		} catch (final SQLException e) {
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
		final String directoryPath = directory.getAbsolutePath();
		final DBConnection connection = new DBConnection(url,
				directoryPath);
		connections.add(connection);
		return connection;
	}

	private boolean isValidURL(final String url) {
		if (!url.startsWith(ProtocolConstants.URL_PREFIX)
				|| !url.endsWith(ProtocolConstants.URL_SUFFIX)) {
			return false;
		} else {
			final String protocol = url.substring(
					url.indexOf(ProtocolConstants.SEPARATOR) + 1,
					url.lastIndexOf(ProtocolConstants.SEPARATOR));
			return isValidProtocol(protocol);
		}
	}

	private boolean isValidProtocol(final String protocol) {
		final boolean exists = ProtocolConstants.SUPPORTED_PROTOCOLS.contains(protocol);
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
