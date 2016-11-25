package jdbms.sql.exceptions;

public class ColumnAlreadyExistsException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 7031139545495825589L;

	public ColumnAlreadyExistsException() {
		super();
	}

	public ColumnAlreadyExistsException(String message) {
		super(message);
	}

	public ColumnAlreadyExistsException(Throwable cause) {
		super(cause);
	}

	public ColumnAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public ColumnAlreadyExistsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
