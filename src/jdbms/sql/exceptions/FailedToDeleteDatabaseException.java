package jdbms.sql.exceptions;

public class FailedToDeleteDatabaseException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -6982584183963601001L;

    public FailedToDeleteDatabaseException() {
        super("");
    }

    public FailedToDeleteDatabaseException(final String message) {
        super(message);
    }

    public FailedToDeleteDatabaseException(final Throwable cause) {
        super(cause);
    }

    public FailedToDeleteDatabaseException(final String message, final
    Throwable cause) {
        super(message, cause);
    }

    public FailedToDeleteDatabaseException(final String message,
                                           final Throwable cause, final
										   boolean enableSuppression,
                                           final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
