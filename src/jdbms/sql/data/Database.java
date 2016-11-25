package jdbms.sql.data;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import jdbms.sql.exceptions.ColumnAlreadyExistsException;
import jdbms.sql.exceptions.TableAlreadyExistsException;

public class Database {

	/**Array of database tables.*/
	private Map<String, Table> tables;
	/**Database name.*/
	private String databaseName;

	public Database(String databaseName) {
		this.databaseName = databaseName;
		tables = new HashMap<>();
		new File(databaseName).mkdir();
	}

	public void addTable(TableIdentifier newTableIdentifier)
			throws TableAlreadyExistsException {
		try {
			tables.put(newTableIdentifier.getTableName(),
					new Table(newTableIdentifier));
		} catch (ColumnAlreadyExistsException e) {
			// ErrorHandler.printTableAlreadyExistsError();
		}
	}

	public Table getTable(String tableName) {
		return tables.get(tableName);
	}

	public void dropTable(String tableName) {
		tables.remove(tableName);
	}

	public String getDatabaseName() {
		return databaseName;
	}
}
