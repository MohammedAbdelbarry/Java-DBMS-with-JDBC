package jdbms.sql.datatypes;

public abstract class SQLType<T extends Comparable<T>>
implements Comparable<SQLType<T>> {
	protected T value;
	public SQLType(final T value) {
		this.value = value;
	}
	public T getValue() {
		return value;
	}
	public String getStringValue() {
		if (value == null) {
			return "";
		}
		return value.toString();
	}
	public abstract String getType();
	@Override
	public int compareTo(final SQLType<T> other) {
		if (value == null) {
			if (other.value == null) {
				return 0;
			} else {
				return -1;
			}
		}
		if (other.value == null) {
			return 1;
		}
		return value.compareTo(other.getValue());
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}
	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final SQLType<?> other = (SQLType<?>) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
}
