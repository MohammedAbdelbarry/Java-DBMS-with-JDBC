package jdbms.sql.parsing.operators;

public abstract class Operator {
    private String symbol = null;

    public Operator(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
