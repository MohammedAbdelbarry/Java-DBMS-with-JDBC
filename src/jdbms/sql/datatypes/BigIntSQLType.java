package jdbms.sql.datatypes;

import jdbms.sql.datatypes.util.SQLTypeFactory;

public class BigIntSQLType extends SQLType<Long> {
	static {
		SQLTypeFactory.getInstance().
		registerType("BIGINT", BigIntSQLType.class);
	}
	public BigIntSQLType(final String value) {
		super(value == null ? null
				: Long.parseLong(value));
	}
	@Override
	public String getType() {
		return "BIGINT";
	}
}
