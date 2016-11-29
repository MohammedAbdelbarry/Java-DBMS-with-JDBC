package jdbms.sql.parsing.expressions;

import jdbms.sql.parsing.properties.InputParametersContainer;
import jdbms.sql.parsing.statements.WhereStatement;

/**
 * The Class ConditionalAssignmentListExpression.
 */
public class ConditionalAssignmentListExpression
extends AssignmentListExpression {

	/**
	 * Instantiates a new conditional assignment list expression.
	 * @param parameters the input parameters
	 */
	public ConditionalAssignmentListExpression(
			InputParametersContainer parameters) {
		super(new WhereStatement(parameters), parameters);
	}
}
