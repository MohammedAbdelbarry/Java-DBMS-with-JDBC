package jdbms.sql.exceptions;

public class DatabaseAlreadyExistsException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -4292988693641550467L;

    public DatabaseAlreadyExistsException() {
        super("");
    }

    public DatabaseAlreadyExistsException(final String message) {
        super(message);
    }

    public DatabaseAlreadyExistsException(final Throwable cause) {
        super(cause);
    }

    public DatabaseAlreadyExistsException(final String message, final
    Throwable cause) {
        super(message, cause);
    }

    public DatabaseAlreadyExistsException(final String message,
                                          final Throwable cause, final
										  boolean enableSuppression,
                                          final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
