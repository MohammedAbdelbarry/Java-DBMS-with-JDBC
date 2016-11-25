package jdbms.sql.parsing.expressions;

import jdbms.sql.parsing.properties.InputParametersContainer;

public class TerminalAssignmentExpression extends AssignmentListExpression {

	public TerminalAssignmentExpression(
			InputParametersContainer parameters) {
		super(new TerminalExpression(parameters), parameters);
	}
}
