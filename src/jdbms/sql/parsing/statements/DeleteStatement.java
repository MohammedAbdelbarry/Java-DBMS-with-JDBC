package jdbms.sql.parsing.statements;

import jdbms.sql.parsing.statements.util.StatementFactory;

public class DeleteStatement extends Statement {
	private static final String STATEMENT_IDENTIFIER = "DELETE";
	private static final String CLASS_ID = "DELETESTATEMENTCLASS";
	static {
		StatementFactory.getInstance().registerStatement(CLASS_ID, DropDatabaseStatement.class);
	}
	public DeleteStatement() {
		super(null, STATEMENT_IDENTIFIER);
	}

}
