package jdbms.sql.parsing.expressions.math;

import jdbms.sql.parsing.expressions.Expression;
import jdbms.sql.parsing.expressions.util.StringModifier;
import jdbms.sql.parsing.operators.BinaryOperator;
import jdbms.sql.parsing.properties.InputParametersContainer;
import jdbms.sql.parsing.statements.Statement;
import jdbms.sql.parsing.util.Constants;

public abstract class BinaryExpression implements Expression {
	private BinaryOperator operator;
	private Expression nextExpression;
	private Statement nextStatement;
	private StringModifier modifier;
	protected InputParametersContainer parameters;
	public BinaryExpression(String symbol,
			Expression nextExpression,
			InputParametersContainer parameters) {
		this.operator = new BinaryOperator(symbol);
		this.nextExpression = nextExpression;
		this.parameters = parameters;
		this.modifier = new StringModifier();
	}
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
		String modifiedExpression = modifier.modifyString(sqlExpression).trim();
		if (!modifiedExpression.contains(this.operator.getSymbol())) {
			return false;
		}
		int operatorIndex = modifiedExpression.indexOf(this.operator.getSymbol());
		String modifiedRightPart = modifiedExpression.substring(operatorIndex +
				this.operator.getSymbol().length()).trim();
		String sqlExpRightPart = sqlExpression.substring(operatorIndex +
				this.operator.getSymbol().length()).trim();
		int seperatorIndex = modifiedRightPart.indexOf(" ");
		String leftOperand = sqlExpression.substring(0, operatorIndex).trim();
		String rightOperand = sqlExpRightPart.substring(0, seperatorIndex).trim();
		if (!validOperand(leftOperand) || !validOperand(rightOperand)) {
			return false;
		}
		String restOfExpression = sqlExpRightPart.substring(seperatorIndex).trim();
		operator.setLeftOperand(leftOperand);
		operator.setRightOperand(rightOperand);
		if (this.nextExpression != null) {
			return nextExpression.interpret(restOfExpression.trim());
		} else if (this.nextStatement != null) {
			return nextStatement.interpret(restOfExpression.trim());
		}
		return false;
	}
	
	private boolean validOperand(String exp) {
		if (exp.matches(Constants.INT_REGEX) ||
				exp.matches(Constants.COLUMN_REGEX) ||
				exp.matches(Constants.STRING_REGEX) ||
				exp.matches(Constants.DOUBLE_STRING_REGEX)) {
			return true;
		}
		return false;
	}
	public String getLeftOperand() {
		return operator.getLeftOperand();
	}
	public String getRightOperand() {
		return operator.getRightOperand();
	}
	public void setLeftOperand(String operand) {
		operator.setLeftOperand(operand);
	}
	public void setRightOperand(String operand) {
		operator.setRightOperand(operand);
	}
	public boolean leftOperandIsConstant() {
		return getLeftOperand().matches(Constants.INT_REGEX) ||
				getLeftOperand().matches(Constants.STRING_REGEX) ||
				getLeftOperand().matches(Constants.DOUBLE_STRING_REGEX);
	}
	public boolean rightOperandIsConstant() {
		return getRightOperand().matches(Constants.INT_REGEX) ||
				getRightOperand().matches(Constants.STRING_REGEX) ||
				getRightOperand().matches(Constants.DOUBLE_STRING_REGEX);
	}
	public boolean leftOperandIsColumnName() {
		return getLeftOperand().matches(Constants.COLUMN_REGEX);
	}
	public boolean rightOperandIsColumnName() {
		return getRightOperand().matches(Constants.COLUMN_REGEX);
	}
	public String getLeftOperandDataType() {
		if (getLeftOperand().matches(Constants.STRING_REGEX) ||
				getLeftOperand().matches(Constants.DOUBLE_STRING_REGEX)) {
			return "VARCHAR";
		} else if (getLeftOperand().matches(Constants.INT_REGEX)) {
			return "INTEGER";
		} else {
			return null;
		}
	}
	public String getRightOperandDataType() {
		if (getRightOperand().matches(Constants.STRING_REGEX) ||
				getRightOperand().matches(Constants.DOUBLE_STRING_REGEX)) {
			return "VARCHAR";
		} else if (getRightOperand().matches(Constants.INT_REGEX)) {
			return "INTEGER";
		} else {
			return null;
		}
	}
}
