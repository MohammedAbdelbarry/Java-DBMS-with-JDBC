package jdbms.sql.parsing.properties;

import java.util.ArrayList;

import jdbms.sql.data.ColumnIdentifier;

/**
 * The Class TableCreationParameters.
 */
public class TableCreationParameters {

    /** The table name. */
    private String tableName;

    /** The column definitions. */
    private ArrayList<ColumnIdentifier> columnDefinitions;

    /**
     * Instantiates a new table creation parameters.
     */
    public TableCreationParameters() {

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

}
