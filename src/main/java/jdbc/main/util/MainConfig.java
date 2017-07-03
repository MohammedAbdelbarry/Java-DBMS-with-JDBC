package jdbc.main.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MainConfig {
	private static final String BUNDLE_NAME
	= "jdbc.main.util.main-config";
	private final ResourceBundle
	resourceBundle;
	public MainConfig() {
		resourceBundle = ResourceBundle.
				getBundle(BUNDLE_NAME);
	}
	public String getString(final String key) {
		try {
			return resourceBundle.getString(key);
		} catch (final MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
