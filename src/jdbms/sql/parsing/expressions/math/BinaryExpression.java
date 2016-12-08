package jdbms.sql.parsing.expressions.math;

import jdbms.sql.datatypes.util.DataTypesValidator;
import jdbms.sql.parsing.expressions.Expression;
import jdbms.sql.parsing.expressions.util.StringModifier;
import jdbms.sql.parsing.operators.BinaryOperator;
import jdbms.sql.parsing.properties.InputParametersContainer;
import jdbms.sql.parsing.statements.Statement;
import jdbms.sql.parsing.util.Constants;

/**
 * The Binary Expression Class.
 */
public abstract class BinaryExpression implements Expression {

	private final BinaryOperator operator;
	private Expression nextExpression;
	private Statement nextStatement;
	private final StringModifier modifier;
	private DataTypesValidator validator;
	protected InputParametersContainer parameters;

	/**
	 * Instantiates a new binary expression.
	 * @param symbol the binary epxression symbol
	 * @param nextExpression the next expression to bee interpreted
	 * @param parameters the input parameters
	 */
	public BinaryExpression(final String symbol,
			final Expression nextExpression,
			final InputParametersContainer parameters) {
		this.operator = new BinaryOperator(symbol);
		this.nextExpression = nextExpression;
		this.parameters = parameters;
		this.modifier = new StringModifier();
		this.validator = new DataTypesValidator();
	}

	/**
	 * Instantiates a new binary expression.
	 * @param symbol the binary epxression symbol
	 * @param nextStatement the next statement to be interpreted
	 * @param parameters the input parameters
	 */
	public BinaryExpression(final String symbol,
			final Statement nextStatement,
			final InputParametersContainer parameters) {
		this.operator = new BinaryOperator(symbol);
		this.nextStatement = nextStatement;
		this.parameters = parameters;
		this.modifier = new StringModifier();
	}

	@Override
	public boolean interpret(String sqlExpression) {
		sqlExpression = sqlExpression.trim();
		final String modifiedExpression
		= modifier.modifyString(sqlExpression).trim();
		if (!modifiedExpression.contains(this.operator.getSymbol())) {
			return false;
		}
		final int operatorIndex
		= modifiedExpression.indexOf(this.operator.getSymbol());
		final String modifiedRightPart
		= modifiedExpression.substring(operatorIndex
				+ this.operator.getSymbol().length()).trim();
		final String sqlExpRightPart = sqlExpression.substring(operatorIndex
				+ this.operator.getSymbol().length()).trim();
		final int seperatorIndex = modifiedRightPart.indexOf(" ");
		final String leftOperand = sqlExpression.
				substring(0, operatorIndex).trim();
		final String rightOperand = sqlExpRightPart.
				substring(0, seperatorIndex).trim();
		if (!validOperand(leftOperand) || !validOperand(rightOperand)) {
			return false;
		}
		final String restOfExpression = sqlExpRightPart.
				substring(seperatorIndex).trim();
		operator.setLeftOperand(leftOperand);
		operator.setRightOperand(rightOperand);
		if (this.nextExpression != null) {
			return nextExpression.
					interpret(restOfExpression.trim());
		} else if (this.nextStatement != null) {
			return nextStatement.interpret(restOfExpression.trim());
		}
		return false;
	}

	/**
	 * Validates the binary expression operands.
	 * @param exp the operand to be checked
	 * @return true, if operand is valid
	 */
	private boolean validOperand(final String exp) {
		return exp.matches(Constants.COLUMN_REGEX)
				|| validator.isConstant(exp);
	}

	/**
	 * Gets the left operand of the binary expression.
	 * @return the left operand
	 */
	public final String getLeftOperand() {
		return operator.getLeftOperand();
	}

	/**
	 * Gets the right operand of the binary expression.
	 * @return the right operand
	 */
	public final String getRightOperand() {
		return operator.getRightOperand();
	}

	/**
	 * Sets the left operand of the binary expression.
	 * @param operand the new left operand
	 */
	public final void setLeftOperand(final String operand) {
		operator.setLeftOperand(operand);
	}

	/**
	 * Sets the right operand of the binary expression.
	 * @param operand the new right operand
	 */
	public final void setRightOperand(final String operand) {
		operator.setRightOperand(operand);
	}

	/**
	 * Validates the left operand.
	 * @return true, if constant
	 */
	public final boolean leftOperandIsConstant() {
		return validator.isConstant(getLeftOperand());
	}

	/**
	 * Validates the right operand.
	 * @return true, if constant
	 */
	public final boolean rightOperandIsConstant() {
		return validator.isConstant(getRightOperand());
	}

	/**
	 * Validates left operand.
	 * @return true, if it's a valid column name
	 */
	public final boolean leftOperandIsColumnName() {
		return getLeftOperand().matches(Constants.COLUMN_REGEX);
	}

	/**
	 * Validates right operand.
	 * @return true, if it's a valid column name
	 */
	public final boolean rightOperandIsColumnName() {
		return getRightOperand().matches(Constants.COLUMN_REGEX);
	}

	/**
	 * Gets the left operand data type.
	 * @return the left operand data type
	 */
	public final String getLeftOperandDataType() {
		return validator.getDataType(getLeftOperand());
	}

	/**
	 * Gets the right operand data type.
	 * @return the right operand data type
	 */
	public final String getRightOperandDataType() {
		return validator.getDataType(getRightOperand());
	}
}
