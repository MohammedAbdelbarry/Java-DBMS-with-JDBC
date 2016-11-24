package jdbms.sql.datatypes;

import jdbms.sql.datatypes.util.SQLTypeFactory;

public class VarcharSQLType extends SQLType<String>{
	static {
		SQLTypeFactory.getInstance().registerType("VARCHAR", VarcharSQLType.class);
		SQLTypeFactory.getInstance().registerType("TEXT", VarcharSQLType.class);
	}
	public VarcharSQLType(String value) {
		super(value);
	}

	@Override
	public String getType() {
		return "VARCHAR";
	}

}
