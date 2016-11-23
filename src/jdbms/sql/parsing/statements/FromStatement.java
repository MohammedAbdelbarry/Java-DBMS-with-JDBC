package jdbms.sql.parsing.statements;

public class FromStatement extends Statement {
	private static final String STATEMENT_IDENTIFIER = "FROM";

	public FromStatement() {
		super(null, STATEMENT_IDENTIFIER);
	}

}
