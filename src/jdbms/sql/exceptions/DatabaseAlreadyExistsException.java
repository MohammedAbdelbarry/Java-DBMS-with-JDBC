package jdbms.sql.exceptions;

public class DatabaseAlreadyExistsException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = -4292988693641550467L;

	public DatabaseAlreadyExistsException() {
		super();
	}

	public DatabaseAlreadyExistsException(String message) {
		super(message);
	}

	public DatabaseAlreadyExistsException(Throwable cause) {
		super(cause);
	}

	public DatabaseAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public DatabaseAlreadyExistsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
