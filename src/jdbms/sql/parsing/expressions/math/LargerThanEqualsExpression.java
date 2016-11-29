package jdbms.sql.parsing.expressions.math;

import jdbms.sql.datatypes.IntSQLType;
import jdbms.sql.datatypes.VarcharSQLType;
import jdbms.sql.parsing.expressions.math.util.BooleanExpressionFactory;
import jdbms.sql.parsing.properties.InputParametersContainer;

/**
 * The larger than equals boolean expression class.
 */
public class LargerThanEqualsExpression extends BooleanExpression {
	private static final String SYMBOL = ">=";
	static {
		BooleanExpressionFactory.getInstance().
		registerBoolExpression(SYMBOL,
				LargerThanEqualsExpression.class);
	}
	/**
	 * Instantiates a new larger than equals expression.
	 * @param parameters the input parameters
	 */
	public LargerThanEqualsExpression(
			InputParametersContainer parameters) {
		super(SYMBOL, parameters);
	}
	
	@Override
	public boolean evaluate(VarcharSQLType left,
			VarcharSQLType right) {
		return left.compareTo(right) >= 0;
	}
	@Override
	public boolean evaluate(IntSQLType left, IntSQLType right) {
		return left.compareTo(right) >= 0;
	}
	@Override
	public boolean evaluateConstantExpression() {
		System.out.println(getLeftOperand() + ":" + getRightOperand());
		return getLeftOperand().compareTo(getRightOperand()) >= 0;
	}
}
