package jdbms.sql.data;

import java.io.IOException;
import java.util.Random;

import jdbms.sql.data.query.SelectQueryOutput;
import jdbms.sql.exceptions.AllColumnsDroppingException;
import jdbms.sql.exceptions.ColumnAlreadyExistsException;
import jdbms.sql.exceptions.ColumnListTooLargeException;
import jdbms.sql.exceptions.ColumnNotFoundException;
import jdbms.sql.exceptions.DatabaseAlreadyExistsException;
import jdbms.sql.exceptions.DatabaseNotFoundException;
import jdbms.sql.exceptions.FailedToDeleteDatabaseException;
import jdbms.sql.exceptions.FailedToDeleteTableException;
import jdbms.sql.exceptions.FileFormatNotSupportedException;
import jdbms.sql.exceptions.InvalidDateFormatException;
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
import jdbms.sql.parsing.properties.DropColumnParameters;
import jdbms.sql.parsing.properties.InsertionParameters;
import jdbms.sql.parsing.properties.SelectionParameters;
import jdbms.sql.parsing.properties.TableCreationParameters;
import jdbms.sql.parsing.properties.TableDroppingParameters;
import jdbms.sql.parsing.properties.UpdatingParameters;
import jdbms.sql.parsing.properties.UseParameters;

/** A class representing the current sql
 * data.
 * @author Mohammed Abdelbarry
 */
public class SQLData {

