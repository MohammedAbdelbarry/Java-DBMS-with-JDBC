package jdbms.sql.parsing.properties;

import java.util.ArrayList;

public class DropColumnParameters {

    private ArrayList<String> columns;
    private String tableName;

    public DropColumnParameters() {

    }

    /**
     * @return the tableName
     */
    public String getTableName() {
        return this.tableName;
    }

    /**
     * @param tableName the tableName to set
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * @return the column list to be dropped
     */
    public ArrayList<String> getColumnList() {
        return this.columns;
    }

    /**
     * @param columns the list of columns to be dropped
     */
    public void setColumnList(ArrayList<String> columns) {
        this.columns = columns;
    }
}
