package jdbms.sql.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jdbms.sql.data.query.SelectQueryOutput;
import jdbms.sql.datatypes.IntSQLType;
import jdbms.sql.datatypes.VarcharSQLType;
import jdbms.sql.datatypes.util.DataTypesValidator;
import jdbms.sql.exceptions.ColumnAlreadyExistsException;
import jdbms.sql.exceptions.ColumnListTooLargeException;
import jdbms.sql.exceptions.ColumnNotFoundException;
import jdbms.sql.exceptions.RepeatedColumnException;
import jdbms.sql.exceptions.TypeMismatchException;
import jdbms.sql.exceptions.ValueListTooLargeException;
import jdbms.sql.exceptions.ValueListTooSmallException;
import jdbms.sql.parsing.expressions.math.AssignmentExpression;
import jdbms.sql.parsing.expressions.math.BooleanExpression;
import jdbms.sql.parsing.properties.InsertionParameters;
import jdbms.sql.parsing.properties.SelectionParameters;
import jdbms.sql.parsing.properties.TableCreationParameters;
import jdbms.sql.parsing.properties.UpdatingParameters;
import jdbms.sql.parsing.util.Constants;

public class Table {

	private String tableName;
	private Map<String, TableColumn> tableColumns;
	private ArrayList<String> tableColumnNames;
	private int numberOfRows;
	public Table(TableCreationParameters createTableParameters)
			throws ColumnAlreadyExistsException {
		this.tableName = createTableParameters.getTableName();
		tableColumns = new HashMap<>();
		tableColumnNames = new ArrayList<>();
		numberOfRows = 0;
		ArrayList<ColumnIdentifier> colDefinitions
		= createTableParameters.getColumnDefinitions();
		for (ColumnIdentifier column : colDefinitions) {
				addTableColumn(column.getName(), column.getType());
		}
	}
	public Table(TableIdentifier tableIdentifier)
			throws ColumnAlreadyExistsException {
		this.tableName = tableIdentifier.getTableName();
		ArrayList<ColumnIdentifier> columnIdentifiers
		= tableIdentifier.getColumnsIdentifiers();
		tableColumns = new HashMap<>();
		tableColumnNames = new ArrayList<>();
		for (ColumnIdentifier columnIdentifier
				: columnIdentifiers) {
			addTableColumn(columnIdentifier.getName(),
					columnIdentifier.getType());
		}
	}
	public void addTableColumn(String columnName, String columnDataType)
			throws ColumnAlreadyExistsException {
		if (tableColumns.containsKey(columnName.toUpperCase())) {
			throw new ColumnAlreadyExistsException(columnName);
		}
		TableColumn newColumn = new TableColumn(columnName, columnDataType);
		tableColumns.put(columnName.toUpperCase(), newColumn);
		tableColumnNames.add(columnName);
		for (int i = 0 ; i < numberOfRows ; i++) {
			tableColumns.get(columnName.toUpperCase()).add(null);
		}
	}
	public void insertRows(InsertionParameters insertParameters)
			throws RepeatedColumnException,
			ColumnListTooLargeException,
			ColumnNotFoundException,
			ValueListTooLargeException, ValueListTooSmallException,
			TypeMismatchException {
		if (insertParameters.getColumns() == null) {
			for (ArrayList<String> rowValue
					: insertParameters.getValues()) {
				if (rowValue.size()
						> tableColumnNames.size()) {
					throw new ValueListTooLargeException();
				} else if (rowValue.size()
						< tableColumnNames.size()) {
					throw new ValueListTooSmallException();
				}
			}
			for (ArrayList<String> rowValue
					: insertParameters.getValues()) {
				insertRow(rowValue);
			}
		} else {
			ArrayList<String> columnNames
			= insertParameters.getColumns();
			for (String column : columnNames) {
				if (!tableColumns.containsKey(
						column.toUpperCase())) {
					throw new ColumnNotFoundException(column);
				}
			}
			if (new HashSet<>(columnNames).size()
					< columnNames.size()) {
				throw new RepeatedColumnException();
			}
			Set<String> nullCells
			= new HashSet<>(tableColumns.keySet());
			for (String columnName : columnNames) {
				nullCells.remove(columnName.toUpperCase());
			}
			for (ArrayList<String> rowValue
					: insertParameters.getValues()) {
				if (rowValue.size() >
				insertParameters.getColumns().size()) {
					throw new ValueListTooLargeException();
				} else if (rowValue.size() <
						insertParameters.getColumns().size()) {
					throw new ValueListTooSmallException();
				}
			}
			for (ArrayList<String> rowValue
					: insertParameters.getValues()) {
				insertRow(insertParameters.getColumns(
						), rowValue, nullCells);
			}
		}
	}
	private void insertRow(ArrayList<String> values)
		throws ValueListTooLargeException, ValueListTooSmallException,
		TypeMismatchException {
		DataTypesValidator dataTypesValidator
		= new DataTypesValidator();
		int index = 0;
		for (String column : tableColumnNames) {
			if (!dataTypesValidator.match(tableColumns.get(
					column.toUpperCase()).getColumnDataType(
							), values.get(index))) {
				throw new TypeMismatchException();
			}
			tableColumns.get(column.toUpperCase()).add(values.get(index));
			index++;
		}
		numberOfRows++;
	}
	private void insertRow(ArrayList<String> columnNames,
			ArrayList<String> values, Set<String> nullCells)
			throws RepeatedColumnException, ColumnListTooLargeException,
			ColumnNotFoundException, ValueListTooLargeException,
			TypeMismatchException {
		DataTypesValidator dataTypesValidator
		= new DataTypesValidator();
		for (int i = 0; i < columnNames.size(); i++) {
			if (!dataTypesValidator.match(tableColumns.get(
					columnNames.get(i).toUpperCase()).
					getColumnDataType(), values.get(i))) {
				throw new TypeMismatchException();
			}
			tableColumns.get(columnNames.get(i).
					toUpperCase()).add(values.get(i));
		}
		for (String nullCell : nullCells) {
			tableColumns.get(nullCell).add(null);
		}
		numberOfRows++;
	}

