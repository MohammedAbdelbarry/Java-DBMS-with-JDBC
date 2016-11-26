package jdbms.sql.data;

import java.util.ArrayList;

import jdbms.sql.datatypes.SQLType;
import jdbms.sql.datatypes.util.SQLTypeFactory;

public class TableColumn {

	/**column name.*/
	private String columnName;
	/**values of the column.*/
	private ArrayList<SQLType<?>> values;
	/** The column data type. **/
	private String columnType;

	public TableColumn(String columnName, String columnType) {
		this.columnName = columnName;
		this.columnType = columnType;
		values = new ArrayList<>();
	}
	public TableColumn(ColumnIdentifier columnIdentifier) {
		this.columnName = columnIdentifier.getName();
		this.columnType = columnIdentifier.getType();
		values = new ArrayList<>();
	}
	public void add(String value) {
		values.add(SQLTypeFactory.getInstance().
				getTypeObject(columnType, value));
	}

	public void addAll(ArrayList<SQLType<?>> values) {
		this.values.addAll(values);
	}

	public void assignCell(int cell, String value) {
		values.set(cell, SQLTypeFactory.getInstance().
				getTypeObject(columnType, value));
	}

	public ArrayList<String> getValues() {
		ArrayList<String> requestedValues = new ArrayList<>();
		for (SQLType<?> cur : values) {
			requestedValues.add(cur.getStringValue());
		}
		return requestedValues;
	}

	public SQLType<?> get(int index) {
		return values.get(index);
	}

	public void remove(int index) {
		values.remove(index);
	}

	public String getColumnName() {
		return columnName;
	}

	public String getColumnDataType() {
		return columnType;
	}

	public int getSize() {
		return values.size();
	}

	public void clearColumn() {
		values.clear();
	}
}
