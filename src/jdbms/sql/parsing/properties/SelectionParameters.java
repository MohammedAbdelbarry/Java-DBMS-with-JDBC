package jdbms.sql.parsing.properties;

import java.util.ArrayList;

import jdbms.sql.parsing.expressions.math.BooleanExpression;
import jdbms.sql.parsing.expressions.util.ColumnOrder;

/**
 * The Class SelectionParameters.
 */
public class SelectionParameters {

    /** The columns. */
    private ArrayList<String> columns;

    /** The table name. */
    private String tableName;

    /** The condition. */
    private BooleanExpression condition;

    /** The is distinct. */
    private boolean isDistinct;

    /** The columns order. */
    private ArrayList<ColumnOrder> columnsOrder;

    /**
     * Instantiates a new selection parameters.
     */
    public SelectionParameters() {
        this.isDistinct = false;
    }

    /**
     * Gets the columns.
     * @return the columns
     */
    public ArrayList<String> getColumns() {
        return columns;
    }

    /**
     * Sets the columns.
     * @param columns the new columns
     */
    public void setColumns(final ArrayList<String> columns) {
        this.columns = columns;
    }

    /**
     * Gets the table name.
     * @return the table name
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * Sets the table name.
     * @param table the new table name
     */
    public void setTableName(final String table) {
        this.tableName = table;
    }

    /**
     * Gets the condition.
     * @return the condition
     */
    public BooleanExpression getCondition() {
        return condition;
    }

    /**
     * Sets the condition.
     * @param condition the new condition
     */
    public void setCondition(final BooleanExpression condition) {
        this.condition = condition;
    }

    /**
     * Sets the distinct.
     * @param distinct the new distinct
     */
    public void setDistinct(final boolean distinct) {
        this.isDistinct = distinct;
    }

    /**
     * Checks if is distinct.
     * @return true, if is distinct
     */
    public boolean isDistinct() {
        return this.isDistinct;
    }

    /**
     * Gets the columns order.
     * @return the columns order
     */
    public ArrayList<ColumnOrder> getColumnsOrder() {
        return this.columnsOrder;
    }

    /**
     * Sets the columns order.
     * @param columnsOrder the new columns order
     */
    public void setColumnsOrder(final ArrayList<ColumnOrder> columnsOrder) {
        this.columnsOrder = columnsOrder;
    }
}
