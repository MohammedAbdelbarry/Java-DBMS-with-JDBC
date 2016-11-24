package jdbms.sql.parsing.expressions.math;

import jbdms.sql.datatypes.IntSQLType;
import jbdms.sql.datatypes.VarcharSQLType;

public abstract class BooleanExpression extends BinaryExpression {

	public BooleanExpression(String symbol) {
		super(symbol);
	}

	public abstract boolean evaluate(VarcharSQLType left, VarcharSQLType right);
	public abstract boolean evaluate(IntSQLType left, IntSQLType right);
}
