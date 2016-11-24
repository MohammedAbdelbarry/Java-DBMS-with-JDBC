package jbdms.sql.datatypes;

import jdbms.sql.datatypes.util.SQLTypeFactory;

public class IntSQLType extends SQLType<Integer> {
	static {
		SQLTypeFactory.getInstance().registerType("INTEGER", IntSQLType.class);
	}
	public IntSQLType(String value) {
		super(value == null ? null : Integer.parseInt(value));
	}

	@Override
	public String getType() {
		return "INTEGER";
	}

}
