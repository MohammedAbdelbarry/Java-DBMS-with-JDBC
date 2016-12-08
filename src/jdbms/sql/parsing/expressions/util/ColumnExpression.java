package jdbms.sql.parsing.expressions.util;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expression == null) ? 0 : expression.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ColumnExpression other = (ColumnExpression) obj;
		if (expression == null) {
			if (other.expression != null)
				return false;
		} else if (!expression.equals(other.expression))
			return false;
		return true;
	}
}
