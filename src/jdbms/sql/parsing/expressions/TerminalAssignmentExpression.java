package jdbms.sql.parsing.expressions;

import jdbms.sql.parsing.properties.InputParametersContainer;

/**
 * The Class TerminalAssignmentExpression.
 */
public class TerminalAssignmentExpression extends AssignmentListExpression {

	/**
	 * Instantiates a new terminal assignment expression.
	 * @param parameters the input parameters
	 */
	public TerminalAssignmentExpression(
			InputParametersContainer parameters) {
		super(new TerminalExpression(parameters), parameters);
	}
}
