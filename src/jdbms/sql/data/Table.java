package jdbms.sql.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jdbms.sql.datatypes.IntSQLType;
import jdbms.sql.datatypes.VarcharSQLType;
import jdbms.sql.exceptions.ColumnAlreadyExistsException;
import jdbms.sql.exceptions.ColumnListTooLargeException;
import jdbms.sql.exceptions.ColumnNotFoundException;
import jdbms.sql.exceptions.RepeatedColumnException;
import jdbms.sql.exceptions.TypeMismatchException;
import jdbms.sql.parsing.expressions.math.BooleanExpression;
import jdbms.sql.parsing.properties.InsertionParameters;
import jdbms.sql.parsing.properties.TableCreationParameters;
import jdbms.sql.parsing.util.Constants;

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
	public void deleteRows(BooleanExpression condition)
			throws ColumnNotFoundException, TypeMismatchException {
		if(condition.leftOperandIsConstant()
				&& condition.rightOperandIsConstant()) {
			if (condition.evaluateConstantExpression()) {
				clearTable();
			} else {
				return;
			}
		} else if (condition.leftOperandIsColumnName()
				&& condition.rightOperandIsConstant()) {
			String columnName = condition.getLeftOperand();
			deleteMatching(condition, columnName,
					condition.getRightOperand(), true);
		} else if (condition.rightOperandIsColumnName()
				&& condition.leftOperandIsConstant()) {
			String columnName = condition.getRightOperand();
			deleteMatching(condition, columnName,
					condition.getLeftOperand(), false);
		} else if (condition.rightOperandIsColumnName() &&
				condition.leftOperandIsColumnName()) {
			deleteMatching(condition, condition.getLeftOperand(),
					condition.getRightOperand());
		}
	}
	private int getFirstMatch(BooleanExpression condition,
			TableColumn conditionColumn, String other,
			boolean leftIsTableColumn) {
		if (Constants.STRING_TYPES.contains(
				conditionColumn.getColumnDataType())) {
			for (int i = 0; i < numberOfRows; i++) {
				if (leftIsTableColumn) {
					if (condition.evaluate((VarcharSQLType) conditionColumn.get(i),
							new VarcharSQLType(other))) {
						return i;
					}
				} else {
					if (condition.evaluate(new VarcharSQLType(other),
						(VarcharSQLType) conditionColumn.get(i))) {
						return i;
					}
				}
			}
		} else if (Constants.INTEGER_TYPES.contains(
				conditionColumn.getColumnDataType())) {
			for (int i = 0; i < numberOfRows; i++) {
				if (leftIsTableColumn) {
					if (condition.evaluate((IntSQLType) conditionColumn.get(i),
							new IntSQLType(other))) {
						return i;
					}
				} else {
					if (condition.evaluate(new IntSQLType(other),
						(IntSQLType) conditionColumn.get(i))) {
						return i;
					}
				}
			}
		}
		return -1;
	}
	private int getFirstMatch(BooleanExpression condition,
			TableColumn conditionColumn, TableColumn other)
					throws TypeMismatchException {
		if (!conditionColumn.getColumnDataType().
				equals(other.getColumnDataType())) {
			throw new TypeMismatchException();
		}
		if (Constants.STRING_TYPES.contains(
				conditionColumn.getColumnDataType())) {
			for (int i = 0; i < numberOfRows; i++) {
				if (condition.evaluate((VarcharSQLType)conditionColumn.get(i),
						(VarcharSQLType)other.get(i))) {
					return i;
				}
			}
		} else if (Constants.INTEGER_TYPES.contains(
				conditionColumn.getColumnDataType())) {
			for (int i = 0; i < numberOfRows; i++) {
				if (condition.evaluate((IntSQLType)conditionColumn.get(i),
						(IntSQLType)other.get(i))) {
					return i;
				}
			}
		}
		return -1;
	}
	private void deleteRow(int index) {
		for (TableColumn column : columns.values()) {
			column.remove(index);
		}
	}
	private void clearTable() {
		for (TableColumn column : columns.values()) {
			column.clearColumn();
		}
	}
	private void deleteMatching(BooleanExpression condition,
			String columnName, String other,
			boolean leftIsTableColumn)
					throws ColumnNotFoundException {
		if (!columns.containsKey(columnName)) {
			throw new ColumnNotFoundException();
		}
		int firstMatch = getFirstMatch(condition, columns.get(columnName),
			other, leftIsTableColumn);
		while (firstMatch != -1) {
			deleteRow(firstMatch);
			firstMatch = getFirstMatch(condition, columns.get(columnName),
					other, leftIsTableColumn);
		}
	}
	private void deleteMatching(BooleanExpression condition, String
			columnName, String otherColumnName)
					throws ColumnNotFoundException,
					TypeMismatchException {
		if (!columns.containsKey(columnName)) {
			throw new ColumnNotFoundException(columnName);
		}
		if (!columns.containsKey(otherColumnName)) {
			throw new ColumnNotFoundException(otherColumnName);
		}
		int firstMatch = getFirstMatch(condition, columns.get(columnName),
			columns.get(otherColumnName));
		while (firstMatch != -1) {
			deleteRow(firstMatch);
			firstMatch = getFirstMatch(condition, columns.get(columnName),
					columns.get(otherColumnName));
		}
	}
}
