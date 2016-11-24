package jdbms.sql.parsing.expressions;

import jdbms.sql.parsing.statements.Statement;

public class ColumnListExpression implements Expression {

	private Expression nextExpression = null;
	private Statement nextStatement = null;

	public ColumnListExpression(Expression nextExpression) {
		this.nextExpression = nextExpression;
	}

	public ColumnListExpression(Statement nextStatement) {
		this.nextStatement = nextStatement;
	}

	@Override
	public boolean interpret(String sqlExpression) {
		if (nextExpression != null) {
			return nextExpression.interpret(sqlExpression);
		} else if (nextStatement != null) {	
			return nextStatement.interpret(sqlExpression);
		}
		return false;
	}
}
