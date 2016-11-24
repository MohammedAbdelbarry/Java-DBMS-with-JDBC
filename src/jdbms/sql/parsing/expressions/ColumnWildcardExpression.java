package jdbms.sql.parsing.expressions;

import jdbms.sql.parsing.statements.FromStatement;

public class ColumnWildcardExpression extends ColumnListExpression {

	public ColumnWildcardExpression() {
		super(new FromStatement());
	}

	@Override
	public boolean interpret(String sqlExpression) {
		if (sqlExpression.startsWith("*")) {
			return super.interpret(sqlExpression.substring(sqlExpression.indexOf(" ") + 1));
		}
		return false;
	}
}
