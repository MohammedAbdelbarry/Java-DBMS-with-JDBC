package jdbms.sql.parsing.expressions;

import jdbms.sql.parsing.properties.InputParametersContainer;
import jdbms.sql.parsing.statements.AddStatement;

public class AddColumnTableNameExpression extends TableNameExpression {

	public AddColumnTableNameExpression(InputParametersContainer parameters) {
		super(new AddStatement(parameters), parameters);
	}
}
