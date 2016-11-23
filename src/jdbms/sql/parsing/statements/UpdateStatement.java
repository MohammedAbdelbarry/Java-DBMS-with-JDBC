package jdbms.sql.parsing.statements;

import jdbms.sql.parsing.statements.util.StatementFactory;

public class UpdateStatement extends Statement {
	private static final String STATEMENT_IDENTIFIER = "UPDATE";
	private static final String CLASS_ID = "UPDATESTATEMENTCLASS";
	static {
		StatementFactory.getInstance().registerStatement(CLASS_ID, SelectStatement.class);
	}
	public UpdateStatement() {
		super(null, STATEMENT_IDENTIFIER);
	}

}
