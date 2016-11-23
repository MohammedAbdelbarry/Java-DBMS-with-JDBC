package jdbms.sql.parsing.expressions;

import jdbms.sql.parsing.statements.Statement;

public class DatabaseExpression implements Expression {

	private Statement nextStatement;
	private Expression nextExpression;

	public DatabaseExpression(Statement nextStatement) {
		this.nextStatement = nextStatement;
	}

	public DatabaseExpression(Expression nextExpression) {
		this.nextExpression = nextExpression;
	}

	@Override
	public boolean interpret(String sqlExpression) {
		if (this.nextStatement != null) {
			return this.nextStatement.interpret(sqlExpression);
		} else if (this.nextExpression != null) {
			return this.nextExpression.interpret(sqlExpression);
		}
		return false;
	}
}
