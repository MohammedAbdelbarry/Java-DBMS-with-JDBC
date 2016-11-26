package jdbms.sql.exceptions;

public class ReservedKeywordException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1737842687623416520L;

	public ReservedKeywordException() {
		super();
	}

	public ReservedKeywordException(String message) {
		super(message);
	}

	public ReservedKeywordException(Throwable cause) {
		super(cause);
	}

	public ReservedKeywordException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReservedKeywordException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
