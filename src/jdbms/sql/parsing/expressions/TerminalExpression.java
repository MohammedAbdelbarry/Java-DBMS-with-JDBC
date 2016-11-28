package jdbms.sql.parsing.expressions;

import jdbms.sql.parsing.properties.InputParametersContainer;

public class TerminalExpression implements Expression {

	public TerminalExpression(
			InputParametersContainer parameters) {

	}

	@Override
	public boolean interpret(String sqlExpression) {
		if (sqlExpression.equals(";")) {
			return true;
		}
		return false;
	}
}
