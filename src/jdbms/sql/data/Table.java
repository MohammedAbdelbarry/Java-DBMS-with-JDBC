package jdbms.sql.data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jdbms.sql.data.xml.XMLCreator;

public class Table {

	private String tableName;
	private Map<String, TableColumn> columns;
	private int numberOfRows;
	private Database parent;
	public Table(String tableName, Database parent) {
		this.tableName = tableName;
		columns = new HashMap<>();
		numberOfRows = 0;
		this.parent = parent;
		try {
			createXMLFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createXMLFile() throws IOException {
		XMLCreator creator = new XMLCreator(this);
		String xml = creator.create();
		File xmlFile = new File(parent.getDatabaseName());
		FileWriter writer = new FileWriter(xmlFile);
		writer.write(xml);
		writer.close();
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
		ArrayList<ColumnIdentifier> columnIdentifiers
		= new ArrayList<>();
		for (String name : columns.keySet()) {
			columnIdentifiers.add(new
					ColumnIdentifier(name,
					columns.get(name).getColumnDataType()));
		}
		return new TableIdentifier(tableName, columnIdentifiers, parent);
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
