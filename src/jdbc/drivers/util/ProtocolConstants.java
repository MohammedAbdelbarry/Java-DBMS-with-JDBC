package jdbc.drivers.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProtocolConstants {

	public static final List<String> SUPPORTED_PROTOCOLS =
			Collections.unmodifiableList(Arrays.asList(Messages.getString("ProtocolConstants.XMLProtocol"),
					Messages.getString("ProtocolConstants.AlternativeProtocols"))); //$NON-NLS-1$ //$NON-NLS-2$
	public static final String URL_PREFIX = Messages.getString("ProtocolConstants.ProtocolPrefix"); //$NON-NLS-1$
	public static final String URL_SUFFIX = Messages.getString("ProtocolConstants.LocalHost"); //$NON-NLS-1$
	public static final char SEPARATOR = ':';
}
