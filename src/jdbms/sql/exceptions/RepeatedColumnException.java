package jdbms.sql.exceptions;

public class RepeatedColumnException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = -8110775092161663477L;

	public RepeatedColumnException() {
		super("");
	}

	public RepeatedColumnException(final String message) {
		super(message);
	}

	public RepeatedColumnException(final Throwable cause) {
		super(cause);
	}

	public RepeatedColumnException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public RepeatedColumnException(final String message,
			final Throwable cause, final boolean enableSuppression,
			final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
