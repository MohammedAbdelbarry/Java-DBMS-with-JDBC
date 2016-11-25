package jdbms.sql.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jdbms.sql.exceptions.ColumnAlreadyExistsException;
import jdbms.sql.exceptions.ColumnListTooLargeException;
import jdbms.sql.exceptions.ColumnNotFoundException;
import jdbms.sql.exceptions.RepeatedColumnException;
import jdbms.sql.parsing.expressions.math.BooleanExpression;
import jdbms.sql.parsing.properties.InsertionParameters;
import jdbms.sql.parsing.properties.TableCreationParameters;

public class Table {

	private String tableName;
	private Map<String, TableColumn> columns;
	private int numberOfRows;
	public Table(TableCreationParameters createTableParameters)
			throws ColumnAlreadyExistsException {
		this.tableName = createTableParameters.getTableName();
		columns = new HashMap<>();
		numberOfRows = 0;
		HashMap<String, String> colDefinitions
		= createTableParameters.getColumnDefinitions();
		for (String column : colDefinitions.keySet()) {
				addTableColumn(column, colDefinitions.get(column));
		}
	}
	public Table(TableIdentifier tableIdentifier)
			throws ColumnAlreadyExistsException {
		this.tableName = tableIdentifier.getTableName();
		ArrayList<ColumnIdentifier> columnIdentifiers
		= tableIdentifier.getColumnsIdentifiers();
		for (ColumnIdentifier columnIdentifier
				: columnIdentifiers) {
			addTableColumn(columnIdentifier.getName(),
					columnIdentifier.getType());
		}
	}
	public void addTableColumn(String columnName, String columnDataType)
			throws ColumnAlreadyExistsException {
		if (columns.containsKey(columnName)) {
			throw new ColumnAlreadyExistsException();
		}
		TableColumn newColumn = new TableColumn(columnName, columnDataType);
		columns.put(columnName, newColumn);
	}
	public void insertRows(InsertionParameters insertParameters)
			throws RepeatedColumnException,
			ColumnListTooLargeException,
			ColumnNotFoundException {
		for (ArrayList<String> rowValue : insertParameters.getValues()) {
			insertRow(insertParameters.getColumns(), rowValue);
		}
	}
	private void insertRow(ArrayList<String> columnNames,
			ArrayList<String> values)
			throws RepeatedColumnException, ColumnListTooLargeException,
			ColumnNotFoundException {
		if (columnNames.size() > columns.size()
				|| columnNames.size() != values.size()) {
			throw new ColumnListTooLargeException();
		}
		if (new HashSet<>(columnNames).size() > columnNames.size()) {
			throw new RepeatedColumnException();
		}
		for (String column : columnNames) {
			if (!columns.containsKey(column)) {
				throw new ColumnNotFoundException();
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
		return new TableIdentifier(tableName, columnIdentifiers);
	}

	public Map<String, TableColumn> getColumns() {
		Map<String, TableColumn> clone = new HashMap<>();
		for(String key : columns.keySet()) {
			TableColumn current = columns.get(key);
			clone.put(key, current);
		}
		return clone;
	}

	private ArrayList<TableColumn> getColumnList(ArrayList<String> cols) {
		ArrayList<TableColumn> requestedColumns = new ArrayList<>();
		for (String key : cols) {
			if (columns.keySet().contains(key)) {
				requestedColumns.add(columns.get(key));
			}
		}
		return requestedColumns;
	}

	public int getNumberOfRows() {
		return numberOfRows;
	}

	public String getName() {
		return tableName;
	}
	public void deleteRow(BooleanExpression condition) {
		if(condition.leftOperandIsConstant()
				&& condition.rightOperandIsConstant()) {
			if (condition.evaluateConstantExpression()) {
				for (TableColumn column : columns.values()) {
					column.clearColumn();
				}
			} else {
				return;
			}
		} else if (condition.leftOperandIsColumnName()
				&& condition.rightOperandIsConstant()) {
			if ()
		}
	}
}
