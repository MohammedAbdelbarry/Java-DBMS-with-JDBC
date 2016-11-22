package jdbms.sql.parsing.statements;

import jdbms.sql.parsing.statements.util.StatementFactory;

public class DropTableStatement extends Statement {
	private static final String STATEMENT_IDENTIFIER = "DROP TABLE";
	private static final String CLASS_ID = "DROPTABLESTATEMENTCLASS";
	static {
		StatementFactory.getInstance().registerStatement(CLASS_ID, DropTableStatement.class);
	}
	public DropTableStatement() {
		super(null, STATEMENT_IDENTIFIER);
	}

}
