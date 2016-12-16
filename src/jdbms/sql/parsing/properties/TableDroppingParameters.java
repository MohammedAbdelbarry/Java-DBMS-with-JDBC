package jdbms.sql.parsing.properties;

public class TableDroppingParameters {
    private String tableName;

    public TableDroppingParameters() {

    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String table) {
        this.tableName = table;
    }

}
