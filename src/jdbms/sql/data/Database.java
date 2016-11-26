package jdbms.sql.data;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import jdbms.sql.exceptions.ColumnAlreadyExistsException;
import jdbms.sql.exceptions.ColumnListTooLargeException;
import jdbms.sql.exceptions.ColumnNotFoundException;
import jdbms.sql.exceptions.RepeatedColumnException;
import jdbms.sql.exceptions.TableAlreadyExistsException;
import jdbms.sql.parsing.properties.InsertionParameters;
import jdbms.sql.parsing.properties.TableCreationParameters;

public class Database {

	/**Array of database tables.*/
	private Map<String, Table> tables;
	/**Database name.*/
	private String databaseName;

	public Database(String databaseName) {
		this.databaseName = databaseName;
		tables = new HashMap<>();
		new File(databaseName).mkdir();
	}

	public void addTable(TableIdentifier newTableIdentifier)
			throws TableAlreadyExistsException,
			ColumnAlreadyExistsException {
		if (tables.containsKey(newTableIdentifier.getTableName())) {
			throw new TableAlreadyExistsException();
		}
		tables.put(newTableIdentifier.getTableName(),
			new Table(newTableIdentifier));

	}

	public void addTable(TableCreationParameters tableParameters)
			throws ColumnAlreadyExistsException,
			TableAlreadyExistsException {
		if (tables.containsKey(tableParameters.getTableName())) {
			throw new TableAlreadyExistsException();
		}
		Table newTable = new Table(tableParameters);
		tables.put(tableParameters.getTableName(),
				newTable);
	}
	public Table getTable(String tableName) {
		return tables.get(tableName);
	}

	public void dropTable(String tableName) {
		tables.remove(tableName);
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void insertInto (InsertionParameters parameters)
			throws RepeatedColumnException,
			ColumnListTooLargeException,
			ColumnNotFoundException {
		tables.get(parameters.getTableName()).insertRows(parameters);
	}
}
