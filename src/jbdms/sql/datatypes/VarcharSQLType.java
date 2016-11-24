package jbdms.sql.datatypes;

public class VarcharSQLType extends SQLType<String>{

	public VarcharSQLType(String value) {
		super(value);
	}

	@Override
	public String getType() {
		return "VARCHAR";
	}

}
