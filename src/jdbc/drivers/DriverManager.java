package jdbc.drivers;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import javax.lang.model.type.PrimitiveType;
import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

import jdbc.connections.ConnectionHub;
import jdbc.drivers.util.ProtocolConstants;

public class DriverManager implements Driver {

	private ConnectionHub connection;
	private Properties connectionProperties;

	public DriverManager() {
		
	}
	@Override
	public boolean acceptsURL(String url) throws SQLException {
		return isValidURL(url);
	}

	@Override
	public Connection connect(String url, Properties info) throws SQLException {
		connection = new ConnectionHub(url);
		connectionProperties = info;
		return connection;
	}

	private boolean isValidURL(String url) {
		if (!url.startsWith(ProtocolConstants.URL_PREFIX)
				|| !url.endsWith(ProtocolConstants.URL_SUFFIX)) {
			return false;
		} else {
			String protocol = url.substring(
					url.indexOf(ProtocolConstants.SEPARATOR) + 1,
					url.lastIndexOf(ProtocolConstants.SEPARATOR));
			return isValidProtocol(protocol);
		}
	}

	private boolean isValidProtocol(String protocol) {
		boolean exists = ProtocolConstants.SUPPORTED_PROTOCOLS.contains(protocol);
		return exists;
	}
	@Override
	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
		DriverPropertyInfo[] properties = new DriverPropertyInfo[info.size()];
		int i = 0;
		for (Object property : info.keySet()) {
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
