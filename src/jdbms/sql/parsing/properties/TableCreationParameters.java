package jdbms.sql.parsing.properties;

import java.util.HashMap;

public class TableCreationParameters {
	private String tableName;
	private HashMap<String, String> columnDefinitions;
	public TableCreationParameters() {

	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String table) {
		this.tableName = table;
	}
	public HashMap<String, String> getColumnDefinitions() {
		return columnDefinitions;
	}
	public void setColumnDefinitions(HashMap<String, String> columnDefinitions) {
		this.columnDefinitions = columnDefinitions;
	}

}
