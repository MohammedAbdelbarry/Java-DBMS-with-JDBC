package jdbms.sql.parsing.properties;

import jdbms.sql.parsing.expressions.math.BooleanExpression;

/**
 * The Class DeletionParameters.
 */
public class DeletionParameters {

    /** The condition. */
    private BooleanExpression condition;

    /** The table name. */
    private String tableName;

    /**
     * Instantiates a new deletion parameters.
     */
    public DeletionParameters() {

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
     * @param table the new table name
     */
    public void setTableName(final String table) {
        this.tableName = table;
    }
}
