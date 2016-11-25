package jdbms.sql.parsing.statements;

import jdbms.sql.parsing.expressions.DatabaseTerminatingExpression;
import jdbms.sql.parsing.statements.util.StatementFactory;

public class CreateDatabaseStatement extends InitialStatement {
	private static final String STATEMENT_IDENTIFIER = "CREATE DATABASE";
	private static final String CLASS_ID = "CREATEDATABASESTATEMENTCLASS";
	static {
		StatementFactory.getInstance().registerStatement(CLASS_ID, CreateDatabaseStatement.class);
	}

	public CreateDatabaseStatement() {
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