	public TableIdentifier getTableIdentifier() {
		ArrayList<ColumnIdentifier> columnIdentifiers
		= new ArrayList<>();
		for (String name : tableColumnNames) {
			columnIdentifiers.add(new
					ColumnIdentifier(name,
					tableColumns.get(
							name.toUpperCase()).getColumnDataType()));
		}
		return new TableIdentifier(tableName, columnIdentifiers);
	}

	public Map<String, TableColumn> getColumns() {
		return tableColumns;
	}
	public SelectQueryOutput selectFromTable(SelectionParameters
			selectParameters) throws ColumnNotFoundException,
	TypeMismatchException {
		ArrayList<Integer> matches = null;
		if (selectParameters.getCondition() == null) {
			matches = getAllRows();
		} else {
			matches = getAllMatches(selectParameters.getCondition());
		}
		ArrayList<String> columnNames = selectParameters.getColumns();
		if (columnNames.size() == 1 &&
				columnNames.get(0).equals("*")) {
			columnNames = new ArrayList<>(tableColumnNames);
		}
		Set<String> uniqueColumnNames = new HashSet<>(columnNames);
		columnNames = new ArrayList<>(uniqueColumnNames);
		for (String column : columnNames) {
			if (!tableColumns.containsKey(column.toUpperCase())) {
				throw new ColumnNotFoundException(column);
			}
		}
		SelectQueryOutput output = new SelectQueryOutput();
		output.setColumns(columnNames);
		ArrayList<ArrayList<String>> rows = new ArrayList<>();
		int index = 0;
		for (int i : matches) {
			rows.add(new ArrayList<>());
			for (int j = 0 ; j < columnNames.size() ; j++) {
				rows.get(index).add(tableColumns.get(
						columnNames.get(j).toUpperCase()).
						get(i).getStringValue());
			}
			index++;
		}
		output.setRows(rows);
		return output;
	}
	public void updateTable(UpdatingParameters updateParameters)
			throws ColumnNotFoundException, TypeMismatchException {
		ArrayList<AssignmentExpression> assignments
		= updateParameters.getAssignmentList();
		ArrayList<Integer> matches;
		if (updateParameters.getCondition() == null) {
			matches = getAllRows();
		} else {
			matches = getAllMatches(updateParameters.getCondition());
		}
		for (AssignmentExpression assignment : assignments) {
			AssignColumn(assignment, matches);
		}
	}
	private ArrayList<Integer> getAllMatches(BooleanExpression condition)
			throws ColumnNotFoundException, TypeMismatchException {
		ArrayList<Integer> matches = new ArrayList<>();
		if(condition.leftOperandIsConstant()
				&& condition.rightOperandIsConstant()) {
			if (condition.evaluateConstantExpression()) {
				matches = getAllRows();
			}
		} else if (condition.leftOperandIsColumnName()
				&& condition.rightOperandIsConstant()) {
			String columnName = condition.getLeftOperand();
			if (!tableColumns.containsKey(columnName.toUpperCase())) {
				throw new ColumnNotFoundException(columnName);
			}
			matches = getAllMatches(condition,
					tableColumns.get(columnName.toUpperCase()),
					condition.getRightOperand(), true);
		} else if (condition.rightOperandIsColumnName()
				&& condition.leftOperandIsConstant()) {
			String columnName = condition.getRightOperand();
			if (!tableColumns.containsKey(columnName.toUpperCase())) {
				throw new ColumnNotFoundException(columnName);
			}
			matches = getAllMatches(condition,
					tableColumns.get(columnName.toUpperCase()),
					condition.getLeftOperand(), false);
		} else if (condition.rightOperandIsColumnName() &&
				condition.leftOperandIsColumnName()) {
			if (!tableColumns.containsKey(condition.
					getLeftOperand().toUpperCase())) {
				throw new ColumnNotFoundException(condition.
						getLeftOperand());
			}
			if (!tableColumns.containsKey(condition.
					getRightOperand().toUpperCase())) {
				throw new ColumnNotFoundException(condition.
						getRightOperand());
			}
			matches = getAllMatches(condition, tableColumns.get(
					condition.getLeftOperand().toUpperCase()),
					tableColumns.get(condition.getRightOperand().
							toUpperCase()));
		}
		return matches;
	}
	private void AssignColumn(AssignmentExpression assignment,
			ArrayList<Integer> matches) throws ColumnNotFoundException,
			TypeMismatchException {
		if (!assignment.leftOperandIsColumnName() ||
				!assignment.rightOperandIsConstant() ||
				!tableColumns.containsKey(assignment.getLeftOperand().toUpperCase())) {
			throw new ColumnNotFoundException(assignment.getLeftOperand());
		}
		for (int i : matches) {
			tableColumns.get(assignment.getLeftOperand().toUpperCase()).
			assignCell(i, assignment.getRightOperand());
		}
	}

