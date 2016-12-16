package jdbc.drivers.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProtocolConstants {

    private final List<String> supportedProtocols;
    private final String urlPrefix;
    private final String urlSuffix;
    private final char separator = ':';

    public ProtocolConstants() {
        final ProtocolConstantsHelper helper
                = new ProtocolConstantsHelper();
        supportedProtocols =
                Collections.unmodifiableList(Arrays.asList(
                        helper.getString(
                                "ProtocolConstants."
                                        + "XMLProtocol"),
                        helper.getString(
                                "ProtocolConstants."
                                        + "AlternativeProtocols"),
                        helper.getString(
                                "ProtocolConstants."
                                        + "ProtocolBufferProtocol")));
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
