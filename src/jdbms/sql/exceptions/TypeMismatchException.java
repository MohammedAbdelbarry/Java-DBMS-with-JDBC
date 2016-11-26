package jdbms.sql.exceptions;

public class TypeMismatchException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = -4193778200259411821L;

	public TypeMismatchException() {
		super();
	}

	public TypeMismatchException(String message) {
		super(message);
	}

	public TypeMismatchException(Throwable cause) {
		super(cause);
	}

	public TypeMismatchException(String message, Throwable cause) {
		super(message, cause);
	}

	public TypeMismatchException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
