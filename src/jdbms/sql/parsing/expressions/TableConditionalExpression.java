package jdbms.sql.parsing.expressions;

import jdbms.sql.parsing.properties.InputParametersContainer;
import jdbms.sql.parsing.statements.WhereStatement;

/**
 * The Class TableConditionalExpression.
 */
public class TableConditionalExpression extends TableNameExpression {

	/**
	 * Instantiates a new table conditional expression.
	 * @param parameters the input parameters
	 */
	public TableConditionalExpression(
			InputParametersContainer parameters) {
		super(new WhereStatement(parameters), parameters);
	}
}
