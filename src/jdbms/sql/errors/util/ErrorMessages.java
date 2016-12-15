package jdbms.sql.errors.util;

public class ErrorMessages {
	private String notFound;
	private String alreadyExists;
	private String columnList;
	private String valueList;
	private String unsupportedDatatype;
	private String typeMismatch;
	private String reservedKeyword;
	private String internalError;
	private String failedToDelete;
	private String syntaxError;
	private String invalidDate;
	private String repeatedColumns;
	private String tooLarge;
	private String tooSmall;
	private String statementIsNot;
	public ErrorMessages() {
        ErrorMessagesHelper helper
                = new ErrorMessagesHelper();
        notFound = helper.
                getString("ErrorMessages.not.found");
        alreadyExists = helper.
                getString("ErrorMessages.already.exists");
        columnList = helper.
                getString("ErrorMessages.column.list");
        valueList = helper.
                getString("ErrorMessages.value.list");
        unsupportedDatatype = helper.
                getString("ErrorMessages.unsupported.data.type");
        typeMismatch = helper.
                getString("ErrorMessages.type.mismatch");
        reservedKeyword = helper.
                getString("ErrorMessages.reserved.keyword");
	    internalError = helper.
                getString("ErrorMessages.internal.error");
        failedToDelete = helper.
                getString("ErrorMessages.failed.to.delete");
        syntaxError = helper.
                getString("ErrorMessages.syntax.error");
        invalidDate = helper.
                getString("ErrorMessages.invalid.date");
        repeatedColumns = helper.
                getString("ErrorMessages.repeated.columns");
        tooLarge = helper.
                getString("ErrorMessages.too.large");
        tooSmall = helper.
                getString("ErrorMessages.too.small");
        statementIsNot = helper.
                getString("ErrorMessages.statement.is.not");
	}
	public String getNotFound() {
		return notFound;
	}

	public String getAlreadyExists() {
		return alreadyExists;
	}

	public String getColumnList() {
		return columnList;
	}

	public String getValueList() {
		return valueList;
	}

	public String getUnsupportedDatatype() {
		return unsupportedDatatype;
	}

	public String getTypeMismatch() {
		return typeMismatch;
	}

	public String getReservedKeyword() {
		return reservedKeyword;
	}

	public String getInternalError() {
		return internalError;
	}

	public String getFailedToDelete() {
		return failedToDelete;
	}

	public String getSyntaxError() {
		return syntaxError;
	}

	public String getInvalidDate() {
		return invalidDate;
	}

	public String getRepeatedColumns() {
		return repeatedColumns;
	}

	public String getTooLarge() {
		return tooLarge;
	}

	public String getTooSmall() {
		return tooSmall;
	}

	public String getStatementIsNot() {
		return statementIsNot;
	}
}
