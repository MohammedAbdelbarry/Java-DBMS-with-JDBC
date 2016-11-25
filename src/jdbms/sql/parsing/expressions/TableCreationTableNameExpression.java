package jdbms.sql.parsing.expressions;

import jdbms.sql.parsing.properties.InputParametersContainer;

public class TableCreationTableNameExpression extends TableNameExpression {

	public TableCreationTableNameExpression(
			InputParametersContainer parameters) {
		super(new TableCreationColumnsTypesExpression(parameters),
				parameters);
	}
}
