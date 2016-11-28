package jdbms.sql.parsing.expressions;

import jdbms.sql.errors.ErrorHandler;
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
		ErrorHandler.printSyntaxErrorNear("Command end.");
		return false;
	}
}
