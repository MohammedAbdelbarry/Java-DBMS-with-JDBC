package jdbms.sql.parsing.expressions;

import jdbms.sql.parsing.statements.Statement;

public class TableCreationTableInfo implements Expression {

	private Expression nextExpression;
	private Statement nextStatement;

	public TableCreationTableInfo(Expression nextExpression) {
		this.nextExpression = nextExpression;
	}

	public TableCreationTableInfo(Statement nextStatement) {
		this.nextStatement = nextStatement;
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
