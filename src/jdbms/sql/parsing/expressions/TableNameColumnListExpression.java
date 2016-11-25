package jdbms.sql.parsing.expressions;

import jdbms.sql.parsing.properties.InputParametersContainer;

public class TableNameColumnListExpression extends TableNameExpression {

	public TableNameColumnListExpression(
			InputParametersContainer parameters) {
		super(new InsertColumnListExpression(parameters), parameters);
	}
}
