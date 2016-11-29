package jdbms.sql.parsing.expressions;

import jdbms.sql.parsing.properties.InputParametersContainer;

/**
 * The Class Table Creation Table Name Expression.
 */
public class TableCreationTableNameExpression extends TableNameExpression {

	/**
	 * Instantiates a new table creation table name expression.
	 * @param parameters the input parameters
	 */
	public TableCreationTableNameExpression(
			InputParametersContainer parameters) {
		super(new TableCreationColumnsTypesExpression(parameters),
				parameters);
	}
}
