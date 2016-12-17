package jdbms.sql.parsing.operators;

/**
 * The Class UnaryOperator.
 */
public class UnaryOperator extends Operator {

    /** The operand. */
    private String operand = null;

    /**
     * Instantiates a new unary operator.
     * @param symbol the symbol
     */
    public UnaryOperator(final String symbol) {
        super(symbol);
    }

    /**
     * Gets the operand.
     * @return the operand
     */
    public String getOperand() {
        return operand;
    }

    /**
     * Sets the operand.
     * @param operand the new operand
     */
    public void setOperand(final String operand) {
        this.operand = operand;
    }
}
