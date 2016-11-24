package jdbms.sql.datatypes;

public abstract class SQLType<T extends Comparable<T>>
implements Comparable<SQLType<T>> {
	T value;
	public SQLType(T value) {
		this.value = value;
	}
	public T getValue() {
		return value;
	}
	public String getStringValue() {
		return value.toString();
	}
	public abstract String getType();
	@Override
	public int compareTo(SQLType<T> other) {
		return value.compareTo(other.getValue());
	}
}