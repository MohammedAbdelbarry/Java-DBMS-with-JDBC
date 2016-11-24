package jdbms.sql.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Table {

	private String tableName;
	private Map<String, TableColumn> columns;
	private int numberOfRows;

	public Table(String tableName) {
		this.tableName = tableName;
		columns = new HashMap<>();
		numberOfRows = 0;
	}

	public void addTableColumn(String columnName, String columnDataType) {
		TableColumn newColumn = new TableColumn(columnName, columnDataType);
		columns.put(columnName, newColumn);
	}

	public void insertRow(ArrayList<String> columnNames, ArrayList<String> values) {
		if (columnNames.size() > columns.size()
				|| columnNames.size() != values.size()) {
			//throw new ColumnListTooLargeException();
		}
		for (String column : columnNames) {
			if (!columns.containsKey(column)) {
				//throw new ColumnNotFoundException();
			}
		}
		Set<String> nullCells = new HashSet<>(columns.keySet());
		nullCells.removeAll(columnNames);
		for (int i = 0; i < columnNames.size(); i++) {
			columns.get(columnNames.get(i)).add(values.get(i));
		}
		for (String nullCell : nullCells) {
			columns.get(nullCell).add(null);
		}
		numberOfRows++;
	}

	public TableIdentifier getTableIdentifier() {
		Collection<String> types = new ArrayList<>();
		for (TableColumn column : columns.values()) {
			types.add(column.getColumnDataType());
		}
		return new TableIdentifier(tableName, types);
	}

	public Map<String, TableColumn> getColumns() {
		Map<String, TableColumn> clone = new HashMap<>();
		for(String key : columns.keySet()) {
			TableColumn current = columns.get(key);
			clone.put(key, current);
		}
		return clone;
	}

	public int getNumberOfRows() {
		return numberOfRows;
	}

	public String getName() {
		return tableName;
	}
}
