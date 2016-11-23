package jdbms.sql.parsing.statements;

import jdbms.sql.parsing.expressions.BooleanExpression;

public class WhereStatement extends Statement {
	private static final String STATEMENT_IDENTIFIER = "WHERE";
	public WhereStatement() {
		super(new BooleanExpression(), STATEMENT_IDENTIFIER);
	}
}
