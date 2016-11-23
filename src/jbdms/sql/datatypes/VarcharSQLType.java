package jbdms.sql.datatypes;

public class VarcharSQLType extends SQLType<String>{
	
	public VarcharSQLType(String value) {
		super(value);
	}
	@Override
	public int compareTo(SQLType<String> other) {
		return value.compareTo(other.getValue());
	}
	@Override
	public String getType() {
		return "VARCHAR";
	}

}
