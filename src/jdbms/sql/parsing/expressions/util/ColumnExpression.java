package jdbms.sql.parsing.expressions.util;

import jdbms.sql.errors.ErrorHandler;
import jdbms.sql.parsing.util.Constants;

/**
 * The column expression class.
 */
public class ColumnExpression {

	private String expression;
	
	/**
	 * Instantiates a new column expression.
	 * @param expression the column expression
	 */
	public ColumnExpression(String expression) {
		this.expression = expression.trim();
	}

	/**
	 * Checks if is a valid column name.
	 * @return true, if is valid column name
	 */
	public boolean isValidColumnName() {
		if (Constants.RESERVED_KEYWORDS.contains(expression.toUpperCase())) {
			ErrorHandler.printReservedKeywordError(expression);
			return false;
		}
		return this.expression.matches(Constants.COLUMN_REGEX);
	}
	
	/**
	 * Gets the column expression.
	 * @return the column expression
	 */
	public String getExpression() {
		return expression;
	}
}
