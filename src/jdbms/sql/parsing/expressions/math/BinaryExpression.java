package jdbms.sql.parsing.expressions.math;

import jdbms.sql.parsing.expressions.Expression;
import jdbms.sql.parsing.operators.BinaryOperator;
import jdbms.sql.parsing.properties.InputParametersContainer;
import jdbms.sql.parsing.statements.Statement;
import jdbms.sql.parsing.util.Constants;

public abstract class BinaryExpression implements Expression {
	private BinaryOperator operator;
	private Expression nextExpression;
	private Statement nextStatement;
	protected InputParametersContainer parameters;
	public BinaryExpression(String symbol,
			Expression nextExpression,
			InputParametersContainer parameters) {
		this.operator = new BinaryOperator(symbol);
		this.nextExpression = nextExpression;
		this.parameters = parameters;
	}
	public BinaryExpression(String symbol,
			Statement nextStatement,
			InputParametersContainer parameters) {
		this.operator = new BinaryOperator(symbol);
		this.nextStatement = nextStatement;
		this.parameters = parameters;
	}
	@Override
	public boolean interpret(String sqlExpression) {
		sqlExpression = sqlExpression.trim();
		String modifiedExpression = removeString(sqlExpression).trim();
		if (!modifiedExpression.contains(this.operator.getSymbol())) {
			return false;
		}
		int operatorIndex = modifiedExpression.indexOf(this.operator.getSymbol());
		if (modifiedExpression.substring(0, operatorIndex).contains("<") ||
				modifiedExpression.substring(0, operatorIndex).contains(">") ||
				modifiedExpression.substring(0, operatorIndex).contains("!")) {
			return false;
		}
		String leftOperand = sqlExpression.substring(0, operatorIndex).trim();
		String rightPart = sqlExpression.substring(operatorIndex +
				this.operator.getSymbol().length()).trim();
		String[] parts = rightPart.split(" ");
		operator.setLeftOperand(leftOperand);
		operator.setRightOperand(parts[0].trim());
		if (this.nextExpression != null) {
			return nextExpression.interpret(parts[parts.length - 1].trim());
		} else if (this.nextStatement != null) {
			return nextStatement.interpret(parts[parts.length - 1].trim());
		}
		return false;
	}

	private String removeString(String expression) {
		String stringless = "";
		for (int i = 0; i < expression.length(); i++) {
			stringless += expression.charAt(i);
			if (expression.charAt(i) == '\'') {
				for (int j = i + 1; j < expression.length(); j++) {
					if (expression.charAt(j) == '\'') {
						stringless += expression.charAt(j);
						i = j;
						break;
					}
					stringless += "s";
				}
			} else if (stringless.charAt(i) == '"') {
				for (int j = i + 1; j < expression.length(); j++) {
					if (expression.charAt(j) == '"') {
						stringless += expression.charAt(j);
						i = j;
						break;
					}
					stringless += "s";
				}
			}
		}
		return stringless;
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
