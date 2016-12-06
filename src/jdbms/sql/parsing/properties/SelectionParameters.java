package jdbms.sql.parsing.properties;

import java.util.ArrayList;

import jdbms.sql.parsing.expressions.math.BooleanExpression;

public class SelectionParameters {
	private ArrayList<String> columns;
	private String tableName;
	private BooleanExpression condition;
	private boolean isDistinct;
	private Boolean isAscending;
	private String sortingColumnName;

	public SelectionParameters() {
		this.isDistinct = false;
		this.isAscending = null;
		this.sortingColumnName = new String();
	}

	public ArrayList<String> getColumns() {
		return columns;
	}

	public void setColumns(ArrayList<String> columns) {
		this.columns = columns;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String table) {
		this.tableName = table;
	}

	public BooleanExpression getCondition() {
		return condition;
	}

	public void setCondition(BooleanExpression condition) {
		this.condition = condition;
	}

	public void setDistinct(boolean distinct) {
		this.isDistinct = distinct;
	}

	public boolean isDistinct() {
		return this.isDistinct;
	}

	public void setAscending(boolean ascending) {
		this.isAscending = ascending;
	}

	public boolean isAscending() {
		return this.isAscending;
	}

	public void setSortingColumnName(String columnName) {
		this.sortingColumnName = columnName;
	}

	public String getSortingColumnName() {
		return this.sortingColumnName;
	}
}
