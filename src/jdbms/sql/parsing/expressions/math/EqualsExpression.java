package jdbms.sql.parsing.expressions.math;

import jbdms.sql.datatypes.IntSQLType;
import jbdms.sql.datatypes.VarcharSQLType;

public class EqualsExpression extends BooleanExpression {
	private static final String SYMBOL = "=";
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
