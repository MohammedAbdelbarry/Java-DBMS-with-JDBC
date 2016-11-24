package jdbms.sql.parsing.statements;

import jdbms.sql.parsing.expressions.ColumnWildcardExpression;
import jdbms.sql.parsing.expressions.SelectColumnListExpression;
import jdbms.sql.parsing.statements.util.StatementFactory;

public class SelectStatement extends Statement {
	private static final String STATEMENT_IDENTIFIER = "SELECT";
	private static final String CLASS_ID = "SELECTSTATEMENTCLASS";
	static {
		StatementFactory.getInstance().registerStatement(CLASS_ID, SelectStatement.class);
	}
	public SelectStatement() {
		super(null, STATEMENT_IDENTIFIER);
	}

	@Override
	public boolean interpret(String sqlExpression) {
		if (sqlExpression.startsWith(statementIdentifier)) {
			String restOfExpression = sqlExpression.substring(sqlExpression.indexOf(" "));
			if (new ColumnWildcardExpression().interpret(restOfExpression)) {
				return true;
			} else if (new SelectColumnListExpression().interpret(restOfExpression)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
