package jdbms.sql.parsing.expressions.math;

import jdbms.sql.datatypes.IntSQLType;
import jdbms.sql.datatypes.VarcharSQLType;
import jdbms.sql.parsing.expressions.TerminalExpression;
import jdbms.sql.parsing.operators.BinaryOperator;
import jdbms.sql.parsing.properties.InputParametersContainer;

public abstract class BooleanExpression extends BinaryExpression {
	private BinaryOperator operator;
	public BooleanExpression(String symbol,
			InputParametersContainer parameters) {
		super(symbol, new TerminalExpression(parameters), parameters);
		this.operator = new BinaryOperator(symbol);
	}
	@Override
	public boolean interpret(String sqlExpression) {
		sqlExpression = sqlExpression.trim();
		if(!sqlExpression.contains(this.operator.getSymbol())) {
			if (sqlExpression.trim().startsWith("TRUE")) {
				sqlExpression = sqlExpression.trim().replace("TRUE", "1 = 1");
			} else if (sqlExpression.trim().startsWith("FALSE")) {
				sqlExpression = sqlExpression.trim().replace("FALSE", "1 > 1");
			} else {
				return false;
			}
		}
		boolean isBinaryExpression = super.interpret(sqlExpression);
		parameters.setCondition(this);
		return isBinaryExpression;
	}
	public abstract boolean evaluate(VarcharSQLType left, VarcharSQLType right);
	public abstract boolean evaluate(IntSQLType left, IntSQLType right);
	public abstract boolean evaluateConstantExpression();
}
