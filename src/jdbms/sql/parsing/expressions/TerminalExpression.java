package jdbms.sql.parsing.expressions;

import jdbms.sql.parsing.properties.InputParametersContainer;

/**
 * The Class TerminalExpression.
 */
public class TerminalExpression implements Expression {

	/**
	 * Instantiates a new terminal expression.
	 * @param parameters the input parameters
	 */
	public TerminalExpression(
			InputParametersContainer parameters) {

	}

	@Override
	public boolean interpret(String sqlExpression) {
		return sqlExpression.equals(";");
	}
}
