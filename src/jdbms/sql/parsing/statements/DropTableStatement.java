package jdbms.sql.parsing.statements;

import jdbms.sql.parsing.expressions.TerminatingTableExpression;
import jdbms.sql.parsing.statements.util.StatementFactory;

public class DropTableStatement extends InitialStatement {
	private static final String STATEMENT_IDENTIFIER = "DROP TABLE";
	private static final String CLASS_ID = "DROPTABLESTATEMENTCLASS";
	static {
		StatementFactory.getInstance().registerStatement(CLASS_ID, DropTableStatement.class);
	}

	public DropTableStatement() {
	}

	@Override
	public boolean interpret(String sqlExpression) {
		if (sqlExpression.startsWith(STATEMENT_IDENTIFIER)) {
			String restOfExpression = sqlExpression.replace(STATEMENT_IDENTIFIER, "").trim();
			return new TerminatingTableExpression(parameters).interpret(restOfExpression);
		}
		return false;
	}

	@Override
	public void act() {
		// TODO Auto-generated method stub

	}
}
