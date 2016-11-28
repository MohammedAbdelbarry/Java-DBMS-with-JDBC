package jdbms.sql.parsing.statements;

import jdbms.sql.parsing.expressions.DefineColumnExpression;
import jdbms.sql.parsing.properties.InputParametersContainer;

public class AddStatement implements Statement {
	private static final String STATEMENT_IDENTIFIER = "ADD";
	private InputParametersContainer parameters;
	public AddStatement(InputParametersContainer parameters) {
		this.parameters = parameters;
	}

	@Override
	public boolean interpret(String sqlExpression) {
		if (sqlExpression.startsWith(STATEMENT_IDENTIFIER)) {
			String restOfExpression = sqlExpression.replaceFirst(STATEMENT_IDENTIFIER, "").trim();
			return new DefineColumnExpression(parameters).interpret(restOfExpression);
		}
		return false;
	}
}
