package jdbms.sql.parsing.properties;

import java.util.ArrayList;

import jdbms.sql.parsing.expressions.math.BooleanExpression;
import jdbms.sql.parsing.expressions.util.ColumnOrder;

public class SelectionParameters {
    private ArrayList<String> columns;
    private String tableName;
    private BooleanExpression condition;
    private boolean isDistinct;
    private ArrayList<ColumnOrder> columnsOrder;

    public SelectionParameters() {
        this.isDistinct = false;
    }

    public ArrayList<String> getColumns() {
        return columns;
    }

    public void setColumns(ArrayList<String> columns) {
        this.columns = columns;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String table) {
        this.tableName = table;
    }

    public BooleanExpression getCondition() {
        return condition;
    }

    public void setCondition(BooleanExpression condition) {
        this.condition = condition;
    }

    public void setDistinct(boolean distinct) {
        this.isDistinct = distinct;
    }

    public boolean isDistinct() {
        return this.isDistinct;
    }

    public ArrayList<ColumnOrder> getColumnsOrder() {
        return this.columnsOrder;
    }

    public void setColumnsOrder(ArrayList<ColumnOrder> columnsOrder) {
        this.columnsOrder = columnsOrder;
    }
}
