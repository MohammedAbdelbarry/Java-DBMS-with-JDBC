package jdbms.sql.parsing.operators;

public class UnaryOperator extends Operator {
	private String operand = null;
	public UnaryOperator(String symbol) {
		super(symbol);
	}
	public String getOperand() {
		return operand;
	}
	public void setOperand(String operand) {
		this.operand = operand;
	}
}
