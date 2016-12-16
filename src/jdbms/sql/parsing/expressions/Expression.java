package jdbms.sql.parsing.expressions;

/**
 * The Interface for all expressions.
 */
public interface Expression {

    /**
     * Attempts parsing the sql expression, returns
     * true if the expression could be parsed successfully,
     * false otherwise.
     * @param sqlExpression the sql expression
     * @return true if the expression was parsed successfully, false otherwise
     */
    boolean interpret(String sqlExpression);
}
