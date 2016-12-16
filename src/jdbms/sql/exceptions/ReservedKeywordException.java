package jdbms.sql.exceptions;

public class ReservedKeywordException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = -1737842687623416520L;

    public ReservedKeywordException() {
        super("");
    }

    public ReservedKeywordException(final String message) {
        super(message);
    }

    public ReservedKeywordException(final Throwable cause) {
        super(cause);
    }

    public ReservedKeywordException(final String message, final Throwable
            cause) {
        super(message, cause);
    }

    public ReservedKeywordException(final String message,
                                    final Throwable cause, final boolean
                                            enableSuppression,
                                    final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
