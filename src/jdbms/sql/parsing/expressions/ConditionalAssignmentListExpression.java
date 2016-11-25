package jdbms.sql.parsing.expressions;

import jdbms.sql.parsing.statements.WhereStatement;

public class ConditionalAssignmentListExpression extends AssignmentListExpression {

	public ConditionalAssignmentListExpression() {
		super(new WhereStatement());
	}
}
