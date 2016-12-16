package jdbms.sql.parsing.expressions.table.name;

import jdbms.sql.parsing.properties.InputParametersContainer;
import jdbms.sql.parsing.statements.OrderByStatement;

public class OrderByTableNameExpression extends TableNameExpression {

    public OrderByTableNameExpression(InputParametersContainer parameters) {
        super(new OrderByStatement(parameters), parameters);
    }
}
