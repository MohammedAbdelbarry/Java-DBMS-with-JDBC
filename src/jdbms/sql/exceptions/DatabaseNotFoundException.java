package jdbms.sql.exceptions;

public class DatabaseNotFoundException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = -2989703287484064941L;

	public DatabaseNotFoundException() {
		super();
	}

	public DatabaseNotFoundException(final String message) {
		super(message);
	}

	public DatabaseNotFoundException(final Throwable cause) {
		super(cause);
	}

	public DatabaseNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public DatabaseNotFoundException(final String message,
			final Throwable cause, final boolean enableSuppression,
			final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
