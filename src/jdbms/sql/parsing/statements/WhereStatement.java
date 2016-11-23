package jdbms.sql.parsing.statements;

import jdbms.sql.parsing.expressions.Expression;

public class WhereStatement extends Statement {
	private static final String STATEMENT_IDENTIFIER = "WHERE";
	public WhereStatement(Expression nextExpression) {
		super(nextExpression, STATEMENT_IDENTIFIER);
	}

}
