package jdbms.sql.parsing.statements;

import jdbms.sql.parsing.statements.util.StatementFactory;

public class InsertIntoStatement extends Statement {
	private static final String STATEMENT_IDENTIFIER = "INSERT INTO";
	private static final String CLASS_ID = "INSERTINTOSTATEMENTCLASS";
	static {
		StatementFactory.getInstance().registerStatement(CLASS_ID, DropTableStatement.class);
	}
	public InsertIntoStatement() {
		super(null, STATEMENT_IDENTIFIER);
	}

}
