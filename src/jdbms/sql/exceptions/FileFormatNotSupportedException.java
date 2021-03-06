package jdbms.sql.exceptions;

public class FileFormatNotSupportedException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 2821318296968090378L;

    public FileFormatNotSupportedException() {
        super("");
    }

    public FileFormatNotSupportedException(final String message) {
        super(message);
    }

    public FileFormatNotSupportedException(final Throwable cause) {
        super(cause);
    }

    public FileFormatNotSupportedException(final String message, final
    Throwable cause) {
        super(message, cause);
    }

    public FileFormatNotSupportedException(final String message,
                                           final Throwable cause, final
										   boolean enableSuppression,
                                           final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
