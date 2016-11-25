package jdbms.sql.exceptions;

public class TableNotFoundException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 1496203178582421938L;

	public TableNotFoundException() {
		super();
	}

	public TableNotFoundException(String message) {
		super(message);
	}

	public TableNotFoundException(Throwable cause) {
		super(cause);
	}

	public TableNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public TableNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
