package jdbms.sql.file;

import java.io.File;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.util.ArrayList;

import jdbms.sql.data.Database;
import jdbms.sql.data.Table;
import jdbms.sql.data.xml.XMLCreator;
import jdbms.sql.data.xml.XMLParser;
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
import jdbms.sql.parsing.parser.testing.TestingMain;

public class FileHandler {
	String path;
	XMLParser parser;
	XMLCreator creator;
	private static final String XML_EXTENSION = ".xml";
	private static final String DTD_IDENTIFIER = "DTD";
	private static final String DTD_EXTENSION = ".dtd";
	public FileHandler() {
		try {
			CodeSource codeSource = TestingMain.class.getProtectionDomain().getCodeSource();
			File jarFile = new File(codeSource.getLocation().toURI().getPath());
			path = jarFile.getParentFile().getPath();
		} catch (URISyntaxException e) {
			ErrorHandler.printInternalError();
		}
		parser = new XMLParser();
		creator = new XMLCreator();
	}

	public void deleteDatabase(String databaseName)
			throws DatabaseNotFoundException {
		File database = new File(path + File.separator + databaseName);
		if (!database.exists()) {
			throw new DatabaseNotFoundException(databaseName);
		}
		if (!database.delete()) {
			ErrorHandler.printFailedToDeleteDatabase(databaseName);
		}
	}
	public void deleteTable(String tableName, String databaseName)
			throws TableNotFoundException {
		File tableXML = new File(path + File.separator
				+ databaseName + File.separator + tableName);
		File tableDTD = new File(path + File.separator
				+ databaseName + File.separator + tableName +
				DTD_IDENTIFIER + DTD_EXTENSION);
		if (!tableXML.exists()) {
			throw new TableNotFoundException(tableName);
		}
		if (!tableXML.delete()
		 || !tableDTD.delete()) {
			ErrorHandler.printInternalError();
		}
	}
	public Database loadDatabase(String databaseName)
	throws DatabaseNotFoundException, TableAlreadyExistsException,
	ColumnAlreadyExistsException, RepeatedColumnException,
	ColumnListTooLargeException, ColumnNotFoundException,
	ValueListTooLargeException, ValueListTooSmallException,
	TypeMismatchException {
		File database = new File(path + File.separator + databaseName);
		if (!database.exists()) {
			throw new DatabaseNotFoundException(databaseName);
		}
		ArrayList<String> tables = findTables(databaseName);
		Database newDatabase = new Database(databaseName);
		for (String table : tables) {
			newDatabase.addTable(loadTable(databaseName, table));
		}
		return newDatabase;
	}
	public void createDatabase(String databaseName)
			throws DatabaseAlreadyExistsException {
		File database = new File(path + File.separator + databaseName);
		if (database.exists()) {
			throw new DatabaseAlreadyExistsException(databaseName);
		}
		if (!database.mkdir()) {
			ErrorHandler.printInternalError();
		}
	}
	public Database createTemporaryDatabase(String tempName)
			throws DatabaseAlreadyExistsException {
		File database = new File(path + File.separator + tempName);
		if (database.exists()) {
			throw new DatabaseAlreadyExistsException(tempName);
		}
		if (!database.mkdir()) {
			ErrorHandler.printInternalError();
		}
		database.deleteOnExit();
		return new Database(tempName);
	}
	public void createTable(Table table, String databaseName) {
		creator.create(table, databaseName, path + File.separator);
	}
	public Table loadTable(String databaseName, String tableName)
	throws ColumnAlreadyExistsException, RepeatedColumnException,
	ColumnListTooLargeException, ColumnNotFoundException,
	ValueListTooLargeException, ValueListTooSmallException,
	TypeMismatchException {
		return parser.parse(tableName, databaseName, path + File.separator);
	}
	private ArrayList<String> findTables(String databaseName) {
		File database = new File(path + File.separator + databaseName);
		File[] files = database.listFiles();
		ArrayList<String> tables = new ArrayList<>();
		for (int i = 0 ; i < files.length ; i++) {
			String fileName = files[i].getName();
			if (fileName.endsWith(XML_EXTENSION)) {
				tables.add(fileName.replaceAll(XML_EXTENSION, ""));
			}
		}
		return tables;
	}
}
