package jdbms.sql.parsing.expressions.table.name;

import jdbms.sql.parsing.properties.InputParametersContainer;
import jdbms.sql.parsing.statements.AddStatement;

/**
 * The add column table name expression class.
 */
public class AddColumnTableNameExpression extends TableNameExpression {

	/**
	 * Instantiates a new table name expression for adding columns.
	 * @param parameters the input parameters
	 */
	public AddColumnTableNameExpression(InputParametersContainer
			parameters) {
		super(new AddStatement(parameters), parameters);
	}
}
