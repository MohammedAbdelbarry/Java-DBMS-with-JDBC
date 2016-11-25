package jdbms.sql.exceptions;

public class ColumnListTooLargeException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = -3724054449498404016L;

	public ColumnListTooLargeException() {
		super();
	}

	public ColumnListTooLargeException(String message) {
		super(message);
	}

	public ColumnListTooLargeException(Throwable cause) {
		super(cause);
	}

	public ColumnListTooLargeException(String message, Throwable cause) {
		super(message, cause);
	}

	public ColumnListTooLargeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
