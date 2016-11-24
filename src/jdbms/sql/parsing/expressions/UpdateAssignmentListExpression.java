package jdbms.sql.parsing.expressions;

import jdbms.sql.parsing.expressions.math.AssignmentExpression;
import jdbms.sql.parsing.statements.WhereStatement;

public class UpdateAssignmentListExpression extends AssignmentListExpression {

	public UpdateAssignmentListExpression() {
		super(new WhereStatement());
	}

	@Override
	public boolean interpret(String sqlExpression) {
		String[] parts = sqlExpression.split(",");
		for (int i = 0; i < parts.length - 1; i++) {
			if (!new AssignmentExpression().interpret(parts[i])) {
				return false;
			}
		}
		return super.interpret(parts[parts.length - 1].trim());
	}
}
