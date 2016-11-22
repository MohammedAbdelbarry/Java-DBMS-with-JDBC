package jdbms.sql.parsing.statements;

public class SelectStatement extends Statement {
	private static final String STATEMENT_IDENTIFIER = "SELECT";
	public SelectStatement() {
		super(null, STATEMENT_IDENTIFIER);
	}

}
