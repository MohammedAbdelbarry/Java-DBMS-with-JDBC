package jdbms.sql.parsing.expressions;

import jdbms.sql.parsing.statements.Statement;

public class ValueListExpression implements Expression {

	private Statement nextStatement;
	private Expression nextExpression;

	public ValueListExpression(Statement nextStatement) {
		this.nextStatement = nextStatement;
	}

	public ValueListExpression(Expression nextExpression) {
		this.nextExpression = nextExpression;
	}

	@Override
	public boolean interpret(String sqlExpression) {
		if (this.nextExpression != null) {
			return this.nextExpression.interpret(sqlExpression);
		} else if (this.nextStatement != null) {
			return this.nextStatement.interpret(sqlExpression);
		}
		return false;
	}
}
