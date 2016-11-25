package jdbms.sql.parsing.statements;

import jdbms.sql.parsing.expressions.TableNameColumnListExpression;
import jdbms.sql.parsing.expressions.TableNameValueListExpression;
import jdbms.sql.parsing.statements.util.StatementFactory;

public class InsertIntoStatement extends InitialStatement {
	private static final String STATEMENT_IDENTIFIER = "INSERT INTO";
	private static final String CLASS_ID = "INSERTINTOSTATEMENTCLASS";
	static {
		StatementFactory.getInstance().registerStatement(CLASS_ID, InsertIntoStatement.class);
	}
	public InsertIntoStatement() {
	}

	@Override
	public boolean interpret(String sqlExpression) {
		if (sqlExpression.startsWith(STATEMENT_IDENTIFIER)) {
			String restOfExpression = sqlExpression.replace(STATEMENT_IDENTIFIER, "").trim();
			if (new TableNameColumnListExpression(parameters).interpret(restOfExpression) ||
					new TableNameValueListExpression(parameters).interpret(restOfExpression)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void act() {
		// TODO Auto-generated method stub

	}

}
