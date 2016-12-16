package jdbms.sql.parsing.expressions.columns.names;

import jdbms.sql.parsing.expressions.Expression;
import jdbms.sql.parsing.properties.InputParametersContainer;
import jdbms.sql.parsing.statements.Statement;

/**
 * The Class ColumnListExpression.
 */
public abstract class ColumnListExpression implements Expression {

    private Expression nextExpression = null;
    private Statement nextStatement = null;
    protected InputParametersContainer parameters;

    /**
     * Instantiates a new column list expression.
     * @param nextExpression the next expression to be interpreted
     * @param parameters     the input parameters
     */
    public ColumnListExpression(Expression nextExpression,
                                InputParametersContainer parameters) {
        this.nextExpression = nextExpression;
        this.parameters = parameters;
    }

    /**
     * Instantiates a new column list expression.
     * @param nextStatement the next statement to be interpreted
     * @param parameters    the input parameters
     */
    public ColumnListExpression(Statement nextStatement,
                                InputParametersContainer parameters) {
        this.nextStatement = nextStatement;
        this.parameters = parameters;
    }

    @Override
    public boolean interpret(String sqlExpression) {
        if (nextExpression != null) {
            return nextExpression.interpret(sqlExpression);
        } else if (nextStatement != null) {
            return nextStatement.interpret(sqlExpression);
        }
        return false;
    }
}
