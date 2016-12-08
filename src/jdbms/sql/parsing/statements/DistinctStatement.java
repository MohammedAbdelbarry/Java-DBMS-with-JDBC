package jdbms.sql.parsing.statements;

import jdbms.sql.parsing.expressions.columns.names.ColumnWildcardExpression;
import jdbms.sql.parsing.expressions.columns.names.SelectColumnListExpression;
import jdbms.sql.parsing.properties.InputParametersContainer;

/**
 * The Class DistinctStatement.
 */
public class DistinctStatement implements Statement {
	
	private static final String
	STATEMENT_IDENTIFIER = "DISTINCT";
	private InputParametersContainer parameters;
	
	/**
	 * Instantiates a new distinct statement.*
	 * @param parameters the input parameters
	 */
	public DistinctStatement(InputParametersContainer parameters) {
		this.parameters = parameters;
	}

	@Override
	public boolean interpret(String sqlExpression) {
		if (sqlExpression.startsWith(STATEMENT_IDENTIFIER)) {
			String restOfExpression = sqlExpression.
					replaceFirst(STATEMENT_IDENTIFIER
							, "").trim();
			this.parameters.setDistinct(true);
			if (new ColumnWildcardExpression(
					parameters).interpret(restOfExpression) ||
					new SelectColumnListExpression(
							parameters).interpret(restOfExpression)) {
				return true;
			}
		}
		return false;
	}
}
