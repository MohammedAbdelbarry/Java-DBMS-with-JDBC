package jdbms.sql.data;

import java.util.ArrayList;

import jdbms.sql.datatypes.SQLType;
import jdbms.sql.datatypes.util.DataTypesValidator;
import jdbms.sql.datatypes.util.SQLTypeFactory;
import jdbms.sql.exceptions.InvalidDateFormatException;
import jdbms.sql.exceptions.TypeMismatchException;
/** The class representing a sql column. **/
public class TableColumn {

	/**column name.*/
	private final String columnName;
	/**values of the column.*/
	private final ArrayList<SQLType<?>> values;
	/** The column data type. **/
	private final String columnType;
	/**
	 * Constructs a column given its name
	 * and data type.
	 * @param columnName the name of the column
	 * @param columnType the data type of the
	 * column
	 */
	public TableColumn(final String columnName,
			final String columnType) {
		this.columnName = columnName;
		this.columnType = columnType;
		values = new ArrayList<>();
	}
	/**
	 * Constructs a table column given
	 * its column identifier.
	 * @param columnIdentifier The {@link ColumnIdentifier}
	 * representing the column
	 */
	public TableColumn(final ColumnIdentifier
			columnIdentifier) {
		this.columnName = columnIdentifier.getName();
		this.columnType = columnIdentifier.getType();
		values = new ArrayList<>();
	}
	/**
	 * Appends a value to the
	 * end of the column.
	 * @param value the value to be
	 * added
	 * @throws InvalidDateFormatException
	 */
	public void add(final String value)
			throws InvalidDateFormatException {
		values.add(SQLTypeFactory.getInstance().
				getTypeObject(columnType, value));
	}
	/**
	 * Adds a list of SQLType values to
	 * the column.
	 * @param values the list of {@link SQLType} values
	 * to be added
	 */
	public void addAll(final ArrayList<SQLType<?>> values) {
		this.values.addAll(values);
	}
	/**
	 * Assigns a value to a specific cell
	 * in the column.
	 * @param cell the index of the
	 * cell
	 * @param value the value to be
	 * assigned.
	 * @throws TypeMismatchException
	 * @throws InvalidDateFormatException
	 */
	public void assignCell(final int cell, final String value)
			throws TypeMismatchException, InvalidDateFormatException {
		final DataTypesValidator dataTypesValidator =
				new DataTypesValidator();
		if (!dataTypesValidator.match(columnType, value)) {
			throw new TypeMismatchException();
		}
		values.set(cell, SQLTypeFactory.getInstance().
				getTypeObject(columnType, value));
	}
	/**
	 * Gets the values in the table column.
	 * @return returns an arraylist of
	 * all the values in the column
	 */
	public ArrayList<String> getValues() {
		final ArrayList<String> requestedValues = new ArrayList<>();
		for (final SQLType<?> cur : values) {
			requestedValues.add(cur.getStringValue());
		}
		return requestedValues;
	}
	/**
	 * Gets the value of a specific
	 * cell in the column.
	 * @param index the index of
	 * the cell
	 * @return the cell
	 */
	public SQLType<?> get(final int index) {
		return values.get(index);
	}
	/**
	 * Removes a cell from the
	 * column.
	 * @param index the index of the
	 * cell to be removed
	 */
	public void remove(final int index) {
		values.remove(index);
	}
	/**
	 * Gets the name of the column.
	 * @return the name of the column
	 */
	public String getColumnName() {
		return columnName;
	}
	/**
	 * Gets the data type of the
	 * column.
	 * @return the data type of the
	 * column
	 */
	public String getColumnDataType() {
		return columnType;
	}
	/**
	 * Gets the number of cells
	 * in the column.
	 * @return the size of the column
	 */
	public int getSize() {
		return values.size();
	}
	/**
	 * Clears the column.
	 */
	public void clearColumn() {
		values.clear();
	}
}
