package jdbms.sql.parsing.statements;

import jdbms.sql.parsing.expressions.ColumnWildcardExpression;
import jdbms.sql.parsing.expressions.SelectColumnListExpression;
import jdbms.sql.parsing.statements.util.StatementFactory;

public class SelectStatement implements Statement {
	private static final String STATEMENT_IDENTIFIER = "SELECT";
	private static final String CLASS_ID = "SELECTSTATEMENTCLASS";
	static {
		StatementFactory.getInstance().registerStatement(CLASS_ID, SelectStatement.class);
	}
	public SelectStatement() {
	}

	@Override
	public boolean interpret(String sqlExpression) {
		if (sqlExpression.startsWith(STATEMENT_IDENTIFIER)) {
			String restOfExpression = sqlExpression.replace(STATEMENT_IDENTIFIER, "").trim();
			if (new ColumnWildcardExpression().interpret(restOfExpression) ||
					new SelectColumnListExpression().interpret(restOfExpression)) {
				return true;
			}
		} 
		return false;
	}
}
