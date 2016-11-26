package jdbms.sql.parsing.expressions.util;

import jdbms.sql.errors.ErrorHandler;
import jdbms.sql.parsing.util.Constants;

public class ColumnExpression {

	private String expression;
	public ColumnExpression(String expression) {
		this.expression = expression.trim();
	}

	public boolean isValidColumnName() {
		if (Constants.RESERVED_KEYWORDS.contains(expression.toUpperCase())) {
			ErrorHandler.printReservedKeywordError(expression);
			return false;
		}
		return this.expression.matches("^[a-zA-Z_][a-zA-Z0-9_\\$]*$");
	}
	public String getExpression() {
		return expression;
	}
}
