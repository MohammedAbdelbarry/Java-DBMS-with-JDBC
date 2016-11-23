package jdbms.sql.data;

import java.util.HashMap;
import java.util.Map;

import jbdms.sql.datatypes.VarcharSQLType;

public class Table {

	private String tableName;
	private Map<String, TableColumn> columns;
	private int numberOfRows;

	public Table(String tableName) {
		this.tableName = tableName;
		columns = new HashMap<>();
		numberOfRows = 0;
	}

	public void addTableColumn(String columnName, String columnDataType) {
		
	}
	public void insertRow() {
		
	}
	
}
