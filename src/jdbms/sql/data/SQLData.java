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

/** A class representing the current sql
 * data.
 * @author Moham
 */
public class SQLData {

	/**Currently Active Database.*/
	private Database activeDatabase;
	private final FileHandler handler;
	private static final String DEFAULT_DATABASE = "default";
	public SQLData() {
		handler = new FileHandler();
		while (activeDatabase == null) {
			try {
				activeDatabase = createTemporaryDatabase();
			} catch (final DatabaseAlreadyExistsException e) {

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
	public void setActiveDatabase(final UseParameters useParameters) {
		try {
			activeDatabase = handler.loadDatabase(
					useParameters.getDatabaseName().toUpperCase());
		} catch (final DatabaseNotFoundException e) {
			ErrorHandler.printDatabaseNotFoundError(e.getMessage());
		} catch (final TableAlreadyExistsException e) {
			ErrorHandler.printTableAlreadyExistsError(e.getMessage());
		} catch (final ColumnAlreadyExistsException e) {
			ErrorHandler.printColumnAlreadyExistsError(e.getMessage());
		} catch (final RepeatedColumnException e) {
			ErrorHandler.printRepeatedColumnError();
		} catch (final ColumnListTooLargeException e) {
			ErrorHandler.printColumnListTooLargeError();
		} catch (final ColumnNotFoundException e) {
			ErrorHandler.printColumnNotFoundError(e.getMessage());
		} catch (final ValueListTooLargeException e) {
			ErrorHandler.printValueListTooLargeError();
		} catch (final ValueListTooSmallException e) {
			ErrorHandler.printValueListTooSmallError();
		} catch (final TypeMismatchException e) {
			ErrorHandler.printTypeMismatchError();
		}
	}

	/**
	 * Creates a new blank database with the name provided and returns it.
	 * @param createDBParameters The parameters of the sql
	 * create statement
	 */
	public void createDatabase(final DatabaseCreationParameters
			createDBParameters) {

		try {
			final Database newDatabase
			= new Database(createDBParameters.getDatabaseName().toUpperCase());
			handler.createDatabase(
					createDBParameters.getDatabaseName().toUpperCase());
			activeDatabase = newDatabase;
		} catch (final DatabaseAlreadyExistsException e) {
			ErrorHandler.printDatabaseAlreadyExistsError(
					createDBParameters.getDatabaseName());
		}
	}

	/**
	 * Drops the database with the provided name.
	 */
	public void dropDatabase(final DatabaseDroppingParameters dropDBParameters) {
		try {
			handler.deleteDatabase(
					dropDBParameters.getDatabaseName().toUpperCase());
			if (dropDBParameters.getDatabaseName().toUpperCase().equals(
					activeDatabase.getDatabaseName().toUpperCase())) {
				activeDatabase = null;
				while (activeDatabase == null) {
					try {
						activeDatabase = createTemporaryDatabase();
					} catch (final DatabaseAlreadyExistsException e) {

					}
				}
			}
		} catch (final DatabaseNotFoundException e) {
			ErrorHandler.printDatabaseNotFoundError(e.getMessage());
		}
	}

	/**
	 * Returns the values selected.
	 * @param selectParameters the {@link SelectionParameters} object
	 * specifying the parameters of the select query
	 * @return the output of the select query
	 */
	public SelectQueryOutput selectFrom(final SelectionParameters
			selectParameters) {
		try {
			return activeDatabase.selectFrom(selectParameters);
		} catch (final ColumnNotFoundException e) {
			ErrorHandler.
			printColumnNotFoundError(e.getMessage());
		} catch (final TypeMismatchException e) {
			ErrorHandler.printTypeMismatchError();
		} catch (final TableNotFoundException e) {
			ErrorHandler.
			printTableNotFoundError(e.getMessage());
		}
		return null;
	}
	/**
	 * Creates a table given the table
	 * creation parameters.
	 * @param tableParamters the table creation parameters
	 */
	public void createTable(final TableCreationParameters tableParamters) {
		try {
			activeDatabase.addTable(tableParamters);
			saveTable(tableParamters.getTableName());
		} catch (final TableAlreadyExistsException e) {
			ErrorHandler.printTableAlreadyExistsError(e.getMessage());
		} catch (final ColumnAlreadyExistsException e) {
			ErrorHandler.printColumnAlreadyExistsError(e.getMessage());
		}
	}
	/**
	 * Drops a table given the
	 * table dropping parameters.
	 * @param tableParameters the table
	 * dropping parameters
	 */
	public void dropTable(final TableDroppingParameters tableParameters) {
		try {
			activeDatabase.dropTable(
					tableParameters.getTableName());
			handler.deleteTable(
					tableParameters.getTableName().toUpperCase(),
					activeDatabase.getDatabaseName().toUpperCase());
		} catch (final TableNotFoundException e) {
			ErrorHandler.
			printTableNotFoundError(e.getMessage());
		}
	}

	public void insertInto(final InsertionParameters parameters) {
		try {
			activeDatabase.insertInto(parameters);
			saveTable(parameters.getTableName());
		} catch (final RepeatedColumnException e) {
			ErrorHandler.printRepeatedColumnError();
		} catch (final ColumnListTooLargeException e) {
			ErrorHandler.printColumnListTooLargeError();
		} catch (final ColumnNotFoundException e) {
			ErrorHandler.
			printColumnNotFoundError(e.getMessage());
		} catch (final ValueListTooLargeException e) {
			ErrorHandler.printValueListTooLargeError();
		} catch (final ValueListTooSmallException e) {
			ErrorHandler.printValueListTooSmallError();
		} catch (final TableNotFoundException e) {
			ErrorHandler.
			printTableNotFoundError(e.getMessage());
		} catch (final TypeMismatchException e) {
			ErrorHandler.printTypeMismatchError();
		}
	}
	/**
	 * Deletes rows from a table
	 * given its table deletion parameters.
	 * @param deleteParameters the table deletion parameters.
	 */
	public void deleteFrom(final DeletionParameters
			deleteParameters) {
		try {
			activeDatabase.deleteFromTable(deleteParameters);
			saveTable(deleteParameters.getTableName());
		} catch (final ColumnNotFoundException e) {
			ErrorHandler.
			printColumnNotFoundError(e.getMessage());
		} catch (final TypeMismatchException e) {
			ErrorHandler.
			printTypeMismatchError();
			e.printStackTrace();
		} catch (final TableNotFoundException e) {
			ErrorHandler.
			printTableNotFoundError(e.getMessage());
		}
	}
	/**
	 * Updates a table given its update parameters
	 * @param updateParameters the update parameters
	 */
	public void updateTable(final UpdatingParameters
			updateParameters) {
		try {
			activeDatabase.updateTable(updateParameters);
			saveTable(updateParameters.getTableName());
		} catch (final ColumnNotFoundException e) {
			ErrorHandler.printColumnNotFoundError(
					e.getMessage());
		} catch (final TypeMismatchException e) {
			ErrorHandler.printTypeMismatchError();
		} catch (final TableNotFoundException e) {
			ErrorHandler.printTableNotFoundError(
					e.getMessage());
		}
	}
	/**
	 * Adds a new table column.
	 * @param parameters the column addition
	 * parameters
	 */
	public void addTableColumn(final
			AddColumnParameters parameters) {
		try {
			activeDatabase.addTableColumn(parameters);
			saveTable(parameters.getTableName());
		} catch (final ColumnAlreadyExistsException e) {
			ErrorHandler.
			printColumnAlreadyExistsError(e.getMessage());
		} catch (final TableNotFoundException e) {
			ErrorHandler.
			printTableNotFoundError(
					e.getMessage());
		}
	}
	/**
	 * Creates a temporary database.
	 * @return a temporary database
	 * @throws DatabaseAlreadyExistsException
	 */
	private Database createTemporaryDatabase()
			throws DatabaseAlreadyExistsException {
		return handler.createTemporaryDatabase(
				DEFAULT_DATABASE.toUpperCase() +
				new Random().nextLong());
	}
	/**
	 * Saves a table to an xml file.
	 * @param tableName the table to be saved
	 */
	private void saveTable(final String tableName) {
		handler.createTable(
				activeDatabase.getTable(
						tableName.toUpperCase()),
				activeDatabase.getDatabaseName(
						).toUpperCase());
	}
}
