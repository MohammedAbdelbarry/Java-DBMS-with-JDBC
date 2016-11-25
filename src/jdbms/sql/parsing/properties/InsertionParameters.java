package jdbms.sql.parsing.properties;

import java.util.ArrayList;

public class InsertionParameters {
	private ArrayList<String> columns;
	private ArrayList<ArrayList<String>> values;
	private String tableName;
	public InsertionParameters() {

	}
	public ArrayList<String> getColumns() {
		return columns;
	}
	public void setColumns(ArrayList<String> columns) {
		this.columns = columns;
	}
	public ArrayList<ArrayList<String>> getValues() {
		return values;
	}
	public void setValues(ArrayList<ArrayList<String>> values) {
		this.values = values;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String table) {
		this.tableName = table;
	}
}
