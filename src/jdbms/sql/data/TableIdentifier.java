package jdbms.sql.data;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

public class TableIdentifier {
	private String tableName;
	private ArrayList<ColumnIdentifier> columns;
	private Database parent;
	public TableIdentifier(String name,
			ArrayList<ColumnIdentifier> columns,
			Database parent) {
		tableName = name;
		this.columns = columns;
		this.parent = parent;
	}
	public String getTableName() {
		return tableName;
	}

	public Database getParent() {
		return parent;
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
