package jdbms.sql.parsing.expressions.terminal;

import jdbms.sql.parsing.expressions.Expression;
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
			final InputParametersContainer parameters) {

	}

	@Override
	public boolean interpret(final String sqlExpression) {
		return sqlExpression.equals(";");
	}
}
