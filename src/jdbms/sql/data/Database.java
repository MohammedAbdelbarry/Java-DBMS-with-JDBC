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
import jdbms.sql.parsing.properties.DeletionParameters;
import jdbms.sql.parsing.properties.InsertionParameters;
import jdbms.sql.parsing.properties.SelectionParameters;
import jdbms.sql.parsing.properties.TableCreationParameters;
import jdbms.sql.parsing.properties.UpdatingParameters;

public class Database {

	/**Array of database tables.*/
	private Map<String, Table> tables;
	/**Database name.*/
	private String databaseName;

	public Database(String databaseName) {
		this.databaseName = databaseName;
		tables = new HashMap<>();
	}

	public void addTable(TableIdentifier newTableIdentifier)
			throws TableAlreadyExistsException,
			ColumnAlreadyExistsException {
		if (tables.containsKey(newTableIdentifier.getTableName().toUpperCase())) {
			throw new TableAlreadyExistsException(
					newTableIdentifier.getTableName());
		}
		tables.put(newTableIdentifier.getTableName().toUpperCase(),
			new Table(newTableIdentifier));

	}

	public void addTable(TableCreationParameters tableParameters)
			throws ColumnAlreadyExistsException,
			TableAlreadyExistsException {
		if (tables.containsKey(tableParameters.getTableName().toUpperCase())) {
			throw new TableAlreadyExistsException(
					tableParameters.getTableName());
		}
		Table newTable = new Table(tableParameters);
		tables.put(tableParameters.getTableName().toUpperCase(),
				newTable);
	}

	public void dropTable(String tableName) {
		tables.remove(tableName.toUpperCase());
	}

	public void deleteFromTable(DeletionParameters deleteParameters)
			throws ColumnNotFoundException,
			TypeMismatchException, TableNotFoundException {
		if (!tables.containsKey(deleteParameters.getTableName().toUpperCase())) {
			throw new TableNotFoundException(
					deleteParameters.getTableName());
		}
		tables.get(deleteParameters.getTableName().toUpperCase()).
		deleteRows(deleteParameters.getCondition());
	}
	public void insertInto (InsertionParameters insertParameters)
			throws RepeatedColumnException,
			ColumnListTooLargeException, ColumnNotFoundException,
			ValueListTooLargeException, ValueListTooSmallException,
			TableNotFoundException, TypeMismatchException {
		if (!tables.containsKey(insertParameters.getTableName().toUpperCase())) {
			throw new TableNotFoundException(
					insertParameters.getTableName());
		}
		tables.get(insertParameters.getTableName().toUpperCase()).
		insertRows(insertParameters);
	}
	public SelectQueryOutput selectFrom(
			SelectionParameters selectParameters)
			throws ColumnNotFoundException,
			TypeMismatchException, TableNotFoundException {
		if (!tables.containsKey(selectParameters.getTableName().toUpperCase())) {
			throw new TableNotFoundException(
					selectParameters.getTableName());
		}
		return tables.get(selectParameters.
				getTableName().toUpperCase()).selectFromTable(selectParameters);
	}
	public void updateTable(UpdatingParameters updateParameters)
			throws ColumnNotFoundException, TypeMismatchException,
			TableNotFoundException {
		if (!tables.containsKey(updateParameters.getTableName().toUpperCase())) {
			throw new TableNotFoundException(
					updateParameters.getTableName());
		}
		tables.get(updateParameters.getTableName().toUpperCase()).
		updateTable(updateParameters);
	}
	public String getDatabaseName() {
		return databaseName;
	}
	public Table getTable(String tableName) {
		return tables.get(tableName.toUpperCase());
	}
}
