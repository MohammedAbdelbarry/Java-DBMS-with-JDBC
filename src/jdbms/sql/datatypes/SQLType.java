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
		if (value == null) {
			return "''";
		}
		return value.toString();
	}
	public abstract String getType();
	@Override
	public int compareTo(SQLType<T> other) {
		if (value == null) {
			if (other == null) {
				return 0;
			} else {
				return -1;
			}
		}
		return value.compareTo(other.getValue());
	}

}
