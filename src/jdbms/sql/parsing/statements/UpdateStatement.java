package jdbms.sql.parsing.statements;

import jdbms.sql.parsing.expressions.TableUpdateTableNameExpression;
import jdbms.sql.parsing.statements.util.StatementFactory;

public class UpdateStatement implements Statement {
	private static final String STATEMENT_IDENTIFIER = "UPDATE";
	private static final String CLASS_ID = "UPDATESTATEMENTCLASS";
	static {
		StatementFactory.getInstance().registerStatement(CLASS_ID, SelectStatement.class);
	}
	public UpdateStatement() {
	}

	@Override
	public boolean interpret(String sqlExpression) {
		if (sqlExpression.startsWith(STATEMENT_IDENTIFIER)) {
			String restOfExpression = sqlExpression.replace(STATEMENT_IDENTIFIER, "").trim();
			return new TableUpdateTableNameExpression().interpret(restOfExpression);
		}
		return false;
	}
}
