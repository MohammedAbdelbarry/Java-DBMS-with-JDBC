package jdbms.sql.parsing.expressions.table.name;

import jdbms.sql.parsing.expressions.columns.names.InsertColumnListExpression;
import jdbms.sql.parsing.properties.InputParametersContainer;

/**
 * The Class Table Name Column List Expression.
 */
public class TableNameColumnListExpression extends TableNameExpression {

    /**
     * Instantiates a new table name column list expression.
     * @param parameters the input parameters
     */
    public TableNameColumnListExpression(
            InputParametersContainer parameters) {
        super(new InsertColumnListExpression(parameters), parameters);
    }
}
