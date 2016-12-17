package jdbms.sql.parsing.operators;

/**
 * The Class BinaryOperator.
 */
public class BinaryOperator extends Operator {

    /** The left operand. */
    private String leftOperand = null;

    /** The right operand. */
    private String rightOperand = null;

    /**
     * Instantiates a new binary operator.
     * @param symbol the symbol
     */
    public BinaryOperator(final String symbol) {
        super(symbol);
    }

    /**
     * Gets the left operand.
     * @return the left operand
     */
    public String getLeftOperand() {
        return leftOperand;
    }

    /**
     * Sets the left operand.
     * @param operand the new left operand
     */
    public void setLeftOperand(final String operand) {
        leftOperand = operand;
    }

    /**
     * Gets the right operand.
     * @return the right operand
     */
    public String getRightOperand() {
        return rightOperand;
    }

    /**
     * Sets the right operand.
     * @param operand the new right operand
     */
    public void setRightOperand(final String operand) {
        rightOperand = operand;
    }
}
