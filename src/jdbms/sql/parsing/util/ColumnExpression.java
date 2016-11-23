package jdbms.sql.parsing.expressions;

public class ColumnExpression implements Expression {

	private String expression;
	public ColumnExpression(String expression) {
		this.expression = expression;
	}

	@Override
	public boolean interpret(String expression) {
		if (expression.matches("^[a-zA-Z_][a-zA-Z0-9_\\$]*$")) {
			return true;
		}
		return false;
	}
}
