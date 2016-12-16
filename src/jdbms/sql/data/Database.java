package jdbms.sql.data;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import jdbms.sql.data.query.SelectQueryOutput;
import jdbms.sql.exceptions.AllColumnsDroppingException;
import jdbms.sql.exceptions.ColumnAlreadyExistsException;
import jdbms.sql.exceptions.ColumnListTooLargeException;
import jdbms.sql.exceptions.ColumnNotFoundException;
import jdbms.sql.exceptions.FailedToDeleteTableException;
import jdbms.sql.exceptions.InvalidDateFormatException;
import jdbms.sql.exceptions.RepeatedColumnException;
import jdbms.sql.exceptions.TableAlreadyExistsException;
import jdbms.sql.exceptions.TableNotFoundException;
import jdbms.sql.exceptions.TypeMismatchException;
import jdbms.sql.exceptions.ValueListTooLargeException;
import jdbms.sql.exceptions.ValueListTooSmallException;
import jdbms.sql.file.FileHandler;
import jdbms.sql.parsing.properties.AddColumnParameters;
import jdbms.sql.parsing.properties.DeletionParameters;
import jdbms.sql.parsing.properties.DropColumnParameters;
import jdbms.sql.parsing.properties.InsertionParameters;
import jdbms.sql.parsing.properties.SelectionParameters;
import jdbms.sql.parsing.properties.TableCreationParameters;
import jdbms.sql.parsing.properties.UpdatingParameters;

public class Database {

	/**Array of database tables.*/
	//private final Map<String, Table> tables;
	private final Set<String> tables;
	/**Database name.*/
	private final String databaseName;

