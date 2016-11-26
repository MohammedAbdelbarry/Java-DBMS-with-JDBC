package jdbms.sql.parsing.expressions.math;

import jdbms.sql.parsing.expressions.Expression;
import jdbms.sql.parsing.expressions.util.ColumnExpression;
import jdbms.sql.parsing.expressions.util.ValueExpression;
import jdbms.sql.parsing.operators.BinaryOperator;
import jdbms.sql.parsing.properties.InputParametersContainer;
import jdbms.sql.parsing.statements.Statement;
import jdbms.sql.parsing.util.Constants;

public abstract class BinaryExpression implements Expression {
	private static final int NUMBER_OF_OPERANDS = 2;
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
		if(!sqlExpression.contains(this.operator.getSymbol())){
			if (sqlExpression.trim().startsWith("TRUE")) {
				sqlExpression = sqlExpression.trim().replace("TRUE", "1 = 1");
			} else if (sqlExpression.trim().startsWith("FALSE")) {
				sqlExpression = sqlExpression.trim().replace("FALSE", "1 > 1");
			} else {
				return false;
			}
		}
		int binExpEndIndex = getSepratorIndex(sqlExpression.trim());
		String binExp = sqlExpression.trim().substring(0, binExpEndIndex);
		if(!binExp.contains(this.operator.getSymbol())){
			return false;
		}
		String restOfExpression = sqlExpression.trim().substring(binExpEndIndex + 1);
		String leftOperand = binExp.trim().substring(0, binExp.trim().indexOf(this.operator.getSymbol())).trim();
		String rightOperand = binExp.trim().
				substring(binExp.indexOf(this.operator.getSymbol())
						+ this.operator.getSymbol().length()).trim();
		operator.setLeftOperand(leftOperand);
		operator.setRightOperand(rightOperand);
		if (this.nextExpression != null) {
			return nextExpression.interpret(restOfExpression);
		} else if (this.nextStatement != null) {
			return nextStatement.interpret(restOfExpression);
		}
		return false;
	}
	private int getSepratorIndex(String expression) {
		int operatorIndex = expression.indexOf(this.operator.getSymbol()), start, endIndex = 0;
		boolean isSingleQuoted = false, isDoubleQuoted = false;
		if (expression.charAt(operatorIndex + 1) == ' ') {
			start = operatorIndex + 2;
		} else {
			start = operatorIndex + 1;
		}
		
		if (expression.charAt(start) == '\'') {
			isSingleQuoted = true;
		} else if (expression.charAt(start) == '"') {
			isDoubleQuoted = true;
		}
		
		if (isSingleQuoted) {
			for (int i = start + 1; i < expression.length(); i++) {
				if (expression.charAt(i) == '\'') {
					endIndex = i + 1;
					break;
				}
			}
		} else if (isDoubleQuoted) {
			for (int i = start + 1; i < expression.length(); i++) {
				if (expression.charAt(i) == '"') {
					endIndex = i + 1;
					break;
				}
			}
		} else {
			for (int i = start + 1; i < expression.length(); i++) {
				if (expression.charAt(i) == ' ') {
					endIndex = i;
					break;
				}
			}
		}
		return endIndex;
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
