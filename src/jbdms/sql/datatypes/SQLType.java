package jbdms.sql.datatypes;

public abstract class SQLType<T> implements Comparable<SQLType<T>> {
	T value;
	public SQLType(T value) {
		this.value = value;
	}
	public T getValue() {
		return value;
	}
	public abstract String getType();
}
