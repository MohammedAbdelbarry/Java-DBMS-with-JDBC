package jdbms.sql.exceptions;

public class ColumnListTooLargeException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = -3724054449498404016L;

	public ColumnListTooLargeException() {
		super("");
	}

	public ColumnListTooLargeException(final String message) {
		super(message);
	}

	public ColumnListTooLargeException(final Throwable cause) {
		super(cause);
	}

	public ColumnListTooLargeException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public ColumnListTooLargeException(final String message,
			final Throwable cause, final boolean enableSuppression,
			final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
