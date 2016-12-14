package jdbms.sql.errors.util;

public abstract class ErrorMessages {
	public static final String NOT_FOUND
	= ErrorMessagesHelper.getString("ErrorMessages.not.found");
	public static final String ALREADY_EXISTS
	= ErrorMessagesHelper.getString("ErrorMessages.already.exists");
	public static final String COLUMN_LIST
	= ErrorMessagesHelper.getString("ErrorMessages.column.list");
	public static final String VALUE_LIST
	= ErrorMessagesHelper.getString("ErrorMessages.value.list");
	public static final String UNSUPPORTED_DATATYPE
	= ErrorMessagesHelper.getString("ErrorMessages.unsupported.data.type");
	public static final String TYPE_MISMATCH
	= ErrorMessagesHelper.getString("ErrorMessages.type.mismatch");
	public static final String RESERVED_KEYWORD
	= ErrorMessagesHelper.getString("ErrorMessages.reserved.keyword");
	public static final String INTERNAL_ERROR
	= ErrorMessagesHelper.getString("ErrorMessages.internal.error");
	public static final String FAILED_TO_DELETE
	= ErrorMessagesHelper.getString("ErrorMessages.failed.to.delete");
	public static final String SYNTAX_ERROR
	= ErrorMessagesHelper.getString("ErrorMessages.syntax.error");
	public static final String INVALID_DATE
	= ErrorMessagesHelper.getString("ErrorMessages.invalid.date");
	public static final String REPEATED_COLUMNS
	= ErrorMessagesHelper.getString("ErrorMessages.repeated.columns");
	public static final String TOO_LARGE
	= ErrorMessagesHelper.getString("ErrorMessages.too.large");
	public static final String TOO_SMALL
	= ErrorMessagesHelper.getString("ErrorMessages.too.small");
	public static final String STATEMENT_IS_NOT
	= ErrorMessagesHelper.getString("ErrorMessages.statement.is.not");
}
