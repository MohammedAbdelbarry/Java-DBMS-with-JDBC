package jdbms.sql.parsing.expressions;

import jdbms.sql.parsing.properties.InputParametersContainer;

public class TerminatingTableExpression extends TableNameExpression {

	public TerminatingTableExpression(
			InputParametersContainer parameters) {
		super(new TerminalExpression(parameters), parameters);
	}
}
