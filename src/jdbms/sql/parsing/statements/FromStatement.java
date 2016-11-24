package jdbms.sql.parsing.statements;

import jdbms.sql.parsing.expressions.TableConditionalExpression;
import jdbms.sql.parsing.expressions.TerminatingTableExpression;

public class FromStatement implements Statement {
	private static final String STATEMENT_IDENTIFIER = "FROM";

	public FromStatement() {
	}

	@Override
	public boolean interpret(String sqlExpression) {
		if (sqlExpression.startsWith(STATEMENT_IDENTIFIER)) {
			String restOfExpression = sqlExpression.replace(STATEMENT_IDENTIFIER, "").trim();
			if (new TerminatingTableExpression().interpret(restOfExpression) ||
					new TableConditionalExpression().interpret(restOfExpression)) {
				return true;
			}
		}
		return false;
	}
}
