package jdbms.sql.parsing.expressions;

import jdbms.sql.parsing.properties.InputParametersContainer;
import jdbms.sql.parsing.statements.WhereStatement;

public class ConditionalAssignmentListExpression extends AssignmentListExpression {

	public ConditionalAssignmentListExpression(
			InputParametersContainer parameters) {
		super(new WhereStatement(parameters), parameters);
	}
}
