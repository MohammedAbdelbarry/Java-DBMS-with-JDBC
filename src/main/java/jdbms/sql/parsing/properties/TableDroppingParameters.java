package jdbms.sql.parsing.properties;

/**
 * The Class TableDroppingParameters.
 */
public class TableDroppingParameters {

    /** The table name. */
    private String tableName;

    /**
     * Instantiates a new table dropping parameters.
     */
    public TableDroppingParameters() {

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
