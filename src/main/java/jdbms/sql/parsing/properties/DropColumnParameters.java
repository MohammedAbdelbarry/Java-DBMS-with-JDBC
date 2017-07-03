package jdbms.sql.parsing.properties;

import java.util.ArrayList;

/**
 * The Class DropColumnParameters.
 */
public class DropColumnParameters {

    /** The columns. */
    private ArrayList<String> columns;

    /** The table name. */
    private String tableName;

    /**
     * Instantiates a new drop column parameters.
     */
    public DropColumnParameters() {

    }

    /**
     * Gets the table name.
     * @return the tableName
     */
    public String getTableName() {
        return this.tableName;
    }

    /**
     * Sets the table name.
     * @param tableName the tableName to set
     */
    public void setTableName(final String tableName) {
        this.tableName = tableName;
    }

    /**
     * Gets the column list.
     * @return the column list to be dropped
     */
    public ArrayList<String> getColumnList() {
        return this.columns;
    }

    /**
     * Sets the column list.
     * @param columns the list of columns to be dropped
     */
    public void setColumnList(final ArrayList<String> columns) {
        this.columns = columns;
    }
}
