package jdbms.sql.parsing.expressions.util;

public class ValueExpression {

	private String expression;
	public ValueExpression(String expression) {
		this.expression = expression.trim();
	}

	public boolean isValidExpressionName() {
		return this.expression.matches("^\\d+$") || 
				this.expression.matches("^[\"].*?[\"]$") ||
				this.expression.matches("^['].*?[']$");
	}
	public String getExpression() {
		return expression;
	}
}