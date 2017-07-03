package jdbms.sql.datatypes;

/**
 * Generic class representing a SQL
 * data type.
 * @param <T> The java counterpart of the SQL data type.
 * @author Mohammed Abdelbarry
 */
public abstract class SQLType<T extends Comparable<T>>
        implements Comparable<SQLType<T>> {
    /**
     * The value of the SQL object.
     */
    protected T value;

    /**
     * Constructs a new SQL Object with
     * the given value.
     * @param value The java value of the SQL type
     */
    public SQLType(final T value) {
        this.value = value;
    }

    /**
     * Gets the java value of the
     * SQL object
     * @return the java value of the SQL object
     */
    public T getValue() {
        return value;
    }

    /**
     * Gets the value of the SQL object
     * represented as a string.
     * @return The string value of the SQL object, if the value is null then an
     * empty string is returned.
     */
    @Override
    public String toString() {
        if (value == null) {
            return "";
        }
        return value.toString();
    }

    /**
     * Gets the SQL data type of the
     * object.
     * @return The SQL data type of the object
     */
    public abstract String getType();

    /**
     * Compares a SQLType to another SQLType.
     * Returns a negative integer, zero,
     * or a positive integer as this
     * object is less than, equal to,
     * or greater than the specified
     * object.
     * @return A negative integer, zero, or a positive integer as this object is
     * less than, equal to, or greater than the specified object.
     */
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
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SQLType<?> other = (SQLType<?>) obj;
        if (value == null) {
            if (other.value != null) {
                return false;
            }
        } else if (!value.equals(other.value)) {
            return false;
        }
        return true;
    }
}
