package jdbms.sql.datatypes;

import jdbms.sql.datatypes.util.SQLTypeFactory;
/**
 * A java object representing the
 * SQL Double type.
 * @author Mohammed Abdelbarry
 */
public class DoubleSQLType extends SQLType<Double> {
	static {
		SQLTypeFactory.getInstance().
		registerType("DOUBLE", DoubleSQLType.class);
		SQLTypeFactory.getInstance().
		registerType("REAL", DoubleSQLType.class);
	}
	public DoubleSQLType(final String value) {
		super(value == null ? null
				: Double.parseDouble(value));
	}

	@Override
	public String getType() {
		return "DOUBLE";
	}

}
