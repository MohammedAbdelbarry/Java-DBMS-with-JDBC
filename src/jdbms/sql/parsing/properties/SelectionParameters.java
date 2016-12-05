package jdbms.sql.parsing.properties;

import java.util.ArrayList;

import jdbms.sql.parsing.expressions.math.BooleanExpression;

public class SelectionParameters {
	private ArrayList<String> columns;
	private String tableName;
	private BooleanExpression condition;
	private boolean distinct;

	public SelectionParameters() {
		this.distinct = false;
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
		this.distinct = distinct;
	}

	public boolean isDistinct() {
		return this.distinct;
	}
}
