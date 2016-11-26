package jdbms.sql.parsing.properties;

import java.util.ArrayList;

import jdbms.sql.data.ColumnIdentifier;

public class TableCreationParameters {
	private String tableName;
	//private HashMap<String, String> columnDefinitions;
	private ArrayList<ColumnIdentifier> columnDefinitions;
	public TableCreationParameters() {

	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String table) {
		this.tableName = table;
	}
	public ArrayList<ColumnIdentifier> getColumnDefinitions() {
		return columnDefinitions;
	}
	public void setColumnDefinitions(ArrayList<ColumnIdentifier> columnDefinitions) {
		this.columnDefinitions = columnDefinitions;
	}

}
