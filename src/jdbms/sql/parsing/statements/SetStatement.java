package jdbms.sql.parsing.statements;

import jdbms.sql.parsing.expressions.UpdateAssignmentListExpression;

public class SetStatement implements Statement {
	private static final String STATEMENT_IDENTIFIER = "SET";

	public SetStatement() {
	}
	
	@Override
	public boolean interpret(String sqlExpression) {
		if (sqlExpression.startsWith(STATEMENT_IDENTIFIER)) {
			String restOfExpression = sqlExpression.replace(STATEMENT_IDENTIFIER, "").trim();
			return new UpdateAssignmentListExpression().interpret(restOfExpression);
		}
		return false;
	}
}
