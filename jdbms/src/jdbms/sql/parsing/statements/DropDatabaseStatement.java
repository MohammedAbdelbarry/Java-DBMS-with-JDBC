package jdbms.sql.parsing.statements;

import jdbms.sql.parsing.statements.util.StatementFactory;

public class DropDatabaseStatement extends Statement {
	private static final String STATEMENT_IDENTIFIER = "DROP DATABASE";
	private static final String CLASS_ID = "DROPDATABASESTATEMENTCLASS";
	static {
		StatementFactory.getInstance().registerStatement(CLASS_ID, DropDatabaseStatement.class);
	}
	public DropDatabaseStatement() {
		super(null, STATEMENT_IDENTIFIER);
	}

}
