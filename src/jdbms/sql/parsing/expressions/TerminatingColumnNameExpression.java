package jdbms.sql.parsing.expressions;

import jdbms.sql.parsing.expressions.util.ColumnExpression;
import jdbms.sql.parsing.properties.InputParametersContainer;

public class TerminatingColumnNameExpression extends ColumnListExpression {

	public TerminatingColumnNameExpression(
			InputParametersContainer parameters) {
		super(new TerminalExpression(parameters), parameters);
	}

	@Override
	public boolean interpret(String sqlExpression) {
		if (!new ColumnExpression(sqlExpression.substring(0,
				sqlExpression.indexOf(" "))).isValidColumnName()) {
			return false;
		}
		return super.interpret(sqlExpression.substring(sqlExpression.indexOf(" ")).trim());
	}
}
