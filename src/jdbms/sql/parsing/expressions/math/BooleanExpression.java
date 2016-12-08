package jdbms.sql.parsing.expressions.math;

import jdbms.sql.datatypes.DateSQLType;
import jdbms.sql.datatypes.DateTimeSQLType;
import jdbms.sql.datatypes.FloatSQLType;
import jdbms.sql.datatypes.IntSQLType;
import jdbms.sql.datatypes.VarcharSQLType;
import jdbms.sql.parsing.expressions.TerminalExpression;
import jdbms.sql.parsing.operators.BinaryOperator;
import jdbms.sql.parsing.properties.InputParametersContainer;
import jdbms.sql.parsing.statements.OrderByStatement;

/**
 * The boolean expression class.
 */
public abstract class BooleanExpression extends BinaryExpression {

	/** The binary operator. */
	private final BinaryOperator operator;
	private final InputParametersContainer parameters;

	/**
	 * Instantiates a new boolean expression.
	 * @param symbol the binary operator symbol
	 * @param parameters the input parameters
	 */
	public BooleanExpression(final String symbol,
			final InputParametersContainer parameters) {
		super(symbol, parameters);
		this.operator = new BinaryOperator(symbol);
		this.parameters = parameters;
	}

	@Override
	public boolean interpret(String sqlExpression) {
		sqlExpression = sqlExpression.trim();
		if(!sqlExpression.
				contains(this.operator.getSymbol())) {
			if (sqlExpression.trim().startsWith("TRUE")) {
				sqlExpression
				= sqlExpression.trim().
				replace("TRUE", "1 = 1");
			} else if (sqlExpression.trim().startsWith("FALSE")) {
				sqlExpression
				= sqlExpression.trim().
				replace("FALSE", "1 > 1");
			} else {
				return false;
			}
		}
		super.setNextExpression(new TerminalExpression(this.parameters));
		boolean isBinaryExpression = super.interpret(sqlExpression);
		if (!isBinaryExpression) {
			super.setNextExpression(null);
			super.setNextStatement(new OrderByStatement(this.parameters));
			isBinaryExpression = super.interpret(sqlExpression);
		}
		parameters.setCondition(this);
		return isBinaryExpression;
	}
	/**
	 * Evaluate boolean expression.
	 * @param left the left operand of type varchar
	 * @param right the right operand of type varchar
	 * @return true, if both operands are equivalent
	 */
	public abstract boolean evaluate(VarcharSQLType left,
			VarcharSQLType right);
	/**
	 * Evaluate boolean expression.
	 * @param left the left operand of type integer
	 * @param right the right operand of type integer
	 * @return true, if both operands are equivalent
	 */
	public abstract boolean evaluate(IntSQLType left,
			IntSQLType right);
	/**
	 * Evaluate boolean expression.
	 * @param left the left operand of type float
	 * @param right the right operand of type float
	 * @return true, if both operands are equivalent
	 */
	public abstract boolean evaluate(FloatSQLType left,
			FloatSQLType right);
	/**
	 * Evaluate boolean expression.
	 * @param left the left operand of type date
	 * @param right the right operand of type date
	 * @return true, if both operands are equivalent
	 */
	public abstract boolean evaluate(DateSQLType left,
			DateSQLType right);
	/**
	 * Evaluate boolean expression.
	 * @param left the left operand of type datetime
	 * @param right the right operand of type datetime
	 * @return true, if both operands are equivalent
	 */
	public abstract boolean evaluate(DateTimeSQLType left,
			DateTimeSQLType right);
}
