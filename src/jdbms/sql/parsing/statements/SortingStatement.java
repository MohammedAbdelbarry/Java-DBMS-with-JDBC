package jdbms.sql.parsing.statements;

import jdbms.sql.parsing.expressions.TerminalExpression;
import jdbms.sql.parsing.properties.InputParametersContainer;

/**
 * The Class Set Statement.
 */
public class SortingStatement implements Statement {

	private InputParametersContainer parameters;

	/**
	 * Instantiates a new sorting statement.
	 * @param parameters the input parameters
	 */
	public SortingStatement(InputParametersContainer parameters) {
		this.parameters = parameters;
	}

	@Override
	public boolean interpret(String sqlExpression) {
		if (sqlExpression.startsWith("ASC")) {
			this.parameters.setAscending(true);
		} else if (sqlExpression.startsWith("DESC")){
			this.parameters.setAscending(false);
		} else {
			return false;
		}
		return new TerminalExpression(parameters).
				interpret(sqlExpression.substring(
						sqlExpression.indexOf(" ")).trim());
	}
}
