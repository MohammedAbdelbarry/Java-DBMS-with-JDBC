package jdbms.sql.parsing.expressions;

import jdbms.sql.parsing.statements.Statement;

public abstract class TableNameExpression implements Expression {

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
		String tableName = sqlExpression.substring(0, sqlExpression.indexOf(" ")).trim();
		String restOfExpression = sqlExpression.substring(sqlExpression.indexOf(" ") + 1).trim();
			if (tableName.matches("^[a-zA-Z_][a-zA-Z0-9_\\$]*$")) {
				if (this.nextStatement != null) {
					return this.nextStatement.interpret(restOfExpression);
				} else if (this.nextExpression != null) {
					return this.nextExpression.interpret(restOfExpression);
				}
			}		
		return false;
	}
}
