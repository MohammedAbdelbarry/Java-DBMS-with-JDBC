package jdbms.sql.data.query;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import jdbms.sql.data.ColumnIdentifier;
import jdbms.sql.parsing.util.Constants;
/**
 * The output of an SQL select query.
 * @author Mohammed Abdelbarry
 */
public class SelectQueryOutput {
	/**
	 * The names of the selected columns.
	 */
	private ArrayList<ColumnIdentifier> columns;
	/**
	 * A 2D array representing the selected columns
	 * and rows.
	 */
	private ArrayList<ArrayList<String>> outputRows;
	/**
	 * The name of the table that
	 * contains these values.
	 */
	private String tableName;
	/**
	 * A boolean value that represents whether
	 * the selected rows are unique or
	 * not.
	 */
	private boolean isDistinct = false;
	private static final String NULL_PLACEHOLDER = "";
	private static final String NULL_VALUE = "";
	/**
	 * Constructs a new
	 * {@link SelectQueryOutput}.
	 */
	public SelectQueryOutput() {
		outputRows = new ArrayList<>();
		columns = new ArrayList<>();
	}
	/**
	 * Sets the names of the selected
	 * columns.
	 * @param columns The names of the selected
	 * columns.
	 */
	public void setColumns(final ArrayList<ColumnIdentifier> columns) {
		this.columns = columns;
	}
	/**
	 * Sets the distinct state of the
	 * query.
	 * @param distinct The distinct state
	 * of the query
	 */
	public void setDistinct(final boolean distinct) {
		isDistinct = distinct;
	}
	/**
	 * Sets the rows selected by
	 * the query.
	 * @param rows the rows selected
	 * by the query.
	 */
	public void setRows(final ArrayList<ArrayList<String>> rows) {
		outputRows = rows;
		for (final ArrayList<String> row : outputRows) {
			for (int i = 0  ; i < row.size() ; i++) {
				final String cell = row.get(i);
				if (cell.equals(NULL_VALUE)) {
					row.set(i, null);
				}
				if (cell.matches(Constants.DOUBLE_STRING_REGEX) ||
						cell.matches(Constants.STRING_REGEX)) {
					row.set(i, cell.substring(1, cell.length() - 1));
				}
			}
		}
		if (isDistinct) {
			final Set<ArrayList<String>> uniqueRows
			= new LinkedHashSet<>(outputRows);
			outputRows = new ArrayList<>(uniqueRows);
		}
	}
	/**
	 * Prints the output of the query
	 * in a tabular form.
	 */
	public void printOutput() {
		final PrettyPrinter printer
		= new PrettyPrinter(System.out, NULL_PLACEHOLDER);
		printer.print(makeArrays());
	}
	private String[][] makeArrays() {
		final String[][] arr = new String[outputRows.size() + 1][columns.size()];
		for (int i = 0 ; i < columns.size() ; i++) {
			arr[0][i] = columns.get(i).getName();
		}
		int index = 1;
		for (final ArrayList<String> row : outputRows) {
			row.toArray(arr[index]);
			index++;
		}
		return arr;
	}
	/**
	 * Gets the table name.
	 * @return the table name
	 */
	public String getTableName() {
		return tableName;
	}
	/**
	 * Sets the table name.
	 * @param table the table name
	 */
	public void setTableName(final String table) {
		this.tableName = table;
	}
	/**
	 * Gets the columns names.
	 * @return The columns names
	 */
	public ArrayList<ColumnIdentifier> getColumns() {
		return columns;
	}
	/**
	 * Gets the rows in the query.
	 * @return The data returned by the
	 * query
	 */
	public ArrayList<ArrayList<String>> getData() {
		return outputRows;
	}
	/**
	 * Checks whether the query output
	 * is empty or not.
	 * @return True if the select query
	 * output is empty and false
	 * otherwise.
	 */
	public boolean isEmpty() {
		return outputRows.isEmpty();
	}
}
