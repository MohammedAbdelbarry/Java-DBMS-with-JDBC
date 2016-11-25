package jdbms.sql.parsing.expressions.math;

import jdbms.sql.datatypes.IntSQLType;
import jdbms.sql.datatypes.VarcharSQLType;
import jdbms.sql.parsing.expressions.TerminalExpression;
import jdbms.sql.parsing.properties.InputParametersContainer;

public abstract class BooleanExpression extends BinaryExpression {

	public BooleanExpression(String symbol,
			InputParametersContainer parameters) {
		super(symbol, new TerminalExpression(parameters), parameters);
	}
	@Override
	public boolean interpret(String sqlExpression) {
		boolean isBinaryExpression = super.interpret(sqlExpression);
		parameters.setCondition(this);
		return isBinaryExpression;
	}
	public abstract boolean evaluate(VarcharSQLType left, VarcharSQLType right);
	public abstract boolean evaluate(IntSQLType left, IntSQLType right);
}
