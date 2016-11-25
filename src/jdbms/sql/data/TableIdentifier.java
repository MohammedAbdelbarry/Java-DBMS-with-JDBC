package jdbms.sql.data;

import java.util.ArrayList;

public class TableIdentifier {
	private String tableName;
	private ArrayList<ColumnIdentifier> columns;
	public TableIdentifier(String name,
			ArrayList<ColumnIdentifier> columns) {
		tableName = name;
		this.columns = columns;
	}
	public String getTableName() {
		return tableName;
	}

	public ArrayList<ColumnIdentifier> getColumnsIdentifiers() {
		return columns;
	}
	public ArrayList<String> getColumnNames() {
		ArrayList<String> names = new ArrayList<>();
		for (ColumnIdentifier columnIdentifier : columns) {
			names.add(columnIdentifier.getName());
		}
		return names;
	}
}
