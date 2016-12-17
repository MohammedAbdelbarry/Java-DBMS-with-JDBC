package jdbms.sql.parsing.properties;

import java.util.ArrayList;

/**
 * The Class InsertionParameters.
 */
public class InsertionParameters {

    /** The columns. */
    private ArrayList<String> columns;

    /** The values. */
    private ArrayList<ArrayList<String>> values;

    /** The table name. */
    private String tableName;

    /**
     * Instantiates a new insertion parameters.
     */
    public InsertionParameters() {

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
