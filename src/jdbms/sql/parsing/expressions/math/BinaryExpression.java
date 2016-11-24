package jdbms.sql.parsing.expressions.math;

import jdbms.sql.parsing.expressions.Expression;
import jdbms.sql.parsing.expressions.TerminalExpression;
import jdbms.sql.parsing.operators.BinaryOperator;

public abstract class BinaryExpression implements Expression {
	private static final int NUMBER_OF_OPERANDS = 2;
	private BinaryOperator operator;
	private Expression nextExpression = new TerminalExpression();
	public BinaryExpression(String symbol) {
		operator = new BinaryOperator(symbol);
	}
	@Override
	public boolean interpret(String sqlExpression) {
		 String[] operands = sqlExpression.split(operator.getSymbol());
		 if (operands.length != NUMBER_OF_OPERANDS) {
			 return false;
		 }
		 operator.setLeftOperand(operands[0].trim());
		 operator.setRightOperand(operands[1].substring(0,
				 sqlExpression.lastIndexOf(" ")).trim());
		 return nextExpression.interpret(operands[1].substring(
				 sqlExpression.lastIndexOf(" ")).trim());
	}
	public String getLeftOperand() {
		return operator.getLeftOperand();
	}
	public String getRightOperand() {
		return operator.getRightOperand();
	}
}
