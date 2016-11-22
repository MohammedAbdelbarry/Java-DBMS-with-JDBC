package jdbms.sql.parsing.statements;

import jdbms.sql.parsing.statements.util.StatementFactory;

public class CreateTableStatement extends Statement {
	private static final String STATEMENT_IDENTIFIER = "CREATE TABLE";
	private static final String CLASS_ID = "CREATETABLESTATEMENTCLASS";
	static {
		StatementFactory.getInstance().registerStatement(CLASS_ID, CreateTableStatement.class);
	}
	public CreateTableStatement() {
		super(null, STATEMENT_IDENTIFIER);
	}

}
