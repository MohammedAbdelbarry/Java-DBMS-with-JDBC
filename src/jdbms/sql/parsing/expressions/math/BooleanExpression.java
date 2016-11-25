package jdbms.sql.parsing.expressions.math;

import jdbms.sql.datatypes.IntSQLType;
import jdbms.sql.datatypes.VarcharSQLType;
import jdbms.sql.parsing.expressions.TerminalExpression;

public abstract class BooleanExpression extends BinaryExpression {

	public BooleanExpression(String symbol) {
		super(symbol, new TerminalExpression());
	}

	public abstract boolean evaluate(VarcharSQLType left, VarcharSQLType right);
	public abstract boolean evaluate(IntSQLType left, IntSQLType right);
}
