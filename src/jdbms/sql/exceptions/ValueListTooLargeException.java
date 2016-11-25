package jdbms.sql.exceptions;

public class ValueListTooLargeException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = -7254388599116130458L;

	public ValueListTooLargeException() {
		super();
	}

	public ValueListTooLargeException(String message) {
		super(message);
	}

	public ValueListTooLargeException(Throwable cause) {
		super(cause);
	}

	public ValueListTooLargeException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValueListTooLargeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
