package jdbms.sql.datatypes;

import jdbms.sql.datatypes.util.SQLTypeFactory;

public class FloatSQLType extends SQLType<Double> {
	static {
		SQLTypeFactory.getInstance().
		registerType("FLOAT", FloatSQLType.class);
		SQLTypeFactory.getInstance().
		registerType("REAL", FloatSQLType.class);
	}
	public FloatSQLType(final String value) {
		super(value == null ? null
				: Double.parseDouble(value));
	}

	@Override
	public String getType() {
		return "FLOAT";
	}
	@Override
	public String getStringValue() {
		if (value == null) {
			return "";
		}
		return value.toString();
	}
}
