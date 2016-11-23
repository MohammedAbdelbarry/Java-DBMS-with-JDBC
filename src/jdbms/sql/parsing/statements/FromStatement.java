package jdbms.sql.parsing.statements;

import jdbms.sql.parsing.expressions.Expression;

public class FromStatement extends Statement {
	private static final String STATEMENT_IDENTIFIER = "FROM";

	public FromStatement(Expression nextExpression) {
		super(nextExpression, STATEMENT_IDENTIFIER);
	}

}
