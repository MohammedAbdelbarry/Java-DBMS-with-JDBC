package jdbms.sql.data;

import java.util.ArrayList;

import jbdms.sql.datatypes.SQLType;

public class TableColumn<T extends SQLType<?>> {

	/**column name.*/
	private String columnName;
	/**values of the column.*/
	private ArrayList<T> values;

	public TableColumn(String columnName) {
		this.columnName = columnName;
		values = new ArrayList<T>();
	}

	public void add(T value) {
		values.add(value);    
	}

	public void add(int index, T value) {
		values.add(index, value);
	}

	public void addAll(ArrayList<T> values) {
		this.values.addAll(values);
	}

	public T get(int index) {
		return values.get(index);
	}

	public void remove(int index) {
		values.remove(index);
	}
	public String getColumnName() {
		return columnName;
	}
}
