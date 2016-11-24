package jdbms.sql.data;

import java.util.Collection;

public class TableIdentifier {
	private String tableName;
	Collection<String> columnsDataTypes;
	public TableIdentifier(String name, Collection<String> types) {
		tableName = name;
		columnsDataTypes = types;
	}
	public String getTableName() {
		return tableName;
	}
	public Collection<String> getColumnsDataTypes() {
		return columnsDataTypes;
	}
}
