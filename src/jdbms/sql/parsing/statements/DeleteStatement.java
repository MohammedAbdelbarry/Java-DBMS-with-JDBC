package jdbms.sql.parsing.statements;

import jdbms.sql.parsing.statements.util.StatementFactory;

public class DeleteStatement implements Statement {
	private static final String STATEMENT_IDENTIFIER = "DELETE";
	private static final String CLASS_ID = "DELETESTATEMENTCLASS";
	static {
		StatementFactory.getInstance().registerStatement(CLASS_ID, DropDatabaseStatement.class);
	}
	public DeleteStatement() {
	}

	@Override
	public boolean interpret(String sqlExpression) {
		if (sqlExpression.startsWith(STATEMENT_IDENTIFIER)) {
			String restOfExpression = sqlExpression.replace(STATEMENT_IDENTIFIER, "").trim();
			return new FromStatement().interpret(restOfExpression);
		}
		return false;
	}
}
