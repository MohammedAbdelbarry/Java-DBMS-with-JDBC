package jdbms.sql.data;

import java.util.Random;

import jdbms.sql.data.query.SelectQueryOutput;
import jdbms.sql.errors.ErrorHandler;
import jdbms.sql.exceptions.ColumnAlreadyExistsException;
import jdbms.sql.exceptions.ColumnListTooLargeException;
import jdbms.sql.exceptions.ColumnNotFoundException;
import jdbms.sql.exceptions.DatabaseAlreadyExistsException;
import jdbms.sql.exceptions.DatabaseNotFoundException;
import jdbms.sql.exceptions.RepeatedColumnException;
import jdbms.sql.exceptions.TableAlreadyExistsException;
import jdbms.sql.exceptions.TableNotFoundException;
import jdbms.sql.exceptions.TypeMismatchException;
import jdbms.sql.exceptions.ValueListTooLargeException;
import jdbms.sql.exceptions.ValueListTooSmallException;
import jdbms.sql.file.FileHandler;
import jdbms.sql.parsing.properties.AddColumnParameters;
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

	/**Currently Active Database.*/
	private Database activeDatabase;
	private FileHandler handler;
	private static final String DEFAULT_DATABASE = "default";
	public SQLData() {
		handler = new FileHandler();
		while (activeDatabase == null) {
			try {
				activeDatabase = createTemporaryDatabase();
			} catch (DatabaseAlreadyExistsException e) {

			}
		}
	}

	/**
	 * Sets the given {@link Database} as active.
	 * @param useParameters The parameters of the sql use
	 * statement
	 * @throws TableAlreadyExistsException
	 * @throws DatabaseNotFoundException
	 */
	public void setActiveDatabase(UseParameters useParameters) {
		try {
			activeDatabase = handler.loadDatabase(
					useParameters.getDatabaseName().toUpperCase());
		} catch (DatabaseNotFoundException e) {
			ErrorHandler.printDatabaseNotFoundError(e.getMessage());
		} catch (TableAlreadyExistsException e) {
			ErrorHandler.printTableAlreadyExistsError(e.getMessage());
		} catch (ColumnAlreadyExistsException e) {
			ErrorHandler.printColumnAlreadyExistsError(e.getMessage());
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
		} catch (TypeMismatchException e) {
			ErrorHandler.printTypeMismatchError();
		}
	}

	/**
	 * Creates a new blank database with the name provided and returns it.
	 * @param createDBParameters The parameters of the sql
	 * create statement
	 */
	public void createDatabase(DatabaseCreationParameters
			createDBParameters) {

		try {
			Database newDatabase
			= new Database(createDBParameters.getDatabaseName().toUpperCase());
			handler.createDatabase(
					createDBParameters.getDatabaseName().toUpperCase());
			activeDatabase = newDatabase;
		} catch (DatabaseAlreadyExistsException e) {
			ErrorHandler.printDatabaseAlreadyExistsError(
					createDBParameters.getDatabaseName());
		}
	}

	/**
	 * Drops the database with the provided name.
	 */
	public void dropDatabase(DatabaseDroppingParameters dropDBParameters) {
		try {
			handler.deleteDatabase(
					dropDBParameters.getDatabaseName().toUpperCase());
			if (dropDBParameters.getDatabaseName().toUpperCase().equals(
					activeDatabase.getDatabaseName().toUpperCase())) {
				activeDatabase = null;
				while (activeDatabase == null) {
					try {
						activeDatabase = createTemporaryDatabase();
					} catch (DatabaseAlreadyExistsException e) {

					}
				}
			}
		} catch (DatabaseNotFoundException e) {
			ErrorHandler.printDatabaseNotFoundError(e.getMessage());
		}
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
			saveTable(tableParamters.getTableName());
		} catch (TableAlreadyExistsException e) {
			 ErrorHandler.printTableAlreadyExistsError(e.getMessage());
		} catch (ColumnAlreadyExistsException e) {
			 ErrorHandler.printColumnAlreadyExistsError(e.getMessage());
		}
	}

	public void dropTable(TableDroppingParameters tableParameters) {
		try {
			activeDatabase.dropTable(tableParameters.getTableName());
			handler.deleteTable(tableParameters.getTableName().toUpperCase(),
					activeDatabase.getDatabaseName().toUpperCase());
		} catch (TableNotFoundException e) {
			ErrorHandler.printTableNotFoundError(e.getMessage());
		}
	}

	public void insertInto(InsertionParameters parameters) {
		try {
			activeDatabase.insertInto(parameters);
			saveTable(parameters.getTableName());
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
			saveTable(deleteParameters.getTableName());
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
			saveTable(updateParameters.getTableName());
		} catch (ColumnNotFoundException e) {
			 ErrorHandler.printColumnNotFoundError(e.getMessage());
		} catch (TypeMismatchException e) {
			 ErrorHandler.printTypeMismatchError();
		} catch (TableNotFoundException e) {
			 ErrorHandler.printTableNotFoundError(e.getMessage());
		}
	}
	public void addTableColumn(AddColumnParameters parameters) {
		try {
			activeDatabase.addTableColumn(parameters);
			saveTable(parameters.getTableName());
		} catch (ColumnAlreadyExistsException e) {
			ErrorHandler.printColumnAlreadyExistsError(
					parameters.getColumnIdentifier().getName());
		} catch (TableNotFoundException e) {
			ErrorHandler.printTableNotFoundError(
					parameters.getTableName());
		}
	}
	private Database createTemporaryDatabase()
			throws DatabaseAlreadyExistsException {
		return handler.createTemporaryDatabase(
				DEFAULT_DATABASE.toUpperCase() +
				new Random().nextLong());
	}
	private void saveTable(String tableName) {
		handler.createTable(activeDatabase.getTable(tableName.toUpperCase()),
				activeDatabase.getDatabaseName().toUpperCase());
	}
 }
