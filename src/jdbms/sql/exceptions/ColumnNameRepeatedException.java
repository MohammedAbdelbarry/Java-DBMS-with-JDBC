package jdbms.sql.exceptions;

public class ColumnNameRepeatedException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = -5191693592596486201L;

	public ColumnNameRepeatedException() {
		super();
	}

	public ColumnNameRepeatedException(String message) {
		super(message);
	}

	public ColumnNameRepeatedException(Throwable cause) {
		super(cause);
	}

	public ColumnNameRepeatedException(String message, Throwable cause) {
		super(message, cause);
	}

	public ColumnNameRepeatedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
