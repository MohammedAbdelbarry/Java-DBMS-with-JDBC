package jdbms.sql.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Data {

	/**array of databases.*/
	private Map<String, Database> data;
	/**Currently Active Database.*/
	private Database activeDatabase;

	public Data() {
		data = new HashMap<>();
		activeDatabase = null;
	}

	/**
	 * Sets the given Database as active.
	 * @param databaseName name of the database to be set active
	 */
	public void setActiveDatabase(String databaseName) {
		activeDatabase = data.get(databaseName);
	}

	/**
	 * Creates a new blank database with the name provided and returns it.
	 * @param newDatabaseName the database name
	 * @return the new blank database
	 */
	public Database createDatabase(String newDatabaseName) {
		Database newDatabase = new Database(newDatabaseName);
		data.put(newDatabaseName, newDatabase);
		activeDatabase = newDatabase;
		return newDatabase;
	}

	/**
	 * Drops the database with the provided name.
	 */
	public void dropDatabase(String databaseName) {
		data.remove(databaseName);
	}

	/**
	 * Returns the values selected.
	 * @param tableName name of the table to be processed
	 * @param columns array list of the columns to be selected from the specified table
	 * @return Array list of the values of the selected columns
	 */
	public ArrayList<ArrayList<String>> selectFrom(String tableName, ArrayList<String> columns) {
		Table curTable = activeDatabase.getTable(tableName);
		ArrayList<TableColumn> cols = curTable.getColumnList(columns);
		ArrayList<ArrayList<String>> values = new ArrayList<>();
		for (TableColumn column : cols) {
			values.add(column.getValues());
		}
		return values;
	}


	
}
