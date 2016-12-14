package jdbms.sql.data;

import java.io.IOException;
import java.util.Random;

import jdbms.sql.data.query.SelectQueryOutput;
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
 * @author Moham
 */
public class SQLData {

	/**Currently Active Database.*/
	private Database activeDatabase;
	private final FileHandler fileHandler;
	private static final String DEFAULT_DATABASE = "default";
	public SQLData() {
		fileHandler = new FileHandler();
		while (activeDatabase == null) {
			try {
				activeDatabase = createTemporaryDatabase();
			} catch (final DatabaseAlreadyExistsException e) {

			}
		}
	}
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
	 * @throws TableAlreadyExistsException
	 * @throws DatabaseNotFoundException
	 */
	public int setActiveDatabase(final UseParameters useParameters)
			throws DatabaseNotFoundException,
			TableAlreadyExistsException {
		activeDatabase = fileHandler.loadDatabase(
				useParameters.getDatabaseName().toUpperCase());
		return 0;
	}

	/**
	 * Creates a new blank database with the name provided and returns it.
	 * @param createDBParameters The parameters of the sql
	 * create statement
	 * @throws DatabaseAlreadyExistsException
	 */
	public int createDatabase(final DatabaseCreationParameters
			createDBParameters) throws DatabaseAlreadyExistsException {
		final Database newDatabase
		= new Database(createDBParameters.getDatabaseName().toUpperCase());
		fileHandler.createDatabase(
				createDBParameters.getDatabaseName().toUpperCase());
		activeDatabase = newDatabase;
		return 0;
	}
	/**
	 * Drops the database with the provided name.
	 * @throws DatabaseNotFoundException
	 * @throws FailedToDeleteDatabaseException
	 * @throws FailedToDeleteTableException
	 * @throws TableNotFoundException
	 */
	public int dropDatabase(final DatabaseDroppingParameters dropDBParameters)
			throws DatabaseNotFoundException,
			FailedToDeleteDatabaseException,
			TableNotFoundException,
			FailedToDeleteTableException {
		fileHandler.deleteDatabase(
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
		return 0;
	}

	/**
	 * Returns the values selected.
	 * @param selectParameters the {@link SelectionParameters} object
	 * specifying the parameters of the select query
	 * @return the output of the select query
	 * @throws ValueListTooSmallException
	 * @throws ValueListTooLargeException
	 * @throws ColumnListTooLargeException
	 * @throws RepeatedColumnException
	 * @throws ColumnAlreadyExistsException
	 * @throws TableNotFoundException
	 * @throws TypeMismatchException
	 * @throws ColumnNotFoundException
	 * @throws InvalidDateFormatException
	 * @throws IOException
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
	 * @throws TableAlreadyExistsException
	 * @throws ColumnAlreadyExistsException
	 * @throws InvalidDateFormatException
	 * @throws IOException
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
	 * @throws TableNotFoundException
	 * @throws FailedToDeleteTableException
	 */
	public int dropTable(final TableDroppingParameters tableParameters)
			throws TableNotFoundException, FailedToDeleteTableException {
		return activeDatabase.dropTable(
				tableParameters.getTableName(), fileHandler);

	}

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
	 * @throws ValueListTooSmallException
	 * @throws ValueListTooLargeException
	 * @throws ColumnListTooLargeException
	 * @throws RepeatedColumnException
	 * @throws ColumnAlreadyExistsException
	 * @throws TableNotFoundException
	 * @throws TypeMismatchException
	 * @throws ColumnNotFoundException
	 * @throws InvalidDateFormatException
	 * @throws IOException
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
	 * @throws ValueListTooSmallException
	 * @throws ValueListTooLargeException
	 * @throws ColumnListTooLargeException
	 * @throws RepeatedColumnException
	 * @throws ColumnAlreadyExistsException
	 * @throws TableNotFoundException
	 * @throws TypeMismatchException
	 * @throws ColumnNotFoundException
	 * @throws InvalidDateFormatException
	 * @throws IOException
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
	 * @throws TypeMismatchException
	 * @throws ValueListTooSmallException
	 * @throws ValueListTooLargeException
	 * @throws ColumnNotFoundException
	 * @throws ColumnListTooLargeException
	 * @throws RepeatedColumnException
	 * @throws TableNotFoundException
	 * @throws ColumnAlreadyExistsException
	 * @throws InvalidDateFormatException
	 * @throws IOException
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
	 * @throws TableNotFoundException
	 * @throws InvalidDateFormatException
	 * @throws TypeMismatchException
	 * @throws ValueListTooSmallException
	 * @throws ValueListTooLargeException
	 * @throws ColumnNotFoundException
	 * @throws ColumnListTooLargeException
	 * @throws RepeatedColumnException
	 * @throws ColumnAlreadyExistsException
	 * @throws IOException
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
					IOException {
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
