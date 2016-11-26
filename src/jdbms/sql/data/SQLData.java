package jdbms.sql.data;

import java.util.HashMap;
import java.util.Map;

import jdbms.sql.data.query.SelectQueryOutput;
import jdbms.sql.exceptions.ColumnAlreadyExistsException;
import jdbms.sql.exceptions.ColumnListTooLargeException;
import jdbms.sql.exceptions.ColumnNotFoundException;
import jdbms.sql.exceptions.RepeatedColumnException;
import jdbms.sql.exceptions.TableAlreadyExistsException;
import jdbms.sql.exceptions.TableNotFoundException;
import jdbms.sql.exceptions.TypeMismatchException;
import jdbms.sql.exceptions.ValueListTooLargeException;
import jdbms.sql.exceptions.ValueListTooSmallException;
import jdbms.sql.parsing.properties.DatabaseCreationParameters;
import jdbms.sql.parsing.properties.DatabaseDroppingParameters;
import jdbms.sql.parsing.properties.DeletionParameters;
import jdbms.sql.parsing.properties.InsertionParameters;
import jdbms.sql.parsing.properties.SelectionParameters;
import jdbms.sql.parsing.properties.TableCreationParameters;
import jdbms.sql.parsing.properties.TableDroppingParameters;
import jdbms.sql.parsing.properties.UpdatingParameters;
import jdbms.sql.parsing.properties.UseParameters;


public class SQLData {

	/**array of databases.*/
	private Map<String, Database> data;
	/**Currently Active Database.*/
	private Database activeDatabase;

	public SQLData() {
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
		if (!data.containsKey(dropDBParameters.getDatabaseName())) {
			//ErrorHandler.printDatabaseNotFoundError();
			return;
		}
		data.remove(dropDBParameters.getDatabaseName());
	}

	/**
	 * Returns the values selected.
	 * @param selectParameters the {@link SelectionParameters} object
	 * specifying the parameters of the select query
	 * @return the output of the select query
	 */
	public SelectQueryOutput selectFrom(SelectionParameters
			selectParameters) {
		try {
			return activeDatabase.selectFrom(selectParameters);
		} catch (ColumnNotFoundException e) {
			// ErrorHandler.printColumnNotFoundError()
		} catch (TypeMismatchException e) {
			// ErrorHandler.printTypeMismatchError()
		} catch (TableNotFoundException e) {
			// ErrorHandler.printTableNotFoundError()
		}
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

	public void insertInto(InsertionParameters parameters) {
		try {
			activeDatabase.insertInto(parameters);
		} catch (RepeatedColumnException e) {
			// ErrorHandler.printRepeatedAlreadyExistsError()
		} catch (ColumnListTooLargeException e) {
			// ErrorHandler.printColumnListTooLarge()
		} catch (ColumnNotFoundException e) {
			// ErrorHandler.printColumnNotFoundException();
		} catch (ValueListTooLargeException e) {
			// ErrorHandler.printValueListTooLarge()
		} catch (ValueListTooSmallException e) {
			// ErrorHandler.printValueListTooSmall();
		}
	}
	public void deleteFrom(DeletionParameters deleteParameters) {
		try {
			activeDatabase.deleteFromTable(deleteParameters);
		} catch (ColumnNotFoundException e) {
			// ErrorHandler.printColumnNotFoundError();
		} catch (TypeMismatchException e) {
			// ErrorHandler.printTypeMismatchError();
			e.printStackTrace();
		} catch (TableNotFoundException e) {
			// ErrorHandler.printTableNotFoundError();
			e.printStackTrace();
		}
	}
	public void updateTable(UpdatingParameters updateParameters) {
		try {
			activeDatabase.updateTable(updateParameters);
		} catch (ColumnNotFoundException e) {
			// ErrorHandler.printColumnNotFoundError();
		} catch (TypeMismatchException e) {
			// ErrorHandler.printTypeMismatchError();
			e.printStackTrace();
		} catch (TableNotFoundException e) {
			// ErrorHandler.printTableNotFoundError()
			e.printStackTrace();
		}
	}
 }
