package jdbms.sql.parsing.expressions;

import jdbms.sql.parsing.properties.InputParametersContainer;
import jdbms.sql.parsing.statements.ValueStatement;

/**
 * The Class Table Name Value List Expression.
 */
public class TableNameValueListExpression extends TableNameExpression {

	/**
	 * Instantiates a new table name value list expression.
	 * @param parameters the input parameters
	 */
	public TableNameValueListExpression(
			InputParametersContainer parameters) {
		super(new ValueStatement(parameters), parameters);
	}
}
