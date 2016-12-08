package jdbms.sql.parsing.statements;

import jdbms.sql.parsing.expressions.values.InsertIntoValueListExpression;
import jdbms.sql.parsing.properties.InputParametersContainer;

/**
 * The Class Value Statement.
 */
public class ValueStatement implements Statement {
	
	private static final String STATEMENT_IDENTIFIER = "VALUES";
	private InputParametersContainer parameters;
	
	/**
	 * Instantiates a new value statement.
	 * @param parameters the parameters
	 */
	public ValueStatement(InputParametersContainer parameters) {
		this.parameters = parameters;
	}

	@Override
	public boolean interpret(String sqlExpression) {
		if (sqlExpression.startsWith(STATEMENT_IDENTIFIER)) {
			String restOfExpression = sqlExpression.replaceFirst(STATEMENT_IDENTIFIER, "").trim();
			return new InsertIntoValueListExpression(parameters).interpret(restOfExpression);
		}
		return false;
	}
}
