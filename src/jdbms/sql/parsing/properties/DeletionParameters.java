package jdbms.sql.parsing.properties;

import jdbms.sql.parsing.expressions.math.BooleanExpression;

public class DeletionParameters {
    private BooleanExpression condition;
    private String tableName;

    public DeletionParameters() {

    }

    public BooleanExpression getCondition() {
        return condition;
    }

    public void setCondition(BooleanExpression condition) {
        this.condition = condition;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String table) {
        this.tableName = table;
    }

}
