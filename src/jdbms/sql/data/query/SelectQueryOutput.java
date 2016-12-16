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
	private ArrayList<ColumnIdentifier> columns;
	private ArrayList<ArrayList<String>> outputRows;
	private String tableName;
	private boolean isDistinct = false;
	private static final String NULL_PLACEHOLDER = "";
	private static final String NULL_VALUE = "";
	public SelectQueryOutput() {
		outputRows = new ArrayList<>();
		columns = new ArrayList<>();
	}
	public void setColumns(final ArrayList<ColumnIdentifier> columns) {
		this.columns = columns;
	}
	public void setDistinct(final boolean distinct) {
		isDistinct = distinct;
	}
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
	public String getTableName() {
		return tableName;
	}
	public void setTableName(final String table) {
		this.tableName = table;
	}
	public ArrayList<ColumnIdentifier> getColumns() {
		return columns;
	}
	public ArrayList<ArrayList<String>> getData() {
		return outputRows;
	}
	public boolean isEmpty() {
		return outputRows.isEmpty();
	}
}
