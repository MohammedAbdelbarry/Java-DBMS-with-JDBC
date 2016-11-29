package jdbms.sql.parsing.expressions.math;

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
	
	private BinaryOperator operator;
	private Expression nextExpression;
	private Statement nextStatement;
	private StringModifier modifier;
	protected InputParametersContainer parameters;
	
	/**
	 * Instantiates a new binary expression.
	 * @param symbol the binary epxression symbol
	 * @param nextExpression the next expression to bee interpreted
	 * @param parameters the input parameters
	 */
	public BinaryExpression(String symbol,
			Expression nextExpression,
			InputParametersContainer parameters) {
		this.operator = new BinaryOperator(symbol);
		this.nextExpression = nextExpression;
		this.parameters = parameters;
		this.modifier = new StringModifier();
	}
	
	/**
	 * Instantiates a new binary expression.
	 * @param symbol the binary epxression symbol
	 * @param nextStatement the next statement to be interpreted
	 * @param parameters the input parameters
	 */ 
	public BinaryExpression(String symbol,
			Statement nextStatement,
			InputParametersContainer parameters) {
		this.operator = new BinaryOperator(symbol);
		this.nextStatement = nextStatement;
		this.parameters = parameters;
		this.modifier = new StringModifier();
	}
	
	@Override
	public boolean interpret(String sqlExpression) {
		sqlExpression = sqlExpression.trim();
		String modifiedExpression
		= modifier.modifyString(sqlExpression).trim();
		if (!modifiedExpression.contains(this.operator.getSymbol())) {
			return false;
		}
		int operatorIndex
		= modifiedExpression.indexOf(this.operator.getSymbol());
		String modifiedRightPart
		= modifiedExpression.substring(operatorIndex
				+ this.operator.getSymbol().length()).trim();
		String sqlExpRightPart = sqlExpression.substring(operatorIndex
				+ this.operator.getSymbol().length()).trim();
		int seperatorIndex = modifiedRightPart.indexOf(" ");
		String leftOperand = sqlExpression.
				substring(0, operatorIndex).trim();
		String rightOperand = sqlExpRightPart.
				substring(0, seperatorIndex).trim();
		if (!validOperand(leftOperand) || !validOperand(rightOperand)) {
			return false;
		}
		String restOfExpression = sqlExpRightPart.
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
	private boolean validOperand(String exp) {
		if (exp.matches(Constants.INT_REGEX) 
				|| exp.matches(Constants.COLUMN_REGEX)
				|| exp.matches(Constants.STRING_REGEX)
				|| exp.matches(Constants.DOUBLE_STRING_REGEX)) {
			return true;
		}
		return false;
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
	public final void setLeftOperand(String operand) {
		operator.setLeftOperand(operand);
	}
	
	/**
	 * Sets the right operand of the binary expression.
	 * @param operand the new right operand
	 */
	public final void setRightOperand(String operand) {
		operator.setRightOperand(operand);
	}
	
	/**
	 * Validates the left operand.
	 * @return true, if constant
	 */
	public final boolean leftOperandIsConstant() {
		return getLeftOperand().matches(Constants.INT_REGEX) 
				|| getLeftOperand().
				matches(Constants.STRING_REGEX)
				|| getLeftOperand().
				matches(Constants.DOUBLE_STRING_REGEX);
	}
	
	/**
	 * Validates the right operand.
	 * @return true, if constant
	 */
	public final boolean rightOperandIsConstant() {
		return getRightOperand().matches(Constants.INT_REGEX)
				|| getRightOperand().
				matches(Constants.STRING_REGEX)
				|| getRightOperand().
				matches(Constants.DOUBLE_STRING_REGEX);
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
		if (getLeftOperand().matches(Constants.STRING_REGEX)
				|| getLeftOperand().matches(Constants.
						DOUBLE_STRING_REGEX)) {
			return "VARCHAR";
		} else if (getLeftOperand().matches(Constants.INT_REGEX)) {
			return "INTEGER";
		} else {
			return null;
		}
	}
	
	/**
	 * Gets the right operand data type.
	 * @return the right operand data type
	 */
	public final String getRightOperandDataType() {
		if (getRightOperand().matches(Constants.STRING_REGEX)
				|| getRightOperand().matches(Constants.
						DOUBLE_STRING_REGEX)) {
			return "VARCHAR";
		} else if (getRightOperand().matches(Constants.INT_REGEX)) {
			return "INTEGER";
		} else {
			return null;
		}
	}
}
