package jdbms.sql.errors.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public abstract class ErrorMessagesHelper {
	private static final String BUNDLE_NAME
	= "jdbms.sql.errors.util.error-messages";

	private static final ResourceBundle RESOURCE_BUNDLE
	= ResourceBundle.getBundle(BUNDLE_NAME);

	public static String getString(final String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (final MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
