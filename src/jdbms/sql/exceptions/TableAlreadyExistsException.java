package jdbms.sql.exceptions;

public class TableAlreadyExistsException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = -4957650035529989120L;

	public TableAlreadyExistsException() {
		super();
	}

	public TableAlreadyExistsException(String message) {
		super(message);
	}

	public TableAlreadyExistsException(Throwable cause) {
		super(cause);
	}

	public TableAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public TableAlreadyExistsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
