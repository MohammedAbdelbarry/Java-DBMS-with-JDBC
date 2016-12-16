package jdbms.sql.parsing.expressions.columns.names;

import java.util.ArrayList;

import jdbms.sql.parsing.properties.InputParametersContainer;
import jdbms.sql.parsing.statements.FromStatement;

/**
 * The Class ColumnWildcardExpression.
 */
public class ColumnWildcardExpression extends ColumnListExpression {

    /**
     * Instantiates a new column wildcard expression.
     * @param parameters the input parameters
     */
    public ColumnWildcardExpression(InputParametersContainer parameters) {
        super(new FromStatement(parameters), parameters);
    }

    @Override
    public boolean interpret(String sqlExpression) {
        if (sqlExpression.startsWith("*")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("*");
            parameters.setColumns(list);
            return super.interpret(sqlExpression.
                    substring(sqlExpression.
                            indexOf("*") + 1).
                    trim());
        }
        return false;
    }
}
