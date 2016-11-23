package jdbms.sql.parsing.expressions;

import jdbms.sql.parsing.statements.Statement;

public class TableExpression implements Expression {

	private Statement nextStatement;
	private Expression nextExpression;

	public TableExpression(Expression nextExpression) {
		this.nextExpression = nextExpression;
	}

	public TableExpression(Statement nextStatement) {
		this.nextStatement = nextStatement;
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
