package jdbms.sql.parsing.expressions;

public class DatabaseTerminatingExpression extends DatabaseExpression {

	public DatabaseTerminatingExpression() {
		super(new TerminalExpression());
	}

	@Override
	public boolean interpret(String sqlExpression) {
		sqlExpression = sqlExpression.replace(";", "");
		sqlExpression = sqlExpression.trim();
		if (sqlExpression.matches("^[a-zA-Z_][a-zA-Z0-9_\\$]*$")) {
			return super.interpret(";");
		}
		return false;
	}
}
