package jbdms.sql.datatypes;

public class IntSQLType extends SQLType<Integer> {

	public IntSQLType(String value) {
		super(value == null ? null : Integer.parseInt(value));
	}

	@Override
	public String getType() {
		return "INTEGER";
	}

}
