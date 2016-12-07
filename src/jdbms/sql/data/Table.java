package jdbms.sql.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
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
/**
 * The class representing a sql column.
 * @author Moham
 */
public class Table {
	/**
	 * The name of the table.
	 */
	private final String tableName;
	/**
	 * A map between the name of
	 * the column and the actual
	 * {@link TableColumn}
	 */
	private final Map<String, TableColumn> tableColumns;
	/**
	 * An array list holding
	 * the name
	 */
	private final ArrayList<String> tableColumnNames;
	/**
	 * The number of rows in the table.
	 */
	private int numberOfRows;
	/** indicates a null value. **/
	private static final String NULL_INDICATOR = "null";
	/**
	 * Creates a table given its
	 * {@link TableCreationParameters}
	 * @param createTableParameters the table
	 * creation parameters
	 * @throws ColumnAlreadyExistsException
	 */
	public Table(final TableCreationParameters
			createTableParameters)
					throws ColumnAlreadyExistsException {
		this.tableName = createTableParameters.getTableName();
		tableColumns = new HashMap<>();
		tableColumnNames = new ArrayList<>();
		numberOfRows = 0;
		final ArrayList<ColumnIdentifier> colDefinitions
		= createTableParameters.getColumnDefinitions();
		for (final ColumnIdentifier column : colDefinitions) {
			addTableColumn(column.getName(), column.getType());
		}
	}
	/**
	 * Creates a table given its
	 * {@link TableIdentifier}
	 * @param tableIdentifier the table
	 * identifier
	 * @throws ColumnAlreadyExistsException
	 */
	public Table(final TableIdentifier tableIdentifier)
			throws ColumnAlreadyExistsException {
		this.tableName = tableIdentifier.getTableName();
		final ArrayList<ColumnIdentifier> columnIdentifiers
		= tableIdentifier.getColumnsIdentifiers();
		tableColumns = new HashMap<>();
		tableColumnNames = new ArrayList<>();
		for (final ColumnIdentifier columnIdentifier
				: columnIdentifiers) {
			addTableColumn(columnIdentifier.getName(),
					columnIdentifier.getType());
		}
	}
	/**
	 * Adds a new table column.
	 * @param columnName the name
	 * of the column
	 * @param columnDataType the data
	 * type of the column
	 * @throws ColumnAlreadyExistsException
	 */
	public void addTableColumn(final String columnName, final String columnDataType)
			throws ColumnAlreadyExistsException {
		if (tableColumns.containsKey(columnName.toUpperCase())) {
			throw new ColumnAlreadyExistsException(columnName);
		}
		final TableColumn newColumn = new TableColumn(columnName, columnDataType);
		tableColumns.put(columnName.toUpperCase(), newColumn);
		tableColumnNames.add(columnName);
		for (int i = 0 ; i < numberOfRows ; i++) {
			tableColumns.get(columnName.toUpperCase()).add(null);
		}
	}
	/**
	 * Inserts rows into the table.
	 * @param insertParameters the
	 * {@link InsertionParameters}
	 * @throws RepeatedColumnException
	 * @throws ColumnListTooLargeException
	 * @throws ColumnNotFoundException
	 * @throws ValueListTooLargeException
	 * @throws ValueListTooSmallException
	 * @throws TypeMismatchException
	 */
	public void insertRows(final InsertionParameters insertParameters)
			throws RepeatedColumnException,
			ColumnListTooLargeException,
			ColumnNotFoundException,
			ValueListTooLargeException, ValueListTooSmallException,
			TypeMismatchException {
		if (insertParameters.getColumns() == null) {
			for (final ArrayList<String> rowValue
					: insertParameters.getValues()) {
				if (rowValue.size()
						> tableColumnNames.size()) {
					throw new ValueListTooLargeException();
				} else if (rowValue.size()
						< tableColumnNames.size()) {
					throw new ValueListTooSmallException();
				}
			}
			for (final ArrayList<String> rowValue
					: insertParameters.getValues()) {
				insertRow(rowValue);
			}
		} else {
			final ArrayList<String> columnNames
			= insertParameters.getColumns();
			for (final String column : columnNames) {
				if (!tableColumns.containsKey(
						column.toUpperCase())) {
					throw new ColumnNotFoundException(column);
				}
			}
			if (new HashSet<>(columnNames).size()
					< columnNames.size()) {
				throw new RepeatedColumnException();
			}
			final Set<String> nullCells
			= new HashSet<>(tableColumns.keySet());
			for (final String columnName : columnNames) {
				nullCells.remove(columnName.toUpperCase());
			}
			for (final ArrayList<String> rowValue
					: insertParameters.getValues()) {
				if (rowValue.size() >
				insertParameters.getColumns().size()) {
					throw new ValueListTooLargeException();
				} else if (rowValue.size() <
						insertParameters.getColumns().size()) {
					throw new ValueListTooSmallException();
				}
			}
			for (final ArrayList<String> rowValue
					: insertParameters.getValues()) {
				insertRow(insertParameters.getColumns(
						), rowValue, nullCells);
			}
		}
	}
	/**
	 * Inserts a row.
	 * @param values the values in the row
	 * @throws ValueListTooLargeException
	 * @throws ValueListTooSmallException
	 * @throws TypeMismatchException
	 */
	private void insertRow(final ArrayList<String> values)
			throws ValueListTooLargeException, ValueListTooSmallException,
			TypeMismatchException {
		final DataTypesValidator dataTypesValidator
		= new DataTypesValidator();
		int index = 0;
		for (final String column : tableColumnNames) {
			if (values.get(index).equals(NULL_INDICATOR)) {
				tableColumns.get(
						column.toUpperCase()).add(null);
			} else if (!dataTypesValidator.
					match(tableColumns.get(
							column.toUpperCase()).getColumnDataType(
									), values.get(index))) {
				throw new TypeMismatchException();
			} else {
				tableColumns.get(column.toUpperCase()).add(
						values.get(index));
			}
			index++;
		}
		numberOfRows++;
	}
	/**
	 * Inserts a row.
	 * @param columnNames the columns
	 * in which the values will get
	 * inserted
	 * @param values the values
	 * @param nullCells the columns
	 * to be filled with null
	 * @throws RepeatedColumnException
	 * @throws ColumnListTooLargeException
	 * @throws ColumnNotFoundException
	 * @throws ValueListTooLargeException
	 * @throws TypeMismatchException
	 */
	private void insertRow(final ArrayList<String> columnNames,
			final ArrayList<String> values, final Set<String> nullCells)
					throws RepeatedColumnException, ColumnListTooLargeException,
					ColumnNotFoundException, ValueListTooLargeException,
					TypeMismatchException {
		final DataTypesValidator dataTypesValidator
		= new DataTypesValidator();
		for (int i = 0; i < columnNames.size(); i++) {
			if (values.get(i).equals(NULL_INDICATOR)) {
				tableColumns.get(
						columnNames.get(i).toUpperCase()).add(null);
			} else if (!dataTypesValidator.match(tableColumns.get(
					columnNames.get(i).toUpperCase()).
					getColumnDataType(), values.get(i))) {
				throw new TypeMismatchException();
			} else {
				tableColumns.get(columnNames.get(i).
						toUpperCase()).add(values.get(i));
			}
		}
		for (final String nullCell : nullCells) {
			tableColumns.get(nullCell).add(null);
		}
		numberOfRows++;
	}
	/**
	 * Generated the table identifier
	 * of the table.
	 * @return The table indentifier.
	 */
	public TableIdentifier getTableIdentifier() {
		final ArrayList<ColumnIdentifier> columnIdentifiers
		= new ArrayList<>();
		for (final String name : tableColumnNames) {
			columnIdentifiers.add(new
					ColumnIdentifier(name,
							tableColumns.get(
									name.toUpperCase()).getColumnDataType()));
		}
		return new TableIdentifier(tableName, columnIdentifiers);
	}
	/**
	 * Gets the columns in a table.
	 * @return the map representing the
	 * table columns
	 */
	public Map<String, TableColumn> getColumns() {
		return tableColumns;
	}
	/**
	 * Selects values from a table.
	 * @param selectParameters the parameters
	 * of the select statement
	 * @return the select output
	 * @throws ColumnNotFoundException
	 * @throws TypeMismatchException
	 */
	public SelectQueryOutput selectFromTable(final SelectionParameters
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
		final Set<String> uniqueColumnNames = new LinkedHashSet<>(columnNames);
		columnNames = new ArrayList<>(uniqueColumnNames);
		for (final String column : columnNames) {
			if (!tableColumns.containsKey(column.toUpperCase())) {
				throw new ColumnNotFoundException(column);
			}
		}
		final SelectQueryOutput output = new SelectQueryOutput();
		output.setColumns(columnNames);
		final ArrayList<ArrayList<String>> rows = new ArrayList<>();
		int index = 0;
		for (final int i : matches) {
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
	/**
	 * Performs the sql update statement.
	 * @param updateParameters the
	 * update statement parameters
	 * @throws ColumnNotFoundException
	 * @throws TypeMismatchException
	 */
	public void updateTable(final UpdatingParameters updateParameters)
			throws ColumnNotFoundException, TypeMismatchException {
		final ArrayList<AssignmentExpression> assignments
		= updateParameters.getAssignmentList();
		ArrayList<Integer> matches;
		if (updateParameters.getCondition() == null) {
			matches = getAllRows();
		} else {
			matches = getAllMatches(updateParameters.getCondition());
		}
		for (final AssignmentExpression assignment : assignments) {
			AssignColumn(assignment, matches);
		}
	}
	/**
	 * Gets all the rows matching
	 * a boolean expression.
	 * @param condition the boolean expression
	 * @return a list of the matching rows
	 * @throws ColumnNotFoundException
	 * @throws TypeMismatchException
	 */
	private ArrayList<Integer> getAllMatches(
			final BooleanExpression condition)
					throws ColumnNotFoundException, TypeMismatchException {
		ArrayList<Integer> matches = new ArrayList<>();
		if(condition.leftOperandIsConstant()
				&& condition.rightOperandIsConstant()) {
			if (condition.evaluateConstantExpression()) {
				matches = getAllRows();
			}
		} else if (condition.leftOperandIsColumnName()
				&& condition.rightOperandIsConstant()) {
			final String columnName = condition.getLeftOperand();
			if (!tableColumns.containsKey(columnName.toUpperCase())) {
				throw new ColumnNotFoundException(columnName);
			}
			matches = getAllMatches(condition,
					tableColumns.get(columnName.toUpperCase()),
					condition.getRightOperand(), true);
		} else if (condition.rightOperandIsColumnName()
				&& condition.leftOperandIsConstant()) {
			final String columnName = condition.getRightOperand();
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
	/**
	 * Assigns a value to a column.
	 * @param assignment the assignment
	 * expression.
	 * @param matches the rows
	 * matching the condition of the
	 * assignment
	 * @throws ColumnNotFoundException
	 * @throws TypeMismatchException
	 */
	private void AssignColumn(final AssignmentExpression assignment,
			final ArrayList<Integer> matches) throws ColumnNotFoundException,
	TypeMismatchException {
		if (!assignment.leftOperandIsColumnName() ||
				!assignment.rightOperandIsConstant() ||
				!tableColumns.containsKey(
						assignment.getLeftOperand().toUpperCase())) {
			throw new ColumnNotFoundException(
					assignment.getLeftOperand());
		}
		for (final int i : matches) {
			tableColumns.get(assignment.
					getLeftOperand().toUpperCase()).
			assignCell(i, assignment.getRightOperand());
		}
	}
	/**
	 * Gets a list of all the names of the columns
	 * in this table.
	 * @return a list of all the names of the columns
	 * in this table
	 */
	public ArrayList<String> getColumnNames() {
		return tableColumnNames;
	}
	/**
	 * Gets the number of rows.
	 * @return the number of rows
	 */
	public int getNumberOfRows() {
		return numberOfRows;
	}
	/**
	 * Gets the table name.
	 * @return the table name.
	 */
	public String getName() {
		return tableName;
	}
	/**
	 * Deletes all rows matching a boolean condition.
	 * @param condition the boolean condition.
	 * @throws ColumnNotFoundException
	 * @throws TypeMismatchException
	 */
	public void deleteRows(final BooleanExpression condition)
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
			final String columnName = condition.getLeftOperand();
			deleteMatching(condition, columnName,
					condition.getRightOperand(), true);
		} else if (condition.rightOperandIsColumnName()
				&& condition.leftOperandIsConstant()) {
			final String columnName = condition.getRightOperand();
			deleteMatching(condition, columnName,
					condition.getLeftOperand(), false);
		} else if (condition.rightOperandIsColumnName() &&
				condition.leftOperandIsColumnName()) {
			deleteMatching(condition, condition.getLeftOperand(),
					condition.getRightOperand());
		}
	}
	/**
	 * Deletes all the rows matching a
	 * boolean condition.
	 * @param condition the boolean condition
	 * @param columnName the name of the
	 * column in the condition
	 * @param other the value
	 * compared to the column
	 * @param leftIsTableColumn specifies
	 * if the left hand side of the expression
	 * is a column or a value
	 * @throws ColumnNotFoundException
	 * @throws TypeMismatchException
	 */
	private void deleteMatching(final BooleanExpression condition,
			final String columnName, final String other,
			final boolean leftIsTableColumn)
					throws ColumnNotFoundException,
					TypeMismatchException {
		if (!tableColumns.containsKey(columnName.toUpperCase())) {
			throw new ColumnNotFoundException(columnName);
		}
		final DataTypesValidator validator
		= new DataTypesValidator();
		if (!validator.match(tableColumns.get(
				columnName.toUpperCase()).
				getColumnDataType(), other)) {
			throw new TypeMismatchException();
		}
		int firstMatch = getFirstMatch(condition,
				tableColumns.get(columnName.toUpperCase()),
				other, leftIsTableColumn);
		while (firstMatch != -1) {
			deleteRow(firstMatch);
			firstMatch = getFirstMatch(condition,
					tableColumns.get(columnName.toUpperCase()),
					other, leftIsTableColumn);
		}
	}
	/**
	 * Deletes all the rows matching a
	 * boolean condition.
	 * @param condition the boolean condition
	 * @param columnName the name of the
	 * column in the condition
	 * @param otherColumnName the value
	 * of the other column
	 * @throws ColumnNotFoundException
	 * @throws TypeMismatchException
	 */
	private void deleteMatching(final BooleanExpression condition, final String
			columnName, final String otherColumnName)
					throws ColumnNotFoundException,
					TypeMismatchException {
		if (!tableColumns.
				containsKey(
						columnName.toUpperCase())) {
			throw
			new
			ColumnNotFoundException(
					columnName);
		}
		if (!tableColumns.
				containsKey(
						otherColumnName.toUpperCase())) {
			throw new ColumnNotFoundException(otherColumnName);
		}
		final DataTypesValidator validator
		= new DataTypesValidator();
		if (!validator.checkDataTypes(
				tableColumns.get(
						columnName.toUpperCase()).
				getColumnDataType(),
				tableColumns.get(
						otherColumnName.toUpperCase()).
				getColumnDataType())) {
			throw new TypeMismatchException();
		}
		int firstMatch = getFirstMatch(condition,
				tableColumns.get(columnName.toUpperCase()),
				tableColumns.get(otherColumnName.toUpperCase()));
		while (firstMatch != -1) {
			deleteRow(firstMatch);
			firstMatch = getFirstMatch(condition,
					tableColumns.get(columnName.toUpperCase()),
					tableColumns.get(otherColumnName.toUpperCase()));
		}
	}
	/**
	 * gets the first match for
	 * a boolean expression.
	 * @param condition the boolean
	 * condition
	 * @param conditionColumn the column
	 * of the condition
	 * @param other the value
	 * compared to the column
	 * @param leftIsTableColumn specifies
	 * if the left hand side of the expression
	 * is a column or a value
	 * @return the index of the first match
	 */
	private int getFirstMatch(final BooleanExpression condition,
			final TableColumn conditionColumn, final String other,
			final boolean leftIsTableColumn) {
		if (Constants.STRING_TYPES.contains(
				conditionColumn.getColumnDataType())) {
			for (int i = 0; i < numberOfRows; i++) {
				final String cellValue
				= conditionColumn.get(i).getStringValue();
				if (leftIsTableColumn) {
					if (condition.evaluate(
							new VarcharSQLType(removeQuotes
									(cellValue)),
							new VarcharSQLType(
									removeQuotes(other)))) {
						return i;
					}
				} else {
					if (condition.evaluate(
							new VarcharSQLType(
									removeQuotes(other)),
							new VarcharSQLType(
									removeQuotes(cellValue)))) {
						return i;
					}
				}
			}
		} else if (Constants.INTEGER_TYPES.contains(
				conditionColumn.getColumnDataType())) {
			for (int i = 0; i < numberOfRows; i++) {
				if (leftIsTableColumn) {
					if (condition.evaluate((IntSQLType)
							conditionColumn.get(i),
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
	/**
	 * gets the first match for
	 * a boolean expression.
	 * @param condition the boolean
	 * condition
	 * @param conditionColumn the column
	 * of the condition
	 * @param other the other column
	 * @return the index of the first match
	 */
	private int getFirstMatch(final BooleanExpression condition,
			final TableColumn conditionColumn,
			final TableColumn other) {
		if (Constants.STRING_TYPES.contains(
				conditionColumn.getColumnDataType())) {
			for (int i = 0; i < numberOfRows; i++) {
				final String leftCellValue
				= conditionColumn.get(i).getStringValue();
				final String rightCellValue
				= other.get(i).getStringValue();
				if (condition.evaluate(
						new VarcharSQLType(
								leftCellValue.substring(1,
										leftCellValue.length() - 1)),
						new VarcharSQLType(
								rightCellValue.substring(1,
										rightCellValue.length() - 1)))) {
					return i;
				}
			}
		} else if (Constants.INTEGER_TYPES.contains(
				conditionColumn.getColumnDataType())) {
			for (int i = 0; i < numberOfRows; i++) {
				if (condition.evaluate((
						IntSQLType)conditionColumn.get(i),
						(IntSQLType)other.get(i))) {
					return i;
				}
			}
		}
		return -1;
	}
	/**
	 * Gets all matches of a boolean expression.
	 * @param condition the boolean condition
	 * @param conditionColumn the column
	 * of the condition
	 * @param other the value
	 * compared to the column
	 * @param leftIsTableColumn specifies
	 * if the left hand side of the expression
	 * is a column or a value
	 * @return
	 */
	private ArrayList<Integer> getAllMatches(final BooleanExpression condition,
			final TableColumn conditionColumn, final String other,
			final boolean leftIsTableColumn) {
		final ArrayList<Integer> matches = new ArrayList<>();
		if (Constants.STRING_TYPES.contains(
				conditionColumn.getColumnDataType())) {
			for (int i = 0; i < numberOfRows; i++) {
				final String cellValue
				= conditionColumn.get(i).getStringValue();
				if (leftIsTableColumn) {
					if (condition.evaluate(
							new VarcharSQLType(cellValue.substring(1,
									cellValue.length() - 1)),
							new VarcharSQLType(removeQuotes(other)))) {
						matches.add(i);
					}
				} else {
					if (condition.evaluate(
							new VarcharSQLType(removeQuotes(other)),
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
					if (condition.evaluate(
							(IntSQLType) conditionColumn.get(i),
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
	/**
	 * gets all matches for
	 * a boolean expression.
	 * @param condition the boolean
	 * condition
	 * @param conditionColumn the column
	 * of the condition
	 * @param other the other column
	 * @return a list of all matches
	 */
	private ArrayList<Integer> getAllMatches(
			final BooleanExpression condition,
			final TableColumn conditionColumn,
			final TableColumn other)
					throws TypeMismatchException {
		final DataTypesValidator validator
		= new DataTypesValidator();
		if (!validator.checkDataTypes(
				conditionColumn.getColumnDataType(),
				other.getColumnDataType())) {
			throw new TypeMismatchException();
		}
		final ArrayList<Integer> matches
		= new ArrayList<>();
		if (Constants.STRING_TYPES.contains(
				conditionColumn.getColumnDataType())) {
			for (int i = 0; i < numberOfRows; i++) {
				final String leftCellValue
				= conditionColumn.get(i).getStringValue();
				final String rightCellValue
				= other.get(i).getStringValue();
				if (condition.evaluate(
						new VarcharSQLType(
								leftCellValue.substring(1,
										leftCellValue.length() - 1)),
						new VarcharSQLType(rightCellValue.substring(1,
								rightCellValue.length() - 1)))) {
					matches.add(i);
				}
			}
		} else if (Constants.INTEGER_TYPES.contains(
				conditionColumn.getColumnDataType())) {
			for (int i = 0; i < numberOfRows; i++) {
				if (condition.evaluate(
						(IntSQLType)conditionColumn.get(i),
						(IntSQLType)other.get(i))) {
					matches.add(i);
				}
			}
		}
		return matches;
	}
	/**
	 * Deletes a row from the table.
	 * @param index the index of the row
	 */
	private void deleteRow(final int index) {
		for (final TableColumn column
				: tableColumns.values()) {
			column.remove(index);
		}
		numberOfRows--;
	}
	/**
	 * Clears the table.
	 */
	private void clearTable() {
		for (final TableColumn column
				: tableColumns.values()) {
			column.clearColumn();
		}
		numberOfRows = 0;
	}
	/**
	 * Gets all rows in the table.
	 * @return a list of all rows
	 * in the table
	 */
	private ArrayList<Integer> getAllRows() {
		final ArrayList<Integer> rows = new ArrayList<>();
		for (int i = 0 ; i < numberOfRows ; i++) {
			rows.add(i);
		}
		return rows;
	}
	/**
	 * removes the quotes from a string
	 * value.
	 * @param s the string
	 * @return the quote-less string
	 */
	private String removeQuotes(final String s) {
		return s.substring(1, s.length() - 1);
	}
}
