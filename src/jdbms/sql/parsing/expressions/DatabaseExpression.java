package jdbms.sql.parsing.expressions;

import jdbms.sql.parsing.statements.Statement;

public class DatabaseExpression implements Expression {

	private Statement nextStatement;
	private Expression nextExpression;

	public DatabaseExpression(Statement nextStatement, Expression nextExpression) {
		this.nextStatement = nextStatement;
		this.nextExpression = nextExpression;
	}

	@Override
	public boolean interpret(String sqlExpression) {
		if (sqlExpression.matches("^[a-zA-Z_][a-zA-Z0-9_\\$]*$")) {
			if (this.nextStatement == null) {
				return true;
			} else {
				return nextStatement.interpret(sqlExpression);
			}
		}
		return false;
	}
}
