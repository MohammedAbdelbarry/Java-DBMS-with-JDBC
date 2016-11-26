package jdbms.sql.errors;

public abstract class ErrorHandler {
	private static final String NULL_PLACEHOLDER = "";
	public static void printColumnNotFoundError(String columnName) {
		System.err.println("Error: Column \""
			+ handleNull(columnName) + "\" Not Found");
	}
	public static void printRepeatedColumnError() {
		System.err.println("Error: Column List Has Repeated Columns");
	}
	public static void printColumnAlreadyExistsError(String columnName) {
		System.err.println("Error: Column \""
			+ handleNull(columnName) + "\" Already Exists");
	}
	public static void  printColumnListTooLargeError() {
		System.err.println("Error: Column List is Too Large");
	}
	public static void  printDatabaseAlreadyExistsError(String databaseName) {
		System.err.println("Error: Database \""
			+ handleNull(databaseName) + "\" Already Exists");
	}
	public static void  printDatabaseNotFoundError(String databaseName) {
		System.err.println("Error: Database \""
			+ handleNull(databaseName) + "\" Not Found");
	}
	public static void printDataTypeNotSupportedError(String dataType) {
		System.err.println("Error: Data Type \""
			+ dataType + "\" Not Supported");
	}
	public static void printTableAlreadyExistsError(String tableName) {
		System.err.println("Error: Table \"" +
			handleNull(tableName) + "\" Already Exists");
	}
	public static void printTableNotFoundError(String tableName) {
		System.err.println("Error: Table \"" +
			handleNull(tableName) + "\" Not Found");
	}
	public static void printTypeMismatchError() {
		System.err.println("Error: Type Mismatch");
	}
	public static void printValueListTooLargeError() {
		System.err.println("Error: Value List is Too Large");
	}
	public static void printValueListTooSmallError() {
		System.err.println("Error: Value List is Too Small");
	}
	private static String handleNull(String string) {
		if (string == null) {
			return NULL_PLACEHOLDER;
		}
		return string;
	}
}
