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
	private final String TIMEREGEX = "^\\s*(?:[0-1][0-9]|[2][0-3])"
		    + "\\s*:\\s*[0-5][0-9]\\s*:\\s*[0-5][0-9]\\s*$";
	private final DataTypesValidator validator;
	protected InputParametersContainer parameters;

	/**
	 * Instantiates a new binary expression.
	 * @param symbol the binary epxression symbol
	 * @param parameters the input parameters
	 */
	public BinaryExpression(final String symbol,
			final InputParametersContainer parameters) {
		this.operator = new BinaryOperator(symbol);
		this.parameters = parameters;
		this.modifier = new StringModifier();
		this.validator = new DataTypesValidator();
	}

	public void setNextExpression(final Expression nextExpression) {
		this.nextExpression = nextExpression;
	}

	public void setNextStatement(final Statement nextStatement) {
		this.nextStatement = nextStatement;
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
		String rightOperand = sqlExpRightPart.
				substring(0, seperatorIndex).trim();
		String restOfExpression = sqlExpRightPart.
				substring(seperatorIndex).trim();
		if (restOfExpression.indexOf(" ") != -1
				&& validator.match("DATE", rightOperand)
				&& restOfExpression.substring(0,
						restOfExpression.indexOf(" ")).
				matches(TIMEREGEX)) {
			rightOperand = rightOperand + restOfExpression.substring(0,
					restOfExpression.indexOf(" ")).trim();
			restOfExpression = restOfExpression.substring(restOfExpression.indexOf(" ")).trim();
		}
		if (!validOperand(leftOperand) || !validOperand(rightOperand)) {
			return false;
		}
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
