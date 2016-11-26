package jdbms.sql.errors;

public abstract class ErrorHandler {

	public void printColumnNotFoundError(String columnName) {
		System.err.println("Error: Column \"" + columnName + "\" Not Found");
	}
}
