package jdbms.sql.parsing.statements;

import jdbms.sql.parsing.expressions.Expression;

public class SetStatement extends Statement {
	private static final String STATEMENT_IDENTIFIER = "SET";
	public SetStatement(Expression nextExpression) {
		super(nextExpression, STATEMENT_IDENTIFIER);
	}

}
