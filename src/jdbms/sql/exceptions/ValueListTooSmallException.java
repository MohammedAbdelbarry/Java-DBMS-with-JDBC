package jdbms.sql.exceptions;

public class ValueListTooSmallException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = -5707348571705640359L;

	public ValueListTooSmallException() {
		super();
	}

	public ValueListTooSmallException(String message) {
		super(message);
	}

	public ValueListTooSmallException(Throwable cause) {
		super(cause);
	}

	public ValueListTooSmallException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValueListTooSmallException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
