package jdbms.sql.data;

import java.util.HashSet;
import java.util.Set;

import jdbms.sql.data.query.SelectQueryOutput;
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

	public void addTable(final TableIdentifier newTableIdentifier,
			final FileHandler fileHandler)
					throws TableAlreadyExistsException,
					ColumnAlreadyExistsException, InvalidDateFormatException {
		if (tables.contains(newTableIdentifier.getTableName().toUpperCase())) {
			throw new TableAlreadyExistsException(
					newTableIdentifier.getTableName());
		}
		tables.add(newTableIdentifier.getTableName().toUpperCase());
		fileHandler.createTable(new Table(newTableIdentifier),
				databaseName.toUpperCase());
	}

	public void addTable(final TableCreationParameters tableParameters,
			final FileHandler fileHandler)
					throws ColumnAlreadyExistsException,
					TableAlreadyExistsException,
					InvalidDateFormatException {
		if (tables.contains(tableParameters.getTableName().toUpperCase())) {
			throw new TableAlreadyExistsException(
					tableParameters.getTableName());
		}
		tables.add(tableParameters.getTableName().toUpperCase());
		final Table newTable = new Table(tableParameters);
		fileHandler.createTable(newTable, databaseName.toUpperCase());
	}
	public void addTableName(final String tableName)
			throws TableAlreadyExistsException {
		if (tables.contains(tableName.toUpperCase())) {
			throw new TableAlreadyExistsException(
					tableName);
		}
		tables.add(tableName.toUpperCase());
	}
	public void dropTable(final String tableName,
			final FileHandler fileHandler)
					throws TableNotFoundException,
					FailedToDeleteTableException {
		if (!tables.contains(tableName.toUpperCase())) {
			throw new TableNotFoundException(tableName);
		}
		tables.remove(tableName.toUpperCase());
		fileHandler.deleteTable(tableName, databaseName.toUpperCase());
	}

	public void deleteFromTable(final DeletionParameters deleteParameters,
			final FileHandler fileHandler)
					throws ColumnNotFoundException,
					TypeMismatchException, TableNotFoundException,
					ColumnAlreadyExistsException, RepeatedColumnException,
					ColumnListTooLargeException, ValueListTooLargeException,
					ValueListTooSmallException, InvalidDateFormatException {
		if (!tables.contains(deleteParameters.getTableName().toUpperCase())) {
			throw new TableNotFoundException(
					deleteParameters.getTableName());
		}
		final Table activeTable = fileHandler.loadTable(databaseName.toUpperCase(),
				deleteParameters.getTableName().toUpperCase());
		activeTable.deleteRows(deleteParameters.getCondition());
		fileHandler.createTable(activeTable, databaseName.toUpperCase());
	}
	public void insertInto (final InsertionParameters insertParameters,
			final FileHandler fileHandler)
					throws RepeatedColumnException,
					ColumnListTooLargeException, ColumnNotFoundException,
					ValueListTooLargeException, ValueListTooSmallException,
					TableNotFoundException, TypeMismatchException,
					ColumnAlreadyExistsException,
					InvalidDateFormatException {
		if (!tables.contains(insertParameters.getTableName().toUpperCase())) {
			throw new TableNotFoundException(
					insertParameters.getTableName());
		}
		final Table activeTable = fileHandler.loadTable(databaseName.toUpperCase(),
				insertParameters.getTableName().toUpperCase());
		activeTable.insertRows(insertParameters);
		fileHandler.createTable(activeTable, databaseName.toUpperCase());
	}
	public SelectQueryOutput selectFrom(
			final SelectionParameters selectParameters,
			final FileHandler fileHandler)
					throws ColumnNotFoundException,
					TypeMismatchException, TableNotFoundException,
					ColumnAlreadyExistsException, RepeatedColumnException,
					ColumnListTooLargeException, ValueListTooLargeException,
					ValueListTooSmallException,
					InvalidDateFormatException {
		if (!tables.contains(selectParameters.getTableName().toUpperCase())) {
			throw new TableNotFoundException(
					selectParameters.getTableName());
		}
		final Table activeTable = fileHandler.loadTable(databaseName.toUpperCase(),
				selectParameters.getTableName().toUpperCase());
		return activeTable.selectFromTable(selectParameters);
	}
	public void updateTable(final UpdatingParameters updateParameters,
			final FileHandler fileHandler)
					throws ColumnNotFoundException, TypeMismatchException,
					TableNotFoundException, ColumnAlreadyExistsException,
					RepeatedColumnException, ColumnListTooLargeException,
					ValueListTooLargeException, ValueListTooSmallException,
					InvalidDateFormatException {
		if (!tables.contains(updateParameters.getTableName().toUpperCase())) {
			throw new TableNotFoundException(
					updateParameters.getTableName());
		}
		final Table activeTable = fileHandler.loadTable(databaseName.toUpperCase(),
				updateParameters.getTableName().toUpperCase());
		activeTable.updateTable(updateParameters);
		fileHandler.createTable(activeTable, databaseName.toUpperCase());
	}
	public void addTableColumn(final AddColumnParameters parameters,
			final FileHandler fileHandler)
					throws ColumnAlreadyExistsException,
					TableNotFoundException, RepeatedColumnException,
					ColumnListTooLargeException, ColumnNotFoundException,
					ValueListTooLargeException, ValueListTooSmallException,
					TypeMismatchException, InvalidDateFormatException {
		if (!tables.contains(parameters.
				getTableName().toUpperCase())) {
			throw new TableNotFoundException(parameters.getTableName());
		}
		final Table activeTable = fileHandler.loadTable(databaseName.toUpperCase(),
				parameters.getTableName().toUpperCase());
		activeTable.addTableColumn(parameters.getColumnIdentifier().getName(),
				parameters.getColumnIdentifier().getType());
		fileHandler.createTable(activeTable, databaseName.toUpperCase());
	}
	public String getDatabaseName() {
		return databaseName;
	}

}
