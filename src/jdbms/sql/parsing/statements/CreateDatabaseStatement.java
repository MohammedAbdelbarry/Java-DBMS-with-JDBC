package jdbms.sql.parsing.statements;

import jdbms.sql.parsing.statements.util.StatementFactory;

public class CreateDatabaseStatement extends Statement {
	private static final String STATEMENT_IDENTIFIER = "CREATE DATABASE";
	private static final String CLASS_ID = "CREATEDATABASESTATEMENTCLASS";
	static {
		StatementFactory.getInstance().registerStatement(CLASS_ID, CreateDatabaseStatement.class);
	}
	public CreateDatabaseStatement() {
		super(null, STATEMENT_IDENTIFIER);
	}

}
