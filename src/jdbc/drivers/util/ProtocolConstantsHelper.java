package jdbc.drivers.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ProtocolConstantsHelper {
	private final String bundleName
	= "jdbc/drivers/util/protocol-constants";
	private final ResourceBundle resourceBundle;
	protected ProtocolConstantsHelper() {
		resourceBundle
		= ResourceBundle.getBundle(bundleName);
	}

	protected String getString(final String key) {
		try {
			return resourceBundle.getString(key);
		} catch (final MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
