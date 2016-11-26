package jdbms.sql.data;

import java.util.HashMap;
import java.util.Map;

import jdbms.sql.data.query.SelectQueryOutput;
import jdbms.sql.errors.ErrorHandler;
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
	private Map<String, Database> databases;
	/**Currently Active Database.*/
	private Database activeDatabase;
	private static final String DEFAULT_DATABASE = "default";
	public SQLData() {
		databases = new HashMap<>();
		databases.put(DEFAULT_DATABASE.toUpperCase(), new Database(DEFAULT_DATABASE));
		activeDatabase = databases.get(DEFAULT_DATABASE.toUpperCase());
	}

	/**
	 * Sets the given {@link Database} as active.
	 * @param useParameters The parameters of the sql use
	 * statement
	 */
	public void setActiveDatabase(UseParameters useParameters) {
		activeDatabase = databases.get(useParameters.getDatabaseName().toUpperCase());
	}

	/**
	 * Creates a new blank database with the name provided and returns it.
	 * @param createDBParameters The parameters of the sql
	 * create statement
	 */
	public void createDatabase(DatabaseCreationParameters
			createDBParameters) {
		if (databases.containsKey(createDBParameters.
				getDatabaseName().toUpperCase())) {
			ErrorHandler.printDatabaseAlreadyExistsError(
					createDBParameters.getDatabaseName());
			return;
		}
		Database newDatabase
		= new Database(createDBParameters.getDatabaseName());
		databases.put(createDBParameters.getDatabaseName().toUpperCase(), newDatabase);
		activeDatabase = newDatabase;
	}

	/**
	 * Drops the database with the provided name.
	 */
	public void dropDatabase(DatabaseDroppingParameters dropDBParameters) {
		if (!databases.containsKey(dropDBParameters.getDatabaseName().toUpperCase())) {
			ErrorHandler.printDatabaseNotFoundError(
					dropDBParameters.getDatabaseName());
			return;
		}
		if (dropDBParameters.getDatabaseName().equals(
				activeDatabase.getDatabaseName())) {
			activeDatabase = databases.get(DEFAULT_DATABASE.toUpperCase());
		}
		databases.remove(dropDBParameters.getDatabaseName());
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
			ErrorHandler.printColumnNotFoundError(e.getMessage());
		} catch (TypeMismatchException e) {
			ErrorHandler.printTypeMismatchError();
		} catch (TableNotFoundException e) {
			 ErrorHandler.printTableNotFoundError(e.getMessage());
		}
		return null;
	}

	public void createTable(TableCreationParameters tableParamters) {
		try {
			activeDatabase.addTable(tableParamters);
		} catch (TableAlreadyExistsException e) {
			 ErrorHandler.printTableAlreadyExistsError(e.getMessage());
		} catch (ColumnAlreadyExistsException e) {
			 ErrorHandler.printColumnAlreadyExistsError(e.getMessage());
		}
	}

	public void dropTable(TableDroppingParameters tableParameters) {
		activeDatabase.dropTable(tableParameters.getTableName());
	}

	public void insertInto(InsertionParameters parameters) {
		try {
			activeDatabase.insertInto(parameters);
		} catch (RepeatedColumnException e) {
			 ErrorHandler.printRepeatedColumnError();
		} catch (ColumnListTooLargeException e) {
			 ErrorHandler.printColumnListTooLargeError();
		} catch (ColumnNotFoundException e) {
			 ErrorHandler.printColumnNotFoundError(e.getMessage());
		} catch (ValueListTooLargeException e) {
			 ErrorHandler.printValueListTooLargeError();
		} catch (ValueListTooSmallException e) {
			 ErrorHandler.printValueListTooSmallError();
		} catch (TableNotFoundException e) {
			 ErrorHandler.printTableNotFoundError(e.getMessage());
		} catch (TypeMismatchException e) {
			ErrorHandler.printTypeMismatchError();
		}
	}
	public void deleteFrom(DeletionParameters deleteParameters) {
		try {
			activeDatabase.deleteFromTable(deleteParameters);
		} catch (ColumnNotFoundException e) {
			 ErrorHandler.printColumnNotFoundError(e.getMessage());
		} catch (TypeMismatchException e) {
			 ErrorHandler.printTypeMismatchError();
			e.printStackTrace();
		} catch (TableNotFoundException e) {
			 ErrorHandler.printTableNotFoundError(e.getMessage());
		}
	}
	public void updateTable(UpdatingParameters updateParameters) {
		try {
			activeDatabase.updateTable(updateParameters);
		} catch (ColumnNotFoundException e) {
			 ErrorHandler.printColumnNotFoundError(e.getMessage());
		} catch (TypeMismatchException e) {
			 ErrorHandler.printTypeMismatchError();
		} catch (TableNotFoundException e) {
			 ErrorHandler.printTableNotFoundError(e.getMessage());
		}
	}
 }
