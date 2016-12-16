package jdbms.sql.exceptions;

public class TypeMismatchException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -4193778200259411821L;

    public TypeMismatchException() {
        super("");
    }

    public TypeMismatchException(final String message) {
        super(message);
    }

    public TypeMismatchException(final Throwable cause) {
        super(cause);
    }

    public TypeMismatchException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public TypeMismatchException(final String message,
                                 final Throwable cause, final boolean
                                         enableSuppression,
                                 final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
