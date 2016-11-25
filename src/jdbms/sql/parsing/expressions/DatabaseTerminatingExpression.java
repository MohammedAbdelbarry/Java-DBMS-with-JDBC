package jdbms.sql.parsing.expressions;

import jdbms.sql.parsing.properties.InputParametersContainer;

public class DatabaseTerminatingExpression extends DatabaseExpression {

	public DatabaseTerminatingExpression(
			InputParametersContainer parameters) {
		super(new TerminalExpression(parameters), parameters);
	}

	@Override
	public boolean interpret(String sqlExpression) {
		String databaseName = sqlExpression.substring(0, sqlExpression.indexOf(" "));
		String restOfExpression = sqlExpression.substring(sqlExpression.indexOf(" ") + 1);
		if (databaseName.matches("^[a-zA-Z_][a-zA-Z0-9_\\$]*$")) {
			parameters.setDatabaseName(databaseName);
			return super.interpret(restOfExpression);
		}
		return false;
	}
}
