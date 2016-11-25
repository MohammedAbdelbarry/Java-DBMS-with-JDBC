package jdbms.sql.data;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Database {

	/**array of database tables.*/
	private Map<String, Table> tables;
	/**database name.*/
	private String databaseName;

	public Database(String databaseName) {
		this.databaseName = databaseName;
		tables = new HashMap<>();
		new File(databaseName).mkdir();
	}

	public void addTable(TableIdentifier newTableIdentifier) {
		String tableName = newTableIdentifier.getTableName();
		Table newTable = new Table(tableName, this);
		tables.put(tableName, newTable);
	}

	public void dropTable(String tableName) {
		tables.remove(tableName);
	}

	public String getDatabaseName() {
		return databaseName;
	}
}
