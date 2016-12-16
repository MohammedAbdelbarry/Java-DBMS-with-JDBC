package jdbms.sql.datatypes;
import jdbms.sql.datatypes.util.SQLTypeFactory;
/**
 * A java object representing the
 * SQL Varchar type.
 * @author Mohammed Abdelbarry
 */
public class VarcharSQLType extends SQLType<String>{
	static {
		SQLTypeFactory.getInstance().registerType("VARCHAR", VarcharSQLType.class);
		SQLTypeFactory.getInstance().registerType("TEXT", VarcharSQLType.class);
	}
	public VarcharSQLType(final String value) {
		super(value);
	}

	@Override
	public String getType() {
		return "VARCHAR";
	}
	@Override
	public int compareTo(final SQLType<String> other) {
		if (value == null) {
			if (other == null) {
				return 0;
			} else {
				return -1;
			}
		}
		if (other == null) {
			return 1;
		}
		return removeQuotes(value).compareTo(
				removeQuotes(other.getValue()));
	}
	/**
	 * removes the quotes from a string
	 * value.
	 * @param s the string
	 * @return the quote-less string
	 */
	private String removeQuotes(final String s) {
		return s.substring(1, s.length() - 1);
	}
}
