package jdbms.sql.parsing.expressions;

import jdbms.sql.parsing.expressions.math.BooleanExpression;

public class IsBooleanExpression implements Expression {

	@Override
	public boolean interpret(String sqlExpression) {
		BooleanExpression boolexp = new BooleanExpression();
		return false;
	}
}
