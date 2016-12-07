package jdbms.sql.parsing.expressions.math;

import jdbms.sql.datatypes.DateSQLType;
import jdbms.sql.datatypes.DateTimeSQLType;
import jdbms.sql.datatypes.FloatSQLType;
import jdbms.sql.datatypes.IntSQLType;
import jdbms.sql.datatypes.VarcharSQLType;
import jdbms.sql.parsing.expressions.math.util.BooleanExpressionFactory;
import jdbms.sql.parsing.properties.InputParametersContainer;

/**
 * The less than expression class.
 */
public class LessThanExpression extends BooleanExpression {

	private static final String SYMBOL = "<";
	static {
		BooleanExpressionFactory.getInstance().
		registerBoolExpression(SYMBOL, LessThanExpression.class);
	}

	/**
	 * Instantiates a new less than expression.
	 * @param parameters the parameters
	 */
	public LessThanExpression(
			final InputParametersContainer parameters) {
		super(SYMBOL, parameters);
	}
	@Override
	public boolean evaluate(final VarcharSQLType left,
			final VarcharSQLType right) {
		return left.compareTo(right) < 0;
	}
	@Override
	public boolean evaluate(final IntSQLType left,
			final IntSQLType right) {
		return left.compareTo(right) < 0;
	}
	@Override
	public boolean evaluate(final FloatSQLType left,
			final FloatSQLType right) {
		return left.compareTo(right) < 0;
	}
	@Override
	public boolean evaluate(final DateSQLType left,
			final DateSQLType right) {
		return left.compareTo(right) < 0;
	}
	@Override
	public boolean evaluate(final DateTimeSQLType left,
			final DateTimeSQLType right) {
		return left.compareTo(right) < 0;
	}
}
