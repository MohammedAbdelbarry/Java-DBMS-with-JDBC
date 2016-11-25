package jdbms.sql.exceptions;

public class ColumnNotFoundException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = -8235368526021223343L;

	public ColumnNotFoundException() {
		super();
	}

	public ColumnNotFoundException(String message) {
		super(message);
	}

	public ColumnNotFoundException(Throwable cause) {
		super(cause);
	}

	public ColumnNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ColumnNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
