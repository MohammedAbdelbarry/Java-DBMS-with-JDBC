package jdbms.sql.parsing.expressions.util;

import jdbms.sql.datatypes.util.DataTypesValidator;

/**
 * The Class Value Expression.
 */
public class ValueExpression {

    /**
     * The expression.
     */
    private final String expression;
    private final DataTypesValidator validator;

    /**
     * Instantiates a new value expression.
     * @param expression the value expression
     */
    public ValueExpression(final String expression) {
        this.expression = expression.trim();
        this.validator = new DataTypesValidator();
    }

    /**
     * Checks if is valid expression name.
     * @return true, if is valid expression name
     */
    public boolean isValidExpressionName() {
        return this.validator.isConstant(this.expression.trim());
    }

    /**
     * Gets the value expression.
     * @return the value expression
     */
    public String getExpression() {
        return expression;
    }
}