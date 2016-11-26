package jdbms.sql.data.query;

import java.util.ArrayList;

public class SelectQueryOutput {
	private ArrayList<String> columns;
	private ArrayList<ArrayList<String>> outputRows;
	private static final String NULL_PLACEHOLDER = "";
	public SelectQueryOutput() {
		outputRows = new ArrayList<>();
		columns = new ArrayList<>();
	}
	public void setColumns(ArrayList<String> columns) {
		this.columns = columns;
	}
	public void setRows(ArrayList<ArrayList<String>> rows) {
		outputRows = rows;
	}
	public void printOutput() {
		PrettyPrinter printer = new PrettyPrinter(System.out, NULL_PLACEHOLDER);
		printer.print(makeArrays());
	}
	private String[][] makeArrays() {
		String[][] arr = new String[outputRows.size() + 1][columns.size()];
		columns.toArray(arr[0]);
		int index = 1;
		for (ArrayList<String> row : outputRows) {
			row.toArray(arr[index]);
			index++;
		}
		return arr;
	}
}
