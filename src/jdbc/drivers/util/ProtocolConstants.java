package jdbc.drivers.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProtocolConstants {

	private List<String> supportedProtocols;
	private String urlPrefix;
	private String urlSuffix;
	private char separator = ':';
	public ProtocolConstants() {
		ProtocolConstantsHelper helper
				= new ProtocolConstantsHelper();
		supportedProtocols =
				Collections.unmodifiableList(Arrays.asList(
								helper.getString(
										"ProtocolConstants."
												+ "XMLProtocol"),
								helper.getString(
										"ProtocolConstants."
												+ "AlternativeProtocols")));
		urlSuffix =
				helper.getString(
						"ProtocolConstants.LocalHost");
		urlPrefix =
				helper.getString(
						"ProtocolConstants.ProtocolPrefix");
	}
	public List<String> getSupportedProtocols() {
		return supportedProtocols;
	}

	public String getUrlPrefix() {
		return urlPrefix;
	}

	public String getUrlSuffix() {
		return urlSuffix;
	}

	public char getSeparator() {
		return separator;
	}
}
