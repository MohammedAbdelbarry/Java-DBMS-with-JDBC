package jdbms.sql.data;

import java.util.HashMap;
import java.util.Map;

import jdbms.sql.parsing.statements.CreateDatabaseStatement;

public class Data {

	/**array of databases.*/
	private Map<String, Database> data;

	public Data() {
		data = new HashMap<>();
	}

	public void createDatabase(String newDatabaseName) {
		Database newDatabase = new Database(newDatabaseName);
		data.put(newDatabaseName, newDatabase);
	}

	public void dropDatabase(String databaseName) {
		data.remove(databaseName);
	}
}
