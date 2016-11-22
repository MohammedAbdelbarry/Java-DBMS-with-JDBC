package jdbms.sql.parsing.statements;

import jdbms.sql.parsing.statements.util.StatementFactory;

public class SelectStatement extends Statement {
	private static final String STATEMENT_IDENTIFIER = "SELECT";
	private static final String CLASS_ID = "SELECTSTATEMENTCLASS";
	static {
		StatementFactory.getInstance().registerStatement(CLASS_ID, SelectStatement.class);
	}
	public SelectStatement() {
		super(null, STATEMENT_IDENTIFIER);
	}

}
