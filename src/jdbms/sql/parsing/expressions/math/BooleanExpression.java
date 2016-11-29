package jdbms.sql.parsing.expressions.math;

import jdbms.sql.datatypes.IntSQLType;
import jdbms.sql.datatypes.VarcharSQLType;
import jdbms.sql.parsing.expressions.TerminalExpression;
import jdbms.sql.parsing.operators.BinaryOperator;
import jdbms.sql.parsing.properties.InputParametersContainer;

// TODO: Auto-generated Javadoc
/**
 * The boolean expression class.
 */
public abstract class BooleanExpression extends BinaryExpression {
	
	/** The binary operator. */
	private BinaryOperator operator;
	
	/**
	 * Instantiates a new boolean expression.
	 * @param symbol the binary operator symbol
	 * @param parameters the input parameters
	 */
	public BooleanExpression(String symbol,
			InputParametersContainer parameters) {
		super(symbol, new TerminalExpression(parameters), parameters);
		this.operator = new BinaryOperator(symbol);
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
		boolean isBinaryExpression = super.interpret(sqlExpression);
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
	 * @param left the left operand of type int
	 * @param right the right operand of type int
	 * @return true, if both operands are equivalent
	 */
	public abstract boolean evaluate(IntSQLType left, IntSQLType right);
	
	/**
	 * Evaluate constant expression.
	 * @return true, if expression was true
	 */
	public abstract boolean evaluateConstantExpression();
}
