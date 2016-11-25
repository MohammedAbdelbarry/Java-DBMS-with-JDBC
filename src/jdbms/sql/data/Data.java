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

	/**
	 * Creates a new blank database with the name provided and returns it.
	 * @param newDatabaseName the database name
	 * @return the new blank database
	 */
	public Database createDatabase(String newDatabaseName) {
		Database newDatabase = new Database(newDatabaseName);
		data.put(newDatabaseName, newDatabase);
		return newDatabase;
	}

	/**
	 * Drops the database with the provided name.
	 */
	public void dropDatabase(String databaseName) {
		data.remove(databaseName);
	}
}
