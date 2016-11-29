package jdbms.sql.parsing.expressions;

import jdbms.sql.errors.ErrorHandler;
import jdbms.sql.parsing.properties.InputParametersContainer;
import jdbms.sql.parsing.util.Constants;

/**
 * The Class DatabaseTerminatingExpression.
 */
public class DatabaseTerminatingExpression extends DatabaseExpression {

	/**
	 * Instantiates a new database terminating expression.
	 * @param parameters the input parameters
	 */
	public DatabaseTerminatingExpression(
			InputParametersContainer parameters) {
		super(new TerminalExpression(parameters), parameters);
	}

	@Override
	public boolean interpret(String sqlExpression) {
		String databaseName = sqlExpression.
				substring(0, sqlExpression.indexOf(" "));
		String restOfExpression
		= sqlExpression.substring(sqlExpression.
				indexOf(" ") + 1);
		if (databaseName.matches("^[a-zA-Z_][a-zA-Z0-9_\\$]*$")) {
			if (Constants.RESERVED_KEYWORDS.
					contains(databaseName.toUpperCase())) {
				ErrorHandler.
				printReservedKeywordError(databaseName);
				return false;
			}
			parameters.setDatabaseName(databaseName);
			return super.interpret(restOfExpression);
		}
		ErrorHandler.printSyntaxErrorNear("Database Name");
		return false;
	}
}
