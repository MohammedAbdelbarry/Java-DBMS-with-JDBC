package jdbms.sql.exceptions;

public class TableAlreadyExistsException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -4957650035529989120L;

    public TableAlreadyExistsException() {
        super("");
    }

    public TableAlreadyExistsException(final String message) {
        super(message);
    }

    public TableAlreadyExistsException(final Throwable cause) {
        super(cause);
    }

    public TableAlreadyExistsException(final String message, final Throwable
            cause) {
        super(message, cause);
    }

    public TableAlreadyExistsException(final String message,
                                       final Throwable cause, final boolean
                                               enableSuppression,
                                       final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
