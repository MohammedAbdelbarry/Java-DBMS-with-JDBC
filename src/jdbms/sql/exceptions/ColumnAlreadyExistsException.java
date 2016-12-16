package jdbms.sql.exceptions;

public class ColumnAlreadyExistsException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID
            = 7031139545495825589L;

    public ColumnAlreadyExistsException() {
        super("");
    }

    public ColumnAlreadyExistsException(final String message) {
        super(message);
    }

    public ColumnAlreadyExistsException(final Throwable cause) {
        super(cause);
    }

    public ColumnAlreadyExistsException(final String message,
                                        final Throwable cause) {
        super(message, cause);
    }

    public ColumnAlreadyExistsException(final String message,
                                        final Throwable cause,
                                        final boolean enableSuppression,
                                        final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