	public Database(final String databaseName) {
		this.databaseName = databaseName;
		tables = new HashSet<>();
	}
	/**
	 * Adds a table to the database.
	 * @param newTableIdentifier the table
	 * identifier object
	 * @param fileHandler the filehandler
	 * that will write the table
	 * @return the number of updated rows
	 * @throws TableAlreadyExistsException If a
	 * table with the same name already exists
	 * in the database
	 * @throws ColumnAlreadyExistsException If a
	 * column with the same name
	 * already exists in the created table
	 * @throws InvalidDateFormatException If a date
	 * value in the table is wrong
	 * @throws IOException If the file handler failed to
	 * write the table to the disk
	 */
	public int addTable(final TableIdentifier newTableIdentifier,
			final FileHandler fileHandler)
					throws TableAlreadyExistsException,
					ColumnAlreadyExistsException, InvalidDateFormatException,
					IOException {
		if (tables.contains(newTableIdentifier.getTableName().toUpperCase())) {
			throw new TableAlreadyExistsException(
					newTableIdentifier.getTableName());
		}
		tables.add(newTableIdentifier.getTableName().toUpperCase());
		fileHandler.createTable(new Table(newTableIdentifier),
				databaseName.toUpperCase());
		return 0;
	}
	/**
	 * Adds a table to the database.
	 * @param tableParameters The
	 * {@link TableCreationParameters} object
	 * @param fileHandler the filehandler
	 * that will write the table
	 * @return the number of updated rows
	 * @throws TableAlreadyExistsException If a
	 * table with the same name already exists
	 * in the database
	 * @throws ColumnAlreadyExistsException If a
	 * column with the same name
	 * already exists in the created table
	 * @throws InvalidDateFormatException If a date
	 * value in the table is wrong
	 * @throws IOException If the file handler failed to
	 * write the table to the disk
	 */
	public int addTable(final TableCreationParameters tableParameters,
			final FileHandler fileHandler)
					throws ColumnAlreadyExistsException,
					TableAlreadyExistsException,
					InvalidDateFormatException,
					IOException {
		if (tables.contains(tableParameters.getTableName().toUpperCase())) {
			throw new TableAlreadyExistsException(
					tableParameters.getTableName());
		}
		tables.add(tableParameters.getTableName().toUpperCase());
		final Table newTable = new Table(tableParameters);
		fileHandler.createTable(newTable, databaseName.toUpperCase());
		return 0;
	}
	/**
	 * Adds a table name to the database.
	 * @param tableName The name of the table
	 * @return The number of updated rows
	 * @throws TableAlreadyExistsException If a
	 * table with the same name already exists
	 * in the database
	 */
	public int addTableName(final String tableName)
			throws TableAlreadyExistsException {
		if (tables.contains(tableName.toUpperCase())) {
			throw new TableAlreadyExistsException(
					tableName);
		}
		tables.add(tableName.toUpperCase());
		return 0;
	}
	/**
	 * Drops a table from the database.
	 * @param tableName The name of the
	 * table to be dropped
	 * @param fileHandler The {@link FileHandler}
	 * object that will delete the table from
	 * the disk
	 * @return The number of updated rows
	 * @throws TableNotFoundException If a
	 * table with the same name does not exist
	 * in the database
	 * @throws FailedToDeleteTableException If
	 * the file handler failed to delete the table
	 * from the disk
	 */
	public int dropTable(final String tableName,
			final FileHandler fileHandler)
					throws TableNotFoundException,
					FailedToDeleteTableException {
		if (!tables.contains(tableName.toUpperCase())) {
			throw new TableNotFoundException(tableName);
		}
		tables.remove(tableName.toUpperCase());
		fileHandler.deleteTable(tableName, databaseName.toUpperCase());
		return 0;
	}
	/**
	 * Deletes a set of rows from a table.
	 * @param deleteParameters The {@link DeletionParameters}
	 * specifying which rows will be deleted
	 * @param fileHandler The {@link FileHandler}
	 * object that will read the table from the disk
	 * and write it back after it has been edited
	 * @return The number of deleted row
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
	 * @see Table#deleteRows(jdbms.sql.parsing.expressions.math.BooleanExpression)
	 */
	public int deleteFromTable(final DeletionParameters deleteParameters,
			final FileHandler fileHandler)
					throws ColumnNotFoundException,
					TypeMismatchException, TableNotFoundException,
					ColumnAlreadyExistsException, RepeatedColumnException,
					ColumnListTooLargeException, ValueListTooLargeException,
					ValueListTooSmallException, InvalidDateFormatException,
					IOException {
		if (!tables.contains(deleteParameters.getTableName().toUpperCase())) {
			throw new TableNotFoundException(
					deleteParameters.getTableName());
		}
		final Table activeTable = fileHandler.loadTable(databaseName.toUpperCase(),
				deleteParameters.getTableName().toUpperCase());
		final int numberOfDeletions
		= activeTable.deleteRows(deleteParameters.getCondition());
		fileHandler.createTable(activeTable, databaseName.toUpperCase());
		return numberOfDeletions;
	}
	/**
	 * Inserts values into the table.
	 * @param insertParameters The {@link InsertionParameters}
	 * Specifying the values to be inserted into the table
	 * @param fileHandler The {@link FileHandler}
	 * object that will read the table from the disk
	 * and write it back after it has been edited
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
	 * @see Table#insertRows(InsertionParameters)
	 */
	public int insertInto (final InsertionParameters insertParameters,
			final FileHandler fileHandler)
					throws RepeatedColumnException,
					ColumnListTooLargeException, ColumnNotFoundException,
					ValueListTooLargeException, ValueListTooSmallException,
					TableNotFoundException, TypeMismatchException,
					ColumnAlreadyExistsException,
					InvalidDateFormatException,
					IOException {
		if (!tables.contains(insertParameters.getTableName().toUpperCase())) {
			throw new TableNotFoundException(
					insertParameters.getTableName());
		}
		final Table activeTable = fileHandler.loadTable(databaseName.toUpperCase(),
				insertParameters.getTableName().toUpperCase());
		final int numberOfInsertions
		= activeTable.insertRows(insertParameters);
		fileHandler.createTable(activeTable, databaseName.toUpperCase());
		return numberOfInsertions;
	}
	/**
	 * Selects a set of rows and columns from
	 * a table.
	 * @param selectParameters The {@link SelectionParameters}
	 * specifying which rows and columns will be selected
	 * @param fileHandler The {@link FileHandler}
	 * object that will read the table from the disk
	 * and write it back after it has been edited
	 * @return The {@link SelectQueryOutput}
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
	 * @see Table#selectFromTable(SelectionParameters)
	 */
	public SelectQueryOutput selectFrom(
			final SelectionParameters selectParameters,
			final FileHandler fileHandler)
					throws ColumnNotFoundException,
					TypeMismatchException, TableNotFoundException,
					ColumnAlreadyExistsException, RepeatedColumnException,
					ColumnListTooLargeException, ValueListTooLargeException,
					ValueListTooSmallException,
					InvalidDateFormatException, IOException {
		if (!tables.contains(selectParameters.getTableName().toUpperCase())) {
			throw new TableNotFoundException(
					selectParameters.getTableName());
		}
		final Table activeTable = fileHandler.loadTable(databaseName.toUpperCase(),
				selectParameters.getTableName().toUpperCase());
		return activeTable.selectFromTable(selectParameters);
	}
	/**
	 * Updates some values in the table.
	 * @param updateParameters The {@link UpdatingParameters}
	 * specifying the rows to be updated and the value to be
	 * assigned
	 * @param fileHandler The {@link FileHandler}
	 * object that will read the table from the disk
	 * and write it back after it has been edited
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
	 * @see Table#updateTable(UpdatingParameters)
	 */
	public int updateTable(final UpdatingParameters updateParameters,
			final FileHandler fileHandler)
					throws ColumnNotFoundException, TypeMismatchException,
					TableNotFoundException, ColumnAlreadyExistsException,
					RepeatedColumnException, ColumnListTooLargeException,
					ValueListTooLargeException, ValueListTooSmallException,
					InvalidDateFormatException, IOException {
		if (!tables.contains(updateParameters.getTableName().toUpperCase())) {
			throw new TableNotFoundException(
					updateParameters.getTableName());
		}
		final Table activeTable = fileHandler.loadTable(databaseName.toUpperCase(),
				updateParameters.getTableName().toUpperCase());
		final int numberOfUpdates
		= activeTable.updateTable(updateParameters);
		fileHandler.createTable(activeTable, databaseName.toUpperCase());
		return numberOfUpdates;
	}
	/**
	 * Adds a column to the table.
	 * @param parameters The {@link AddColumnParameters}
	 * specifying the added columns
	 * @param fileHandler The {@link FileHandler}
	 * object that will read the table from the disk
	 * and write it back after it has been edited
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
	 * @see Table#addTableColumn(String, String)
	 */
	public int addTableColumn(final AddColumnParameters parameters,
			final FileHandler fileHandler)
					throws ColumnAlreadyExistsException,
					TableNotFoundException, RepeatedColumnException,
					ColumnListTooLargeException, ColumnNotFoundException,
					ValueListTooLargeException, ValueListTooSmallException,
					TypeMismatchException, InvalidDateFormatException,
					IOException {
		if (!tables.contains(parameters.
				getTableName().toUpperCase())) {
			throw new TableNotFoundException(parameters.getTableName());
		}
		final Table activeTable = fileHandler.loadTable(databaseName.toUpperCase(),
				parameters.getTableName().toUpperCase());
		final int returnValue
		= activeTable.addTableColumn(
				parameters.getColumnIdentifier().getName(),
				parameters.getColumnIdentifier().getType());
		fileHandler.createTable(activeTable, databaseName.toUpperCase());
		return returnValue;
	}
	/**
	 * Drops one or more columns from the table.
	 * @param parameters The {@link DropColumnParameters}
	 * specifying which columns will be dropped
	 * @param fileHandler The {@link FileHandler}
	 * object that will read the table from the disk
	 * and write it back after it has been edited
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
	 * @see Table#dropTableColumn(DropColumnParameters)
	 */
	public int dropTableColumn(final DropColumnParameters parameters,
			final FileHandler fileHandler)
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
		if (!tables.contains(parameters.
				getTableName().toUpperCase())) {
			throw new TableNotFoundException(parameters.getTableName() );
		}
		final Table activeTable = fileHandler.loadTable(databaseName.toUpperCase(),
				parameters.getTableName().toUpperCase());
		final int returnValue
		= activeTable.dropTableColumn(parameters);
		fileHandler.createTable(activeTable, databaseName.toUpperCase());
		return returnValue;
	}
	/**
	 * Returns the name of the database.
	 * @return the name of the database
	 */
	public String getDatabaseName() {
		return databaseName;
	}

}
