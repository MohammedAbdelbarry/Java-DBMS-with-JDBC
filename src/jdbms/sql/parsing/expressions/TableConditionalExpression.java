package jdbms.sql.parsing.expressions;

import jdbms.sql.parsing.statements.WhereStatement;

public class TableConditionalExpression extends TableExpression {

	public TableConditionalExpression() {
		super(new WhereStatement());
	}

	@Override
	public boolean interpret(String sqlExpression) {
		String tableName = sqlExpression.substring(0, sqlExpression.indexOf(" "));
		String restOfExpression = sqlExpression.substring(sqlExpression.indexOf(" ") + 1);
		if (tableName.matches("^[a-zA-Z_][a-zA-Z0-9_\\$]*$")) {
			return super.interpret(restOfExpression);
		}
		return false;
	}
}
