package jdbms.sql.parsing.expressions.util;

public class ColumnExpression {

	private String expression;
	public ColumnExpression(String expression) {
		this.expression = expression.trim();
	}

	public boolean isValidColumnName() {
		return this.expression.matches("^[a-zA-Z_][a-zA-Z0-9_\\$]*$");
	}
	public String getExpression() {
		return expression;
	}
}
