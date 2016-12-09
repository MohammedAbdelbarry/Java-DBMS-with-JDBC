package jdbms.sql.parsing.statements;

import jdbms.sql.parsing.expressions.columns.names.TerminatingColumnListExpression;
import jdbms.sql.parsing.properties.InputParametersContainer;

/**
 * The Class DropColumnStatement.
 */
public class DropColumnStatement implements Statement {
	
	private static final String
	STATEMENT_IDENTIFIER = "DROP COLUMN";
	private InputParametersContainer parameters;
	
	/**
	 * Instantiates a new drop column statement.*
	 * @param parameters the input parameters
	 */
	public DropColumnStatement(InputParametersContainer parameters) {
		this.parameters = parameters;
	}

	@Override
	public boolean interpret(String sqlExpression) {
		if (sqlExpression.startsWith(STATEMENT_IDENTIFIER)) {
			String restOfExpression = sqlExpression.
					replaceFirst(STATEMENT_IDENTIFIER, "").trim();
			return new TerminatingColumnListExpression(parameters).
					interpret(restOfExpression);
		}
		return false;
	}
}
