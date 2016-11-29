package jdbms.sql.exceptions;

public class TableNotFoundException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 1496203178582421938L;

	public TableNotFoundException() {
		super();
	}

	public TableNotFoundException(final String message) {
		super(message);
	}

	public TableNotFoundException(final Throwable cause) {
		super(cause);
	}

	public TableNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public TableNotFoundException(final String message,
			final Throwable cause, final boolean enableSuppression,
			final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
