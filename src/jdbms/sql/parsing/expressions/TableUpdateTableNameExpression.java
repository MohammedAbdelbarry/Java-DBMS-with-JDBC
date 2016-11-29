package jdbms.sql.parsing.expressions;

import jdbms.sql.parsing.properties.InputParametersContainer;
import jdbms.sql.parsing.statements.SetStatement;

/**
 * The Class Table Update Table Name Expression.
 */
public class TableUpdateTableNameExpression extends TableNameExpression {

	/**
	 * Instantiates a new table update table name expression.
	 * @param parameters the input parameters
	 */
	public TableUpdateTableNameExpression(
			InputParametersContainer parameters) {
		super(new SetStatement(parameters), parameters);
	}
}
