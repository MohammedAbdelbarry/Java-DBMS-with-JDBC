package jdbms.sql.errors.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ErrorMessagesHelper {
	private static final String BUNDLE_NAME
	= "jdbms.sql.errors.util.error-messages";

	private final ResourceBundle resourceBundle;

	public ErrorMessagesHelper() {
		resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);
	}

	public String getString(final String key) {
		try {
			return resourceBundle.getString(key);
		} catch (final MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
