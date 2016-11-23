package jdbms.sql.parsing.statements;

import jdbms.sql.parsing.expressions.TableExpression;

public class FromStatement extends Statement {
	private static final String STATEMENT_IDENTIFIER = "FROM";

	public FromStatement() {
		super(new TableExpression(), STATEMENT_IDENTIFIER);
	}
}
