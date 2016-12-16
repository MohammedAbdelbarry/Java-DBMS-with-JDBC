package jdbms.sql.exceptions;

public class AllColumnsDroppingException extends Exception {
	/**
	 *
	 */
	private static final long serialVersionUID = -6848628003606117886L;

	public AllColumnsDroppingException() {
		super("");
	}

	public AllColumnsDroppingException(final String message) {
		super(message);
	}

	public AllColumnsDroppingException(final Throwable cause) {
		super(cause);
	}

	public AllColumnsDroppingException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public AllColumnsDroppingException(final String message,
			final Throwable cause, final boolean enableSuppression,
			final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
