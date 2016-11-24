package jdbms.sql.parsing.expressions.math;

import jbdms.sql.datatypes.IntSQLType;
import jbdms.sql.datatypes.VarcharSQLType;
import jdbms.sql.parsing.expressions.math.util.BooleanExpressionFactory;

public class EqualsExpression extends BooleanExpression {
	private static final String SYMBOL = "=";
	static {
		BooleanExpressionFactory.getInstance().
		registerBoolExpression(SYMBOL, EqualsExpression.class);
	}
	public EqualsExpression() {
		super(SYMBOL);
	}
	@Override
	public boolean evaluate(VarcharSQLType left, VarcharSQLType right) {
		return left.compareTo(right) == 0;
	}
	@Override
	public boolean evaluate(IntSQLType left, IntSQLType right) {
		return left.compareTo(right) == 0;
	}

}