	public ArrayList<String> getColumnNames() {
		return tableColumnNames;
	}

	public int getNumberOfRows() {
		return numberOfRows;
	}

	public String getName() {
		return tableName;
	}
	public void deleteRows(BooleanExpression condition)
			throws ColumnNotFoundException, TypeMismatchException {
		if (condition == null) {
			clearTable();
			return;
		}
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
	private void deleteMatching(BooleanExpression condition,
			String columnName, String other,
			boolean leftIsTableColumn)
					throws ColumnNotFoundException {
		if (!tableColumns.containsKey(columnName.toUpperCase())) {
			throw new ColumnNotFoundException(columnName);
		}
		int firstMatch = getFirstMatch(condition, tableColumns.get(columnName.toUpperCase()),
				other, leftIsTableColumn);
		while (firstMatch != -1) {
			deleteRow(firstMatch);
			firstMatch = getFirstMatch(condition, tableColumns.get(columnName.toUpperCase()),
					other, leftIsTableColumn);
		}
	}
	private void deleteMatching(BooleanExpression condition, String
			columnName, String otherColumnName)
					throws ColumnNotFoundException,
					TypeMismatchException {
		if (!tableColumns.containsKey(columnName.toUpperCase())) {
			throw new ColumnNotFoundException(columnName);
		}
		if (!tableColumns.containsKey(otherColumnName.toUpperCase())) {
			throw new ColumnNotFoundException(otherColumnName);
		}
		int firstMatch = getFirstMatch(condition, tableColumns.get(columnName.toUpperCase()),
				tableColumns.get(otherColumnName));
		while (firstMatch != -1) {
			deleteRow(firstMatch);
			firstMatch = getFirstMatch(condition, tableColumns.get(columnName.toUpperCase()),
					tableColumns.get(otherColumnName.toUpperCase()));
		}
	}
	private int getFirstMatch(BooleanExpression condition,
			TableColumn conditionColumn, String other,
			boolean leftIsTableColumn) {
		if (Constants.STRING_TYPES.contains(
				conditionColumn.getColumnDataType())) {
			for (int i = 0; i < numberOfRows; i++) {
				String cellValue = conditionColumn.get(i).getStringValue();
				if (leftIsTableColumn) {
					if (condition.evaluate(new VarcharSQLType(removeQuotes
							(cellValue)), new VarcharSQLType(removeQuotes(other)))) {
						return i;
					}
				} else {
					if (condition.evaluate(new VarcharSQLType(removeQuotes(other)),
							new VarcharSQLType(removeQuotes(cellValue)))) {
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
		DataTypesValidator validator = new DataTypesValidator();
		if (!validator.checkDataTypes(conditionColumn.getColumnDataType(),
				other.getColumnDataType())) {
			throw new TypeMismatchException();
		}
		if (Constants.STRING_TYPES.contains(
				conditionColumn.getColumnDataType())) {
			for (int i = 0; i < numberOfRows; i++) {
				String leftCellValue = conditionColumn.get(i).getStringValue();
				String rightCellValue = other.get(i).getStringValue();
				if (condition.evaluate(new VarcharSQLType(leftCellValue.substring(1,
						leftCellValue.length() - 1)),
						new VarcharSQLType(rightCellValue.substring(1,
								rightCellValue.length() - 1)))) {
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
	private ArrayList<Integer> getAllMatches(BooleanExpression condition,
			TableColumn conditionColumn, String other,
			boolean leftIsTableColumn) {
		ArrayList<Integer> matches = new ArrayList<>();
		if (Constants.STRING_TYPES.contains(
				conditionColumn.getColumnDataType())) {
			for (int i = 0; i < numberOfRows; i++) {
				String cellValue = conditionColumn.get(i).getStringValue();
				if (leftIsTableColumn) {
					if (condition.evaluate(new VarcharSQLType(cellValue.substring(1,
							cellValue.length() - 1)),
							new VarcharSQLType(removeQuotes(other)))) {
						matches.add(i);
					}
				} else {
					if (condition.evaluate(new VarcharSQLType(removeQuotes(other)),
							new VarcharSQLType(cellValue.substring(1,
									cellValue.length() - 1)))) {
						matches.add(i);
					}
				}
			}
		} else if (Constants.INTEGER_TYPES.contains(
				conditionColumn.getColumnDataType())) {
			for (int i = 0; i < numberOfRows; i++) {
				if (leftIsTableColumn) {
					if (condition.evaluate((IntSQLType) conditionColumn.get(i),
							new IntSQLType(other))) {
						matches.add(i);
					}
				} else {
					if (condition.evaluate(new IntSQLType(other),
						(IntSQLType) conditionColumn.get(i))) {
						matches.add(i);
					}
				}
			}
		}
		return matches;
	}
	private ArrayList<Integer> getAllMatches(BooleanExpression condition,
			TableColumn conditionColumn, TableColumn other)
					throws TypeMismatchException {
		DataTypesValidator validator = new DataTypesValidator();
		if (!validator.checkDataTypes(conditionColumn.getColumnDataType(),
				other.getColumnDataType())) {
			throw new TypeMismatchException();
		}
		ArrayList<Integer> matches = new ArrayList<>();
		if (Constants.STRING_TYPES.contains(
				conditionColumn.getColumnDataType())) {
			for (int i = 0; i < numberOfRows; i++) {
				String leftCellValue = conditionColumn.get(i).getStringValue();
				String rightCellValue = other.get(i).getStringValue();
				if (condition.evaluate(new VarcharSQLType(leftCellValue.substring(1,
						leftCellValue.length() - 1)),
						new VarcharSQLType(rightCellValue.substring(1,
								rightCellValue.length() - 1)))) {
					matches.add(i);
				}
			}
		} else if (Constants.INTEGER_TYPES.contains(
				conditionColumn.getColumnDataType())) {
			for (int i = 0; i < numberOfRows; i++) {
				if (condition.evaluate((IntSQLType)conditionColumn.get(i),
						(IntSQLType)other.get(i))) {
					matches.add(i);
				}
			}
		}
		return matches;
	}
	private void deleteRow(int index) {
		for (TableColumn column : tableColumns.values()) {
			column.remove(index);
		}
		numberOfRows--;
	}
	private void clearTable() {
		for (TableColumn column : tableColumns.values()) {
			column.clearColumn();
		}
		numberOfRows = 0;
	}
	private ArrayList<Integer> getAllRows() {
		ArrayList<Integer> rows = new ArrayList<>();
		for (int i = 0 ; i < numberOfRows ; i++) {
			rows.add(i);
		}
		return rows;
	}
	private String removeQuotes(String s) {
		return s.substring(1, s.length() - 1);
	}
}
