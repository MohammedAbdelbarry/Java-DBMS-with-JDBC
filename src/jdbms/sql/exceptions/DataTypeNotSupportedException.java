package jdbms.sql.exceptions;

public class DataTypeNotSupportedException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = -5110339303916450057L;

	public DataTypeNotSupportedException() {
		super();
	}

	public DataTypeNotSupportedException(String message) {
		super(message);
	}

	public DataTypeNotSupportedException(Throwable cause) {
		super(cause);
	}

	public DataTypeNotSupportedException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataTypeNotSupportedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
