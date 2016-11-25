package jdbms.sql.parsing.statements;

import jdbms.sql.parsing.expressions.DatabaseTerminatingExpression;
import jdbms.sql.parsing.statements.util.StatementFactory;

public class DropDatabaseStatement extends InitialStatement {
	private static final String STATEMENT_IDENTIFIER = "DROP DATABASE";
	private static final String CLASS_ID = "DROPDATABASESTATEMENTCLASS";
	static {
		StatementFactory.getInstance().registerStatement(CLASS_ID, DropDatabaseStatement.class);
	}
	public DropDatabaseStatement() {
	}

	@Override
	public boolean interpret(String sqlExpression) {
		if (sqlExpression.startsWith(STATEMENT_IDENTIFIER)) {
			String restOfExpression = sqlExpression.replace(STATEMENT_IDENTIFIER, "").trim();
			return new DatabaseTerminatingExpression(parameters).interpret(restOfExpression);
		}
		return false;
	}

	@Override
	public void act() {
		// TODO Auto-generated method stub

	}
}
