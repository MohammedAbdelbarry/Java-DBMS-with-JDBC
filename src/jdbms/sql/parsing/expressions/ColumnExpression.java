package jdbms.sql.parsing.expressions;

public class ColumnExpression implements Expression {

	@Override
	public boolean interpret(String expression) {
		if (expression.isEmpty() || expression == null) {
			return false;
		} else {
			if (expression.startsWith("`") && expression.endsWith("`")) {
				
			}
		}
		return false;
	}
}
