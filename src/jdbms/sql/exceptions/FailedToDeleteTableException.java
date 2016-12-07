package jdbms.sql.exceptions;

public class FailedToDeleteTableException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 2186147084710321471L;

	public FailedToDeleteTableException() {
		super();
	}

	public FailedToDeleteTableException(final String message) {
		super(message);
	}

	public FailedToDeleteTableException(final Throwable cause) {
		super(cause);
	}

	public FailedToDeleteTableException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public FailedToDeleteTableException(final String message,
			final Throwable cause, final boolean enableSuppression,
			final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
