package jdbms.sql.parsing.expressions;

public class DatabaseTerminatingExpression extends DatabaseExpression {

	public DatabaseTerminatingExpression() {
		super(new TerminalExpression());
	}

	@Override
	public boolean interpret(String sqlExpression) {
		String databaseName = sqlExpression.substring(0, sqlExpression.indexOf(" "));
		String restOfExpression = sqlExpression.substring(sqlExpression.indexOf(" ") + 1);
		if (databaseName.matches("^[a-zA-Z_][a-zA-Z0-9_\\$]*$")) {
			return super.interpret(restOfExpression);
		}
		return false;
	}
}
