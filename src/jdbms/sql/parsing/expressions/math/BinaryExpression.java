package jdbms.sql.parsing.expressions.math;

import jdbms.sql.parsing.expressions.Expression;
import jdbms.sql.parsing.operators.BinaryOperator;
import jdbms.sql.parsing.statements.Statement;

public abstract class BinaryExpression implements Expression {
	private static final int NUMBER_OF_OPERANDS = 2;
	private BinaryOperator operator;
	private Expression nextExpression;
	private Statement nextStatement;
	public BinaryExpression(String symbol, Expression nextExpression) {
		this.operator = new BinaryOperator(symbol);
		this.nextExpression = nextExpression;
	}
	public BinaryExpression(String symbol, Statement nextStatement) {
		this.operator = new BinaryOperator(symbol);
		this.nextStatement = nextStatement;
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
		 if (this.nextExpression != null) {
			 return nextExpression.interpret(operands[1].substring(
					 sqlExpression.lastIndexOf(" ")).trim());
		 } else if (this.nextStatement != null) {
			 return nextStatement.interpret(operands[1].substring(
					 sqlExpression.lastIndexOf(" ")).trim());
		 }
		 return false;
	}
	public String getLeftOperand() {
		return operator.getLeftOperand();
	}
	public String getRightOperand() {
		return operator.getRightOperand();
	}
}
