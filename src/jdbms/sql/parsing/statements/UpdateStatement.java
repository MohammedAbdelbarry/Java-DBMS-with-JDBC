package jdbms.sql.parsing.statements;

import jdbms.sql.parsing.expressions.TableUpdateTableNameExpression;
import jdbms.sql.parsing.statements.util.StatementFactory;

public class UpdateStatement extends InitialStatement {
	private static final String STATEMENT_IDENTIFIER = "UPDATE";
	private static final String CLASS_ID = "UPDATESTATEMENTCLASS";
	static {
		StatementFactory.getInstance().registerStatement(CLASS_ID, UpdateStatement.class);
	}
	public UpdateStatement() {
	}

	@Override
	public boolean interpret(String sqlExpression) {
		if (sqlExpression.startsWith(STATEMENT_IDENTIFIER)) {
			String restOfExpression = sqlExpression.replace(STATEMENT_IDENTIFIER, "").trim();
			return new TableUpdateTableNameExpression(parameters).interpret(restOfExpression);
		}
		return false;
	}

	@Override
	public void act() {
		// TODO Auto-generated method stub

	}
}
