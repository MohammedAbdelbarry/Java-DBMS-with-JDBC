package jdbms.sql.parsing.statements;

import jdbms.sql.parsing.expressions.OrderByColumnListExpression;
import jdbms.sql.parsing.properties.InputParametersContainer;

/**
 * Order by Statement Class.
 */
public class OrderByStatement implements Statement {
	
	private static final String STATEMENT_IDENTIFIER = "ORDER BY";
	private InputParametersContainer parameters;
	
	/**
	 * Instantiates a new order by statement.
	 * @param parameters the input parameters
	 */
	public OrderByStatement(InputParametersContainer parameters) {
		this.parameters = parameters;
	}

	@Override
	public boolean interpret(String sqlExpression) {
		if (sqlExpression.startsWith(STATEMENT_IDENTIFIER)) {
			String restOfExpression = sqlExpression.replaceFirst(STATEMENT_IDENTIFIER, "").trim();
			return new OrderByColumnListExpression(parameters).interpret(restOfExpression);
		}
		return false;
	}
}
