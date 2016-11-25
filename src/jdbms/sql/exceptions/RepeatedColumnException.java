package jdbms.sql.exceptions;

public class RepeatedColumnException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = -8110775092161663477L;

	public RepeatedColumnException() {
		super();
	}

	public RepeatedColumnException(String message) {
		super(message);
	}

	public RepeatedColumnException(Throwable cause) {
		super(cause);
	}

	public RepeatedColumnException(String message, Throwable cause) {
		super(message, cause);
	}

	public RepeatedColumnException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
