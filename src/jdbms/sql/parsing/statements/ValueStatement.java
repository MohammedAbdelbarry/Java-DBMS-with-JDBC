package jdbms.sql.parsing.statements;

import jdbms.sql.parsing.expressions.InsertIntoValueListExpression;
import jdbms.sql.parsing.properties.InputParametersContainer;

public class ValueStatement implements Statement {
	private static final String STATEMENT_IDENTIFIER = "VALUES";
	private InputParametersContainer parameters;
	public ValueStatement(InputParametersContainer parameters) {
	}

	@Override
	public boolean interpret(String sqlExpression) {
		if (sqlExpression.startsWith(STATEMENT_IDENTIFIER)) {
			String restOfExpression = sqlExpression.replace(STATEMENT_IDENTIFIER, "").trim();
			return new InsertIntoValueListExpression(parameters).interpret(restOfExpression);
		}
		return false;
	}
}
