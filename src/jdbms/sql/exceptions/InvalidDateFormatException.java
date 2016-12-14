package jdbms.sql.exceptions;

public class InvalidDateFormatException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = -5766821558845693100L;

	public InvalidDateFormatException() {
		super("");
	}

	public InvalidDateFormatException(final String message) {
		super(message);
	}

	public InvalidDateFormatException(final Throwable cause) {
		super(cause);
	}

	public InvalidDateFormatException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public InvalidDateFormatException(final String message,
			final Throwable cause, final boolean enableSuppression,
			final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
