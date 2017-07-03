package jdbms.sql.parsing.properties;

import java.util.ArrayList;

import jdbms.sql.data.ColumnIdentifier;
import jdbms.sql.parsing.expressions.math.AssignmentExpression;
import jdbms.sql.parsing.expressions.math.BooleanExpression;
import jdbms.sql.parsing.expressions.util.ColumnOrder;

/**
 * The Class InputParametersContainer.
 */
public class InputParametersContainer {

    /** The database name. */
    private String databaseName;

    /** The condition. */
    private BooleanExpression condition;

    /** The table name. */
    private String tableName;

    /** The columns. */
    private ArrayList<String> columns;

    /** The values. */
    private ArrayList<ArrayList<String>> values;

    /** The column definitions. */
    private ArrayList<ColumnIdentifier> columnDefinitions;

    /** The assignment list. */
    private ArrayList<AssignmentExpression> assignmentList;

    /** The distinct. */
    private boolean distinct;

    /** The columns order. */
    private ArrayList<ColumnOrder> columnsOrder;

    /**
     * Instantiates a new input parameters container.
     */
    public InputParametersContainer() {
        this.distinct = false;
    }

    /**
     * Gets the database name.
     * @return the database name
     */
    public String getDatabaseName() {
        return databaseName;
    }

    /**
     * Sets the database name.
     * @param databaseName the new database name
     */
    public void setDatabaseName(final String databaseName) {
        this.databaseName = databaseName;
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
     * Gets the table name.
     * @return the table name
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * Sets the table name.
     * @param tableName the new table name
     */
    public void setTableName(final String tableName) {
        this.tableName = tableName;
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
     * Gets the values.
     * @return the values
     */
    public ArrayList<ArrayList<String>> getValues() {
        return values;
    }

    /**
     * Sets the values.
     * @param values the new values
     */
    public void setValues(final ArrayList<ArrayList<String>> values) {
        this.values = values;
    }

    /**
     * Gets the column definitions.
     * @return the column definitions
     */
    public ArrayList<ColumnIdentifier> getColumnDefinitions() {
        return columnDefinitions;
    }

    /**
     * Sets the column definitions.
     * @param columnDefinitions the new column definitions
     */
    public void setColumnDefinitions(final ArrayList<ColumnIdentifier>
                                             columnDefinitions) {
        this.columnDefinitions = columnDefinitions;
    }

    /**
     * Gets the assignment list.
     * @return the assignment list
     */
    public ArrayList<AssignmentExpression> getAssignmentList() {
        return assignmentList;
    }

    /**
     * Sets the assignment list.
     * @param assignmentList the new assignment list
     */
    public void setAssignmentList(final ArrayList<AssignmentExpression>
										  assignmentList) {
        this.assignmentList = assignmentList;
    }

    /**
     * Sets the distinct.
     * @param distinct the new distinct
     */
    public void setDistinct(final boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * Checks if is distinct.
     * @return true, if is distinct
     */
    public boolean isDistinct() {
        return this.distinct;
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
