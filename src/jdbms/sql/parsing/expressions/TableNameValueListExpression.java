package jdbms.sql.parsing.expressions;

import jdbms.sql.parsing.properties.InputParametersContainer;
import jdbms.sql.parsing.statements.ValueStatement;

public class TableNameValueListExpression extends TableNameExpression {

	public TableNameValueListExpression(
			InputParametersContainer parameters) {
		super(new ValueStatement(parameters), parameters);
	}
}
