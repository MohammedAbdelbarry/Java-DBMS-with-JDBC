package jdbms.sql.parsing.properties;

import java.util.ArrayList;

import jdbms.sql.data.ColumnIdentifier;
import jdbms.sql.parsing.expressions.math.AssignmentExpression;
import jdbms.sql.parsing.expressions.math.BooleanExpression;

public class InputParametersContainer {
	private String databaseName;
	private BooleanExpression condition;
	private String tableName;
	private ArrayList<String> columns;
	private ArrayList<ArrayList<String>> values;
	private ArrayList<ColumnIdentifier> columnDefinitions;
	private ArrayList<AssignmentExpression> assignmentList;
	public InputParametersContainer() {

	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public BooleanExpression getCondition() {
		return condition;
	}

	public void setCondition(BooleanExpression condition) {
		this.condition = condition;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public ArrayList<String> getColumns() {
		return columns;
	}

	public void setColumns(ArrayList<String> columns) {
		this.columns = columns;
	}

	public ArrayList<ArrayList<String>> getValues() {
		return values;
	}

	public void setValues(ArrayList<String[]> values) {
		this.values = new ArrayList<>();
		for (String[] row : values) {
			ArrayList<String> temp = new ArrayList<>();
			for (String cell : row) {
				temp.add(cell);
			}
			this.values.add(temp);
		}
	}

	public ArrayList<ColumnIdentifier> getColumnDefinitions() {
		return columnDefinitions;
	}
	public void setColumnDefinitions(ArrayList<ColumnIdentifier> columnDefinitions) {
		this.columnDefinitions = columnDefinitions;
	}

	public ArrayList<AssignmentExpression> getAssignmentList() {
		return assignmentList;
	}

	public void setAssignmentList(ArrayList<AssignmentExpression> assignmentList) {
		this.assignmentList = assignmentList;
	}

}
