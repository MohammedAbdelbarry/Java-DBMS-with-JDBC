package jdbms.sql.parsing.operators;

public class BinaryOperator extends Operator {
	private String leftOperand = null;
	private String rightOperand = null;
	public BinaryOperator(String symbol) {
		super(symbol);
	}
	public String getLeftOperand() {
		return leftOperand;
	}
	public void setLeftOperand(String operand) {
		leftOperand = operand;
	}
	public String getRightOperand() {
		return rightOperand;
	}
	public void setRightOperand(String operand) {
		rightOperand = operand;
	}
}
