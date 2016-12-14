package jdbms.sql.exceptions;

public class DataTypeNotSupportedException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = -5110339303916450057L;

	public DataTypeNotSupportedException() {
		super("");
	}

	public DataTypeNotSupportedException(final String message) {
		super(message);
	}

	public DataTypeNotSupportedException(final Throwable cause) {
		super(cause);
	}

	public DataTypeNotSupportedException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public DataTypeNotSupportedException(final String message,
			final Throwable cause, final boolean enableSuppression,
			final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
