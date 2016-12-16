package jdbms.sql.parsing.expressions.columns.types;

import jdbms.sql.parsing.expressions.Expression;
import jdbms.sql.parsing.properties.InputParametersContainer;
import jdbms.sql.parsing.statements.Statement;

/**
 * The Class ColumnsDatatypesExpression.
 */
public abstract class ColumnsDatatypesExpression implements Expression {

    private Expression nextExpression;
    private Statement nextStatement;
    protected InputParametersContainer parameters;

    /**
     * Instantiates a new columns datatypes expression.
     * @param nextExpression the next expression to be interpreted
     * @param parameters     the input parameters
     */
    public ColumnsDatatypesExpression(Expression nextExpression,
                                      InputParametersContainer parameters) {
        this.nextExpression = nextExpression;
        this.parameters = parameters;
    }

    /**
     * Instantiates a new columns datatypes expression.
     * @param nextStatement the next statement to be interpreted
     * @param parameters    the input parameters
     */
    public ColumnsDatatypesExpression(Statement nextStatement,
                                      InputParametersContainer parameters) {
        this.nextStatement = nextStatement;
        this.parameters = parameters;
    }

    @Override
    public boolean interpret(String sqlExpression) {
        if (this.nextExpression != null) {
            return this.nextExpression.interpret(sqlExpression);
        } else if (this.nextStatement != null) {
            return this.nextStatement.interpret(sqlExpression);
        }
        return false;
    }
}
