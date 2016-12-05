package jdbms.sql.parsing.expressions;

import jdbms.sql.parsing.properties.InputParametersContainer;
import jdbms.sql.parsing.statements.DropColumnStatement;

/**
 * The drop column table name expression class.
 */
public class DropColumnTableNameExpression extends TableNameExpression {

	/**
	 * Instantiates a new table name expression for droping columns.
	 * @param parameters the input parameters
	 */
	public DropColumnTableNameExpression(InputParametersContainer
			parameters) {
		super(new DropColumnStatement(parameters), parameters);
	}
}