	/**Currently Active Database.*/
	private Database activeDatabase;
	/** The File Handler **/
	private final FileHandler fileHandler;
	private static final String DEFAULT_DATABASE = "default";
	/**
	 * Constructs a new SQLData.
	 */
	public SQLData() {
		fileHandler = new FileHandler();
		while (activeDatabase == null) {
			try {
				activeDatabase = createTemporaryDatabase();
			} catch (final DatabaseAlreadyExistsException e) {

			}
		}
	}
	/**
	 * Constructs a new SQLData.
	 * @param fileType The Back-End parser
	 * type
	 * @param filePath The path of the user's data
	 * @throws FileFormatNotSupportedException If
	 * the type of the back-end parser was not supported
	 */
	public SQLData(final String fileType, final String filePath)
			throws FileFormatNotSupportedException {
		fileHandler = new FileHandler(fileType, filePath);
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
	 * @return The number of updates
	 * @throws TableAlreadyExistsException If a table
	 * with the same name already exists in the database
	 * @throws DatabaseNotFoundException If the database
	 * to be deleted was not found
	 */
	public int setActiveDatabase(final UseParameters useParameters)
			throws DatabaseNotFoundException,
			TableAlreadyExistsException {
		activeDatabase = fileHandler.loadDatabase(
				useParameters.getDatabaseName());
		return 0;
	}

	/**
	 * Creates a new blank database with the name provided and returns it.
	 * @param createDBParameters The parameters of the sql
	 * create statement
	 * @return The Number of updates
	 * @throws DatabaseAlreadyExistsException If a database
	 * with the same name already exists
	 */
	public int createDatabase(final DatabaseCreationParameters
			createDBParameters) throws DatabaseAlreadyExistsException {
		final Database newDatabase
		= new Database(createDBParameters.getDatabaseName().toUpperCase());
		fileHandler.createDatabase(
				createDBParameters.getDatabaseName());
		activeDatabase = newDatabase;
		return 0;
	}
	/**
	 * Drops the database with the provided name.
	 * @param DatabaseDroppingParameters The
	 * {@link DatabaseDroppingParameters}
	 * @return The number of updates
	 * @throws DatabaseNotFoundException If the database
	 * to be deleted was not found
	 * @throws FailedToDeleteDatabaseException If the
	 * {@link FileHandler} failed to delete the database
	 * @throws FailedToDeleteTableException If the
	 * {@link FileHandler} failed to delete a table
	 * in the database
	 * @throws TableNotFoundException If a table
	 * was not found in the database
	 */
	public int dropDatabase(final DatabaseDroppingParameters dropDBParameters)
			throws DatabaseNotFoundException,
			FailedToDeleteDatabaseException,
			TableNotFoundException,
			FailedToDeleteTableException {
		fileHandler.deleteDatabase(
				dropDBParameters.getDatabaseName());
		if (dropDBParameters.getDatabaseName().equalsIgnoreCase(
				activeDatabase.getDatabaseName())) {
			activeDatabase = null;
			while (activeDatabase == null) {
				try {
					activeDatabase = createTemporaryDatabase();
				} catch (final DatabaseAlreadyExistsException e) {

				}
			}
		}
		return 0;
	}
	/**
	 * Returns the values selected.
	 * @param selectParameters the {@link SelectionParameters} object
	 * specifying the parameters of the select query
	 * @return the output of the select query
	 * @throws ColumnNotFoundException When a column does not
	 * exist in the table
	 * @throws TypeMismatchException When the user tries
	 * to compare a value to a value of the wrong type
	 * @throws TableNotFoundException If a
	 * table with the same name does not exist
	 * in the database
	 * @throws ColumnAlreadyExistsException If a
	 * column with the same name
	 * already exists in the created table
	 * @throws InvalidDateFormatException If a date
	 * value in the table is wrong
	 * @throws IOException If the file handler failed to
	 * write the table to the disk
	 * @throws RepeatedColumnException If a column
	 * was repeated when loading the table
	 * @throws ColumnListTooLargeException If the column
	 * list was too large when loading the table
	 * @throws ValueListTooLargeException If the value list
	 * was too large when loading the table
	 * @throws ValueListTooSmallException If the value list
	 * was too small when loading the table
	 * @see Database#selectFrom(SelectionParameters, FileHandler)
	 * @see Table#selectFromTable(SelectionParameters)
	 */
	public SelectQueryOutput selectFrom(final SelectionParameters
			selectParameters) throws ColumnNotFoundException,
	TypeMismatchException, TableNotFoundException,
	ColumnAlreadyExistsException, RepeatedColumnException,
	ColumnListTooLargeException, ValueListTooLargeException,
	ValueListTooSmallException, InvalidDateFormatException,
	IOException {
		return activeDatabase.selectFrom(selectParameters, fileHandler);
	}
	/**
	 * Creates a table given the table
	 * creation parameters.
	 * @param tableParamters the table creation parameters
	 * @return The number of updated rows
	 * @throws TableAlreadyExistsException If a table
	 * already exists in the database with the same
	 * name
	 * @throws ColumnAlreadyExistsException If a
	 * column with the same name
	 * @throws InvalidDateFormatException If a date
	 * value in the table is wrong
	 * @throws IOException If the file handler failed to
	 * write the table to the disk
	 * @see Database#addTable(TableCreationParameters, FileHandler)
	 */
	public int createTable(final TableCreationParameters tableParamters)
			throws ColumnAlreadyExistsException, TableAlreadyExistsException,
			InvalidDateFormatException, IOException {
		return activeDatabase.addTable(tableParamters, fileHandler);
	}
	/**
	 * Drops a table given the
	 * table dropping parameters.
	 * @param tableParameters the table
	 * dropping parameters
	 * @return The number of updated rows
	 * @throws TableNotFoundException If the table to be dropped
	 * was not found
	 * @throws FailedToDeleteTableException If the
	 * {@link FileHandler} failed to delete the table
	 * @see Database#dropTable(String, FileHandler)
	 */
	public int dropTable(final TableDroppingParameters tableParameters)
			throws TableNotFoundException, FailedToDeleteTableException {
		return activeDatabase.dropTable(
				tableParameters.getTableName(), fileHandler);

	}
	/**
	 * Inserts rows of values into a table.
	 * @param parameters The {@link InsertionParameters}
	 * specifying the inserted rows
	 * @return The number of inserted rows
	 * @throws ColumnNotFoundException When a column does not
	 * exist in the table
	 * @throws TypeMismatchException When the user tries
	 * to compare a value to a value of the wrong type
	 * @throws TableNotFoundException If a
	 * table with the same name does not exist
	 * in the database
	 * @throws ColumnAlreadyExistsException If a
	 * column with the same name
	 * already exists in the created table
	 * @throws InvalidDateFormatException If a date
	 * value in the table is wrong
	 * @throws IOException If the file handler failed to
	 * write the table to the disk
	 * @throws RepeatedColumnException If a column
	 * was repeated when loading the table
	 * @throws ColumnListTooLargeException If the column
	 * list was too large when loading the table
	 * @throws ValueListTooLargeException If the value list
	 * was too large when loading the table
	 * @throws ValueListTooSmallException If the value list
	 * was too small when loading the table
	 * @see Database#insertInto(InsertionParameters, FileHandler)
	 * @see Table#insertRows(InsertionParameters)
	 */
	public int insertInto(final InsertionParameters parameters)
			throws ColumnAlreadyExistsException, RepeatedColumnException,
			ColumnListTooLargeException, ColumnNotFoundException,
			ValueListTooLargeException, ValueListTooSmallException,
			TableNotFoundException, TypeMismatchException,
			InvalidDateFormatException, IOException {
		return activeDatabase.insertInto(parameters, fileHandler);
	}
	/**
	 * Deletes rows from a table
	 * given its table deletion parameters.
	 * @param deleteParameters the table deletion parameters.
	 * @return The number of deleted rows
	 * @throws ColumnNotFoundException When a column does not
	 * exist in the table
	 * @throws TypeMismatchException When the user tries
	 * to compare a value to a value of the wrong type
	 * @throws TableNotFoundException If a
	 * table with the same name does not exist
	 * in the database
	 * @throws ColumnAlreadyExistsException If a
	 * column with the same name
	 * already exists in the created table
	 * @throws InvalidDateFormatException If a date
	 * value in the table is wrong
	 * @throws IOException If the file handler failed to
	 * write the table to the disk
	 * @throws RepeatedColumnException If a column
	 * was repeated when loading the table
	 * @throws ColumnListTooLargeException If the column
	 * list was too large when loading the table
	 * @throws ValueListTooLargeException If the value list
	 * was too large when loading the table
	 * @throws ValueListTooSmallException If the value list
	 * was too small when loading the table
	 * @see Database#deleteFromTable(DeletionParameters, FileHandler)
	 * @see Table#deleteRows(jdbms.sql.parsing.expressions.math.BooleanExpression)
	 */
	public int deleteFrom(final DeletionParameters deleteParameters)
			throws ColumnNotFoundException, TypeMismatchException,
			TableNotFoundException, ColumnAlreadyExistsException,
			RepeatedColumnException, ColumnListTooLargeException,
			ValueListTooLargeException, ValueListTooSmallException,
			InvalidDateFormatException, IOException {
		return activeDatabase.deleteFromTable(deleteParameters, fileHandler);
	}
	/**
	 * Updates a table given its update parameters
	 * @param updateParameters the update parameters
	 * @return The number of updated rows
	 * @throws ColumnNotFoundException When a column does not
	 * exist in the table
	 * @throws TypeMismatchException When the user tries
	 * to compare a value to a value of the wrong type
	 * @throws TableNotFoundException If a
	 * table with the same name does not exist
	 * in the database
	 * @throws ColumnAlreadyExistsException If a
	 * column with the same name
	 * already exists in the created table
	 * @throws InvalidDateFormatException If a date
	 * value in the table is wrong
	 * @throws IOException If the file handler failed to
	 * write the table to the disk
	 * @throws RepeatedColumnException If a column
	 * was repeated when loading the table
	 * @throws ColumnListTooLargeException If the column
	 * list was too large when loading the table
	 * @throws ValueListTooLargeException If the value list
	 * was too large when loading the table
	 * @throws ValueListTooSmallException If the value list
	 * was too small when loading the table
	 * @see Database#updateTable(UpdatingParameters, FileHandler)
	 * @see Table#updateTable(UpdatingParameters)
	 */
	public int updateTable(final UpdatingParameters
			updateParameters)
					throws ColumnNotFoundException,
					TypeMismatchException,
					TableNotFoundException,
					ColumnAlreadyExistsException,
					RepeatedColumnException,
					ColumnListTooLargeException,
					ValueListTooLargeException,
					ValueListTooSmallException,
					InvalidDateFormatException,
					IOException {
		return activeDatabase.updateTable(updateParameters, fileHandler);
	}
	/**
	 * Adds a new table column.
	 * @param parameters the column addition
	 * parameters
	 * @return The number of updated rows
	 * @throws ColumnNotFoundException When a column does not
	 * exist in the table
	 * @throws TypeMismatchException When the user tries
	 * to compare a value to a value of the wrong type
	 * @throws TableNotFoundException If a
	 * table with the same name does not exist
	 * in the database
	 * @throws ColumnAlreadyExistsException If a
	 * column with the same name
	 * already exists in the created table
	 * @throws InvalidDateFormatException If a date
	 * value in the table is wrong
	 * @throws IOException If the file handler failed to
	 * write the table to the disk
	 * @throws RepeatedColumnException If a column
	 * was repeated when loading the table
	 * @throws ColumnListTooLargeException If the column
	 * list was too large when loading the table
	 * @throws ValueListTooLargeException If the value list
	 * was too large when loading the table
	 * @throws ValueListTooSmallException If the value list
	 * was too small when loading the table
	 * @see Database#addTableColumn(AddColumnParameters, FileHandler)
	 * @see Table#addTableColumn(String, String)
	 */
	public int addTableColumn(final
			AddColumnParameters parameters)
					throws ColumnAlreadyExistsException,
					TableNotFoundException,
					RepeatedColumnException,
					ColumnListTooLargeException,
					ColumnNotFoundException,
					ValueListTooLargeException,
					ValueListTooSmallException,
					TypeMismatchException,
					InvalidDateFormatException,
					IOException {
		return activeDatabase.addTableColumn(parameters, fileHandler);
	}
	/**
	 * Drops a table column.
	 * @param parameters the {@link DropColumnParameters}
	 * @return The number of updated rows
	 * @throws ColumnNotFoundException When a column does not
	 * exist in the table
	 * @throws TypeMismatchException When the user tries
	 * to compare a value to a value of the wrong type
	 * @throws TableNotFoundException If a
	 * table with the same name does not exist
	 * in the database
	 * @throws ColumnAlreadyExistsException If a
	 * column with the same name
	 * already exists in the created table
	 * @throws InvalidDateFormatException If a date
	 * value in the table is wrong
	 * @throws IOException If the file handler failed to
	 * write the table to the disk
	 * @throws RepeatedColumnException If a column
	 * was repeated when loading the table
	 * @throws ColumnListTooLargeException If the column
	 * list was too large when loading the table
	 * @throws ValueListTooLargeException If the value list
	 * was too large when loading the table
	 * @throws ValueListTooSmallException If the value list
	 * was too small when loading the table
	 * @throws AllColumnsDroppingException If The user tries
	 * to drop all the columns in a table
	 * @see Database#dropTableColumn(DropColumnParameters, FileHandler)
	 * @see Table#dropTableColumn(DropColumnParameters)
	 */
	public int dropTableColumn(final
			DropColumnParameters parameters)
					throws ColumnAlreadyExistsException,
					RepeatedColumnException,
					ColumnListTooLargeException,
					ColumnNotFoundException,
					ValueListTooLargeException,
					ValueListTooSmallException,
					TypeMismatchException,
					InvalidDateFormatException,
					TableNotFoundException,
					IOException,
					AllColumnsDroppingException {
		return activeDatabase.dropTableColumn(parameters, fileHandler);
	}
	/**
	 * Creates a temporary database.
	 * @return a temporary database
	 * @throws DatabaseAlreadyExistsException
	 */
	private Database createTemporaryDatabase()
			throws DatabaseAlreadyExistsException {
		return fileHandler.createTemporaryDatabase(
				DEFAULT_DATABASE.toLowerCase() +
				Math.abs(new Random().nextLong()));
	}
}
