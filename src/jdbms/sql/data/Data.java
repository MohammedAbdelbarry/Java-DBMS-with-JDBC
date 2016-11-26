package jdbms.sql.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jdbms.sql.exceptions.ColumnAlreadyExistsException;
import jdbms.sql.exceptions.ColumnListTooLargeException;
import jdbms.sql.exceptions.ColumnNotFoundException;
import jdbms.sql.exceptions.RepeatedColumnException;
import jdbms.sql.exceptions.TableAlreadyExistsException;
import jdbms.sql.parsing.properties.DatabaseCreationParameters;
import jdbms.sql.parsing.properties.DatabaseDroppingParameters;
import jdbms.sql.parsing.properties.InsertionParameters;
import jdbms.sql.parsing.properties.SelectionParameters;
import jdbms.sql.parsing.properties.TableCreationParameters;
import jdbms.sql.parsing.properties.TableDroppingParameters;
import jdbms.sql.parsing.properties.UseParameters;


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
	 * Sets the given {@link Database} as active.
	 * @param useParameters The parameters of the sql use
	 * statement
	 */
	public void setActiveDatabase(UseParameters useParameters) {
		activeDatabase = data.get(useParameters.getDatabaseName());
	}

	/**
	 * Creates a new blank database with the name provided and returns it.
	 * @param createDBParameters The parameters of the sql
	 * create statement
	 * @return the new empty database
	 */
	public Database createDatabase(DatabaseCreationParameters
			createDBParameters) {
		Database newDatabase
		= new Database(createDBParameters.getDatabaseName());
		data.put(createDBParameters.getDatabaseName(), newDatabase);
		activeDatabase = newDatabase;
		return newDatabase;
	}

	/**
	 * Drops the database with the provided name.
	 */
	public void dropDatabase(DatabaseDroppingParameters dropDBParameters) {
		data.remove(dropDBParameters.getDatabaseName());
	}

	/**
	 * Returns the values selected.
	 * @param tableName name of the table to be processed
	 * @param columns array list of the columns to be selected from the specified table
	 * @return Array list of the values of the selected columns
	 */
	public ArrayList<ArrayList<String>> selectFrom(SelectionParameters
			selectParameters) {
		Table curTable = activeDatabase.getTable(selectParameters.getTableName());
//		ArrayList<TableColumn> cols
//		= curTable.getColumnList(selectParameters.getColumns());
//		ArrayList<ArrayList<String>> values = new ArrayList<>();
//		for (TableColumn column : cols) {
//			values.add(column.getValues());
//		}
//		return values;
		return null;
	}

	public void createTable(TableCreationParameters tableParamters) {
		try {
			activeDatabase.addTable(tableParamters);
		} catch (TableAlreadyExistsException e) {
			// ErrorHandler.printTableAlreadyExistsError()
		} catch (ColumnAlreadyExistsException e) {
			// ErrorHandler.printColumnAlreadyExistsError()
		}
	}

	public void dropTable(TableDroppingParameters tableParameters) {
		activeDatabase.dropTable(tableParameters.getTableName());
	}

	public void insertInto(InsertionParameters parameters)
			throws RepeatedColumnException,
			ColumnListTooLargeException,
			ColumnNotFoundException {
		activeDatabase.insertInto(parameters);
	}
 }
