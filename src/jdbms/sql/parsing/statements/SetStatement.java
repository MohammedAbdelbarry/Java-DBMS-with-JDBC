package jdbms.sql.parsing.statements;

import jdbms.sql.errors.ErrorHandler;
import jdbms.sql.parsing.expressions.ConditionalAssignmentListExpression;
import jdbms.sql.parsing.expressions.TerminalAssignmentExpression;
import jdbms.sql.parsing.properties.InputParametersContainer;
/**
 * The Class Set Statement.
 */
public class SetStatement implements Statement {
	
	private static final String STATEMENT_IDENTIFIER = "SET";
	private InputParametersContainer parameters;
	
	/**
	 * Instantiates a new set statement.
	 * @param parameters the input parameters
	 */
	public SetStatement(InputParametersContainer parameters) {
		this.parameters = parameters;
	}

	@Override
	public boolean interpret(String sqlExpression) {
		if (sqlExpression.startsWith(STATEMENT_IDENTIFIER)) {
			String restOfExpression
			= sqlExpression.replaceFirst(STATEMENT_IDENTIFIER,
					"").trim();
			return new ConditionalAssignmentListExpression(
					parameters).interpret(restOfExpression)
					|| new TerminalAssignmentExpression(
							parameters).interpret(restOfExpression);
		}
		ErrorHandler.printSyntaxErrorNear("Set");
		return false;
	}
}
