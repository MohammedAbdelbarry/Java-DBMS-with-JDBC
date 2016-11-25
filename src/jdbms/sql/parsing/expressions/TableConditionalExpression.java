package jdbms.sql.parsing.expressions;

import jdbms.sql.parsing.properties.InputParametersContainer;
import jdbms.sql.parsing.statements.WhereStatement;

public class TableConditionalExpression extends TableNameExpression {

	public TableConditionalExpression(
			InputParametersContainer parameters) {
		super(new WhereStatement(parameters), parameters);
	}
}
