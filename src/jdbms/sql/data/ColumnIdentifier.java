package jdbms.sql.data;

import java.sql.Types;

import jdbms.sql.parsing.util.Constants;

public class ColumnIdentifier {
	private final String name;
	private final String type;
	public String getName() {
		return name;
	}
	public String getType() {
		return type;
	}
	public int getTypeNumber() {
		if (Constants.STRING_TYPES.contains(type)) {
			return Types.VARCHAR;
		} else if (Constants.INTEGER_TYPES.contains(type)) {
			return Types.INTEGER;
		} else if (Constants.FLOAT_TYPES.contains(type)) {
			return Types.FLOAT;
		} else if (Constants.DATE_TYPES.contains(type)) {
			return Types.DATE;
		} else if (Constants.DATE_TIME_TYPES.contains(type)) {
			return Types.TIMESTAMP;
		} else if (Constants.BIG_INTEGER_TYPES.contains(type)) {
			return Types.BIGINT;
		} else if (Constants.DOUBLE_TYPES.contains(type)) {
			return Types.DOUBLE;
		} else {
			return Types.OTHER;
		}
	}
	public ColumnIdentifier(final String name, final String type) {
		this.name = name;
		this.type = type;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		final ColumnIdentifier other = (ColumnIdentifier) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
}
