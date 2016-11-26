package jdbms.sql.data.query;

import java.util.ArrayList;

public class SelectQueryOutput {
	private ArrayList<String> columns;
	private ArrayList<ArrayList<String>> outputRows;
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
}
