package jdbms.sql.parsing.expressions;

import jdbms.sql.parsing.properties.InputParametersContainer;
import jdbms.sql.parsing.statements.SetStatement;

public class TableUpdateTableNameExpression extends TableNameExpression {

	public TableUpdateTableNameExpression(
			InputParametersContainer parameters) {
		super(new SetStatement(parameters), parameters);
	}
}
