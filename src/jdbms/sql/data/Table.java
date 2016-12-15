package jdbms.sql.data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import jdbms.sql.data.query.SelectQueryOutput;
import jdbms.sql.data.util.ComparatorChain;
import jdbms.sql.data.util.SQLComparator;
import jdbms.sql.datatypes.BigIntSQLType;
import jdbms.sql.datatypes.DateSQLType;
import jdbms.sql.datatypes.DateTimeSQLType;
import jdbms.sql.datatypes.DoubleSQLType;
import jdbms.sql.datatypes.FloatSQLType;
import jdbms.sql.datatypes.IntSQLType;
import jdbms.sql.datatypes.SQLType;
import jdbms.sql.datatypes.VarcharSQLType;
import jdbms.sql.datatypes.util.DataTypesValidator;
import jdbms.sql.exceptions.ColumnAlreadyExistsException;
import jdbms.sql.exceptions.ColumnListTooLargeException;
import jdbms.sql.exceptions.ColumnNotFoundException;
import jdbms.sql.exceptions.InvalidDateFormatException;
import jdbms.sql.exceptions.RepeatedColumnException;
import jdbms.sql.exceptions.TypeMismatchException;
import jdbms.sql.exceptions.ValueListTooLargeException;
import jdbms.sql.exceptions.ValueListTooSmallException;
import jdbms.sql.parsing.expressions.math.AssignmentExpression;
import jdbms.sql.parsing.expressions.math.BooleanExpression;
import jdbms.sql.parsing.expressions.util.ColumnOrder;
import jdbms.sql.parsing.properties.DropColumnParameters;
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
	/**
	 * Creates a table given its
	 * {@link TableCreationParameters}
	 * @param createTableParameters the table
	 * creation parameters
	 * @throws ColumnAlreadyExistsException
	 * @throws InvalidDateFormatException
	 */
	public Table(final TableCreationParameters
			createTableParameters)
					throws ColumnAlreadyExistsException,
					InvalidDateFormatException {
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
	 * @throws InvalidDateFormatException
	 */
	public Table(final TableIdentifier tableIdentifier)
			throws ColumnAlreadyExistsException, InvalidDateFormatException {
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
	 * @throws InvalidDateFormatException
	 */
	public int addTableColumn(final String columnName, final String columnDataType)
			throws ColumnAlreadyExistsException, InvalidDateFormatException {
		if (tableColumns.containsKey(columnName.toUpperCase())) {
			throw new ColumnAlreadyExistsException(columnName);
		}
		final TableColumn newColumn = new TableColumn(columnName, columnDataType);
		tableColumns.put(columnName.toUpperCase(), newColumn);
		tableColumnNames.add(columnName);
		for (int i = 0 ; i < numberOfRows ; i++) {
			tableColumns.get(columnName.toUpperCase()).add(null);
		}
		return 0;
	}
	/**
	 * Drops a table column.
	 * @param parameters the {@link
	 * DropColumnParameters} specifying the
	 * columns to be dropped
	 * @throws ColumnNotFoundException
	 */
	public int dropTableColumn(final DropColumnParameters
			parameters) throws ColumnNotFoundException {
		for (final String col : parameters.getColumnList()) {
			if (!tableColumns.containsKey(col.toUpperCase())) {
				throw new ColumnNotFoundException(col);
			}
		}
		for (final String col : parameters.getColumnList()) {
			tableColumns.remove(col.toUpperCase());
			for (int i = 0 ; i < tableColumnNames.size() ; i++) {
				if (tableColumnNames.get(i).equalsIgnoreCase(col)) {
					tableColumnNames.remove(i);
					break;
				}
			}
		}
		return 0;
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
	 * @throws InvalidDateFormatException
	 */
	public int insertRows(final InsertionParameters insertParameters)
			throws RepeatedColumnException,
			ColumnListTooLargeException,
			ColumnNotFoundException,
			ValueListTooLargeException, ValueListTooSmallException,
			TypeMismatchException, InvalidDateFormatException {
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
		return insertParameters.getValues().size();
	}
	/**
	 * Inserts a row.
	 * @param values the values in the row
	 * @throws ValueListTooLargeException
	 * @throws ValueListTooSmallException
	 * @throws TypeMismatchException
	 * @throws InvalidDateFormatException
	 */
	private void insertRow(final ArrayList<String> values)
			throws ValueListTooLargeException, ValueListTooSmallException,
			TypeMismatchException, InvalidDateFormatException {
		final DataTypesValidator dataTypesValidator
		= new DataTypesValidator();
		int index = 0;
		for (final String column : tableColumnNames) {
			if (values.get(index).equals(Constants.NULL_INDICATOR)) {
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
	 * @throws InvalidDateFormatException
	 */
	private void insertRow(final ArrayList<String> columnNames,
			final ArrayList<String> values, final Set<String> nullCells)
					throws RepeatedColumnException, ColumnListTooLargeException,
					ColumnNotFoundException, ValueListTooLargeException,
					TypeMismatchException, InvalidDateFormatException {
		final DataTypesValidator dataTypesValidator
		= new DataTypesValidator();
		for (int i = 0; i < columnNames.size(); i++) {
			if (values.get(i).equals(Constants.NULL_INDICATOR)) {
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
	 * @throws InvalidDateFormatException
	 */
	public SelectQueryOutput selectFromTable(final SelectionParameters
			selectParameters) throws ColumnNotFoundException,
	TypeMismatchException, InvalidDateFormatException {
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
		final ArrayList<ColumnIdentifier> columnIdentifiers
		= new ArrayList<>();
		for (final String col : columnNames) {
			columnIdentifiers.add(new ColumnIdentifier(
					col, tableColumns.get(
							col.toUpperCase()
							).getColumnDataType()));
		}
		final ArrayList<ArrayList<SQLType<?>>> tableRows = getTableRows(matches);
		final ArrayList<ColumnOrder> order
		= selectParameters.getColumnsOrder();
		final HashMap<String, Integer> indices = getColumnIndices();
		if (order != null) {
			for (final ColumnOrder columnOrder : order) {
				if (!tableColumns.containsKey(
						columnOrder.getColumnName(
								).toUpperCase())) {
					throw new ColumnNotFoundException(
							columnOrder.getColumnName());
				}
			}
			final ComparatorChain<ArrayList<SQLType<?>>> comparatorChain
			= new ComparatorChain<>();
			addComparators(order, comparatorChain, indices);
			tableRows.sort(comparatorChain);
		}
		final ArrayList<ArrayList<String>> rows = new ArrayList<>();
		for (int i = 0 ; i < tableRows.size() ; i++) {
			final ArrayList<String> row = new ArrayList<>();
			for (int j = 0 ; j < columnNames.size() ; j++) {
				row.add(tableRows.get(i).get(
						indices.get(columnNames.
								get(j).toUpperCase())).
						getStringValue());
			}
			rows.add(row);
		}
		output.setDistinct(selectParameters.isDistinct());
		output.setColumns(columnIdentifiers);
		output.setRows(rows);
		output.setTableName(tableName);
		return output;
	}
	/**
	 * Performs the sql update statement.
	 * @param updateParameters the
	 * update statement parameters
	 * @throws ColumnNotFoundException
	 * @throws TypeMismatchException
	 * @throws InvalidDateFormatException
	 */
	public int updateTable(final UpdatingParameters updateParameters)
			throws ColumnNotFoundException, TypeMismatchException,
			InvalidDateFormatException {
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
		return matches.size();
	}
	/**
	 * Gets all the rows matching
	 * a boolean expression.
	 * @param condition the boolean expression
	 * @return a list of the matching rows
	 * @throws ColumnNotFoundException
	 * @throws TypeMismatchException
	 * @throws InvalidDateFormatException
	 */
	private ArrayList<Integer> getAllMatches(
			final BooleanExpression condition)
					throws ColumnNotFoundException,
					TypeMismatchException, InvalidDateFormatException {
		ArrayList<Integer> matches = new ArrayList<>();
		final DataTypesValidator validator
		= new DataTypesValidator();
		if(condition.leftOperandIsConstant()
				&& condition.rightOperandIsConstant()) {
			if (!validator.assertDataTypesEquals(condition.getLeftOperand(),
					condition.getRightOperand())) {
				throw new TypeMismatchException();
			}
			if (evaluateExpression(condition,
					condition.getLeftOperand(),
					condition.getRightOperand())) {
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
	 * @throws InvalidDateFormatException
	 */
	private void AssignColumn(final AssignmentExpression assignment,
			final ArrayList<Integer> matches) throws ColumnNotFoundException,
	TypeMismatchException, InvalidDateFormatException {
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
	 * @throws InvalidDateFormatException
	 */
	public int deleteRows(final BooleanExpression condition)
			throws ColumnNotFoundException, TypeMismatchException,
			InvalidDateFormatException {
		int numberOfDeletions = 0;
		if (condition == null) {
			numberOfDeletions = numberOfRows;
			clearTable();
			return numberOfDeletions;
		}
		ArrayList<Integer> matches
		= new ArrayList<>();
		matches = getAllMatches(condition);
		for (final int match : matches) {
			deleteRow(match - numberOfDeletions);
			numberOfDeletions++;
		}
		return numberOfDeletions;
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
	 * @throws InvalidDateFormatException
	 */
	private ArrayList<Integer> getAllMatches(final BooleanExpression condition,
			final TableColumn conditionColumn, final String other,
			final boolean leftIsTableColumn) throws InvalidDateFormatException {
		final ArrayList<Integer> matches = new ArrayList<>();
		for (int i = 0; i < numberOfRows; i++) {
			final String leftCellValue
			= conditionColumn.get(i).getStringValue();
			if (evaluateExpression(condition,
					leftCellValue, other)) {
				matches.add(i);
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
	 * @throws InvalidDateFormatException
	 */
	private ArrayList<Integer> getAllMatches(
			final BooleanExpression condition,
			final TableColumn conditionColumn,
			final TableColumn other)
					throws TypeMismatchException,
					InvalidDateFormatException {
		final DataTypesValidator validator
		= new DataTypesValidator();
		if (!validator.checkDataTypes(
				conditionColumn.getColumnDataType(),
				other.getColumnDataType())) {
			throw new TypeMismatchException();
		}
		final ArrayList<Integer> matches
		= new ArrayList<>();
		for (int i = 0; i < numberOfRows; i++) {
			final String leftCellValue
			= conditionColumn.get(i).getStringValue();
			final String rightCellValue
			= other.get(i).getStringValue();
			if (evaluateExpression(condition,
					leftCellValue, rightCellValue)) {
				matches.add(i);
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
	private boolean evaluateExpression(final BooleanExpression condition,
			final String leftValue, final String rightValue)
					throws InvalidDateFormatException {
		final DataTypesValidator validator
		= new DataTypesValidator();
		if (Constants.STRING_TYPES.contains(
				validator.getDataType(leftValue)) &&
				Constants.STRING_TYPES.contains(
						validator.getDataType(rightValue))) {
			return condition.evaluate(
					new VarcharSQLType(leftValue),
					new VarcharSQLType(rightValue));
		} else if (Constants.BIG_INTEGER_TYPES.contains(
				validator.getDataType(leftValue)) &&
				Constants.BIG_INTEGER_TYPES.contains(
						validator.getDataType(rightValue))) {
			return condition.evaluate(
					new BigIntSQLType(leftValue),
					new BigIntSQLType(rightValue));
		} else if (Constants.DOUBLE_TYPES.contains(
				validator.getDataType(leftValue)) &&
				Constants.DOUBLE_TYPES.contains(
						validator.getDataType(rightValue))) {
			return condition.evaluate(
					new DoubleSQLType(leftValue),
					new DoubleSQLType(rightValue));
		} else if (Constants.INTEGER_TYPES.contains(
				validator.getDataType(leftValue)) &&
				Constants.INTEGER_TYPES.contains(
						validator.getDataType(rightValue))) {
			return condition.evaluate(
					new BigIntSQLType(leftValue),
					new BigIntSQLType(rightValue));
		} else if (Constants.FLOAT_TYPES.contains(
				validator.getDataType(leftValue)) &&
				Constants.FLOAT_TYPES.contains(
						validator.getDataType(rightValue))) {
			return condition.evaluate(
					new DoubleSQLType(leftValue),
					new DoubleSQLType(rightValue));
		} else if (Constants.DATE_TYPES.contains(
				validator.getDataType(leftValue)) &&
				Constants.DATE_TYPES.contains(
						validator.getDataType(rightValue))) {
			return condition.evaluate(
					new DateSQLType(leftValue),
					new DateSQLType(rightValue));
		} else if (Constants.DATE_TIME_TYPES.contains(
				validator.getDataType(leftValue)) &&
				Constants.DATE_TIME_TYPES.contains(
						validator.getDataType(rightValue))) {
			return condition.evaluate(
					new DateTimeSQLType(leftValue),
					new DateTimeSQLType(rightValue));
		}
		return false;
	}
	private void addComparators(final ArrayList<ColumnOrder>
	order,
	final ComparatorChain<ArrayList<SQLType<?>>>
	comparatorChain, final HashMap<String, Integer> columnIndices) {
		for (final ColumnOrder columnOrder : order) {
			comparatorChain.addComparator(getComparator(columnOrder,
					columnIndices),
					!columnOrder.isAscending());
		}
	}
	private Comparator<ArrayList<SQLType<?>>>
	getComparator(final ColumnOrder order,
			final HashMap<String, Integer> columnIndices) {
		final String type = tableColumns.get(
				order.getColumnName().
				toUpperCase()).getColumnDataType();
		final int index = columnIndices.get(order.
				getColumnName().toUpperCase());
		if (Constants.STRING_TYPES.contains(type)) {
			return new SQLComparator<String, VarcharSQLType>(index);
		} else if (Constants.BIG_INTEGER_TYPES.contains(type)) {
			return new SQLComparator<Long, BigIntSQLType>(index);
		} else if (Constants.DOUBLE_TYPES.contains(type)) {
			return new SQLComparator<Double, DoubleSQLType>(index);
		} else if (Constants.INTEGER_TYPES.contains(type)) {
			return new SQLComparator<Integer, IntSQLType>(index);
		} else if (Constants.FLOAT_TYPES.contains(type)) {
			return new SQLComparator<Float, FloatSQLType>(index);
		} else if (Constants.DATE_TYPES.contains(type)) {
			return new SQLComparator<Date, DateSQLType>(index);
		} else if (Constants.DATE_TIME_TYPES.contains(type)) {
			return new SQLComparator<Date, DateTimeSQLType>(index);
		}
		return null;
	}
	private ArrayList<ArrayList<SQLType<?>>> getTableRows(final ArrayList<Integer> matches) {
		final ArrayList<ArrayList<SQLType<?>>> rows
		= new ArrayList<>();
		for (final int match : matches) {
			final ArrayList<SQLType<?>> row = new ArrayList<>();
			for (final String column : tableColumnNames) {
				row.add(tableColumns.get(
						column.toUpperCase()).get(match));
			}
			rows.add(row);
		}
		return rows;
	}
	private HashMap<String, Integer> getColumnIndices() {
		final HashMap<String, Integer> columnIndices
		= new HashMap<>();
		int index = 0;
		for (final String column : tableColumnNames) {
			columnIndices.put(column.toUpperCase(), index);
			index++;
		}
		return columnIndices;
	}
}
