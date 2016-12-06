package jdbms.sql.parsing.statements;

import jdbms.sql.errors.ErrorHandler;
import jdbms.sql.parsing.expressions.OrderByTableNameExpression;
import jdbms.sql.parsing.expressions.TableConditionalExpression;
import jdbms.sql.parsing.expressions.TerminatingTableExpression;
import jdbms.sql.parsing.properties.InputParametersContainer;

/**
 * The Class FromStatement.
 */
public class FromStatement implements Statement {
	
	private static final String STATEMENT_IDENTIFIER = "FROM";
	private InputParametersContainer parameters;
	
	/**
	 * Instantiates a new from statement.
	 * @param parameters the input parameters
	 */
	public FromStatement(InputParametersContainer parameters) {
		this.parameters = parameters;
	}

	@Override
	public boolean interpret(String sqlExpression) {
		if (sqlExpression.startsWith(STATEMENT_IDENTIFIER)) {
			String restOfExpression = sqlExpression.replaceFirst(STATEMENT_IDENTIFIER, "").trim();
			if (new TerminatingTableExpression(parameters).interpret(restOfExpression) ||
					new TableConditionalExpression(parameters).interpret(restOfExpression)
					|| new OrderByTableNameExpression(parameters).interpret(restOfExpression)) {
				return true;
			}
		}
		ErrorHandler.printSyntaxErrorNear("From");
		return false;
	}
}
