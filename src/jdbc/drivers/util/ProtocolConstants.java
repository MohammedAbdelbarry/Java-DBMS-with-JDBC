package jdbc.drivers.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class ProtocolConstants {

	public static final List<String> SUPPORTED_PROTOCOLS =
			Collections.unmodifiableList(
					Arrays.asList(
							ProtocolConstantsHelper
							.getString("ProtocolConstants."
									+ "XMLProtocol"),
							ProtocolConstantsHelper.
							getString(
									"ProtocolConstants."
											+ "AlternativeProtocols")));
	public static final String URL_PREFIX =
			ProtocolConstantsHelper.getString(
					"ProtocolConstants.ProtocolPrefix");
	public static final String URL_SUFFIX =
			ProtocolConstantsHelper.getString(
					"ProtocolConstants.LocalHost");
	public static final char SEPARATOR = ':';
}
