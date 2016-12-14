package jdbms.sql.exceptions;

public class ColumnNotFoundException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = -8235368526021223343L;

	public ColumnNotFoundException() {
		super("");
	}

	public ColumnNotFoundException(final String message) {
		super(message);
	}

	public ColumnNotFoundException(final Throwable cause) {
		super(cause);
	}

	public ColumnNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public ColumnNotFoundException(final String message,
			final Throwable cause, final boolean enableSuppression,
			final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
