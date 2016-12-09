package jdbc.drivers.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProtocolConstants {

	public static final List<String> SUPPORTED_PROTOCOLS =
			Collections.unmodifiableList(Arrays.asList("xmldb", "jsondb"));
	public static final String URL_PREFIX = "jdbc:";
	public static final String URL_SUFFIX = "://localhost";
	public static final char SEPARATOR = ':';
}
