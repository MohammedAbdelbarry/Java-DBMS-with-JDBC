package jdbms.sql.parsing.expressions;

import jdbms.sql.parsing.statements.Statement;

public class TableNameExpression implements Expression {

	private Statement nextStatement;
	private Expression nextExpression;

	public TableNameExpression(Expression nextExpression) {
		this.nextExpression = nextExpression;
	}

	public TableNameExpression(Statement nextStatement) {
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
