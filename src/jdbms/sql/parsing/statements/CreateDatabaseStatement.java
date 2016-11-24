package jdbms.sql.parsing.statements;

import jdbms.sql.parsing.expressions.DatabaseTerminatingExpression;
import jdbms.sql.parsing.expressions.Expression;
import jdbms.sql.parsing.statements.util.StatementFactory;

public class CreateDatabaseStatement implements Statement {
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
			return new DatabaseTerminatingExpression().interpret(restOfExpression);
		}
		return false;
	}
}
