package jdbms.sql.parsing.statements;

import jdbms.sql.parsing.expressions.TableUpdateTableNameExpression;
import jdbms.sql.parsing.expressions.math.BooleanExpression;
public class WhereStatement implements Statement {
	private static final String STATEMENT_IDENTIFIER = "WHERE";

	public WhereStatement() {
	}

	@Override
	public boolean interpret(String sqlExpression) {
		if (sqlExpression.startsWith(STATEMENT_IDENTIFIER)) {
			String restOfExpression = sqlExpression.replace(STATEMENT_IDENTIFIER, "").trim();
			//return new BooleanExpression().interpret(restOfExpression);
		}
		return false;
	}
}
