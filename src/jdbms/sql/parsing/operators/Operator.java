package jdbms.sql.parsing.operators;

/**
 * The Class Operator.
 */
public abstract class Operator {

    /** The symbol. */
    private String symbol = null;

    /**
     * Instantiates a new operator.
     * @param symbol the symbol
     */
    public Operator(final String symbol) {
        this.symbol = symbol;
    }

    /**
     * Gets the symbol.
     * @return the symbol
     */
    public String getSymbol() {
        return symbol;
    }
}
