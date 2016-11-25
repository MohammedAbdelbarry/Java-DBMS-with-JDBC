package jdbms.sql.parsing.statements;

import jdbms.sql.parsing.expressions.ConditionalAssignmentListExpression;
import jdbms.sql.parsing.expressions.TerminalAssignmentExpression;

public class SetStatement implements Statement {
	private static final String STATEMENT_IDENTIFIER = "SET";

	public SetStatement() {
	}
	
	@Override
	public boolean interpret(String sqlExpression) {
		if (sqlExpression.startsWith(STATEMENT_IDENTIFIER)) {
			String restOfExpression = sqlExpression.replace(STATEMENT_IDENTIFIER, "").trim();
			return new ConditionalAssignmentListExpression().interpret(restOfExpression) ||
					new TerminalAssignmentExpression().interpret(restOfExpression);
		}
		return false;
	}
}
