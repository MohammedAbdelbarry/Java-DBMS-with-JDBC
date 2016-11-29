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
	private static final String DATA_DIRECTORY = "Data";
	public FileHandler() {
		try {
			final CodeSource codeSource = TestingMain.class.
					getProtectionDomain().getCodeSource();
			final File jarFile = new File(
					codeSource.getLocation().toURI().getPath());
			path = jarFile.getParentFile().getPath()
					+ File.separator + DATA_DIRECTORY;
		} catch (final URISyntaxException e) {
			ErrorHandler.printInternalError();
		}
		parser = new XMLParser();
		creator = new XMLCreator();
	}

	public void deleteDatabase(final String databaseName)
			throws DatabaseNotFoundException {
		final File database = new File(path + File.separator + databaseName);
		if (!database.exists()) {
			throw new DatabaseNotFoundException(databaseName);
		}
		if (!database.delete()) {
			ErrorHandler.printFailedToDeleteDatabase(databaseName);
		}
	}
	public void deleteTable(final String tableName, final String databaseName)
			throws TableNotFoundException {
		final File tableXML = new File(path + File.separator
				+ databaseName.toUpperCase() + File.separator
				+ tableName.toUpperCase() + XML_EXTENSION);
		final File tableDTD = new File(path + File.separator
				+ databaseName.toUpperCase()
				+ File.separator + tableName.toUpperCase() +
				DTD_IDENTIFIER + DTD_EXTENSION);
		if (!tableXML.exists()) {
			throw new TableNotFoundException(tableName);
		}
		if (!tableXML.delete()
				|| !tableDTD.delete()) {
			ErrorHandler.printInternalError();
		}
	}
	public Database loadDatabase(final String databaseName)
			throws DatabaseNotFoundException, TableAlreadyExistsException,
			ColumnAlreadyExistsException, RepeatedColumnException,
			ColumnListTooLargeException, ColumnNotFoundException,
			ValueListTooLargeException, ValueListTooSmallException,
			TypeMismatchException {
		final File database = new File(path + File.separator + databaseName);
		if (!database.exists()) {
			throw new DatabaseNotFoundException(databaseName);
		}
		final ArrayList<String> tables = findTables(databaseName);
		final Database newDatabase = new Database(databaseName);
		for (final String table : tables) {
			newDatabase.addTable(loadTable(databaseName, table));
		}
		return newDatabase;
	}
	public void createDatabase(final String databaseName)
			throws DatabaseAlreadyExistsException {
		final File database = new File(path + File.separator + databaseName);
		if (database.exists()) {
			throw new DatabaseAlreadyExistsException(databaseName);
		}
		if (!database.mkdirs()) {
			ErrorHandler.printInternalError();
		}
	}
	public Database createTemporaryDatabase(final String tempName)
			throws DatabaseAlreadyExistsException {
		final File database = new File(path + File.separator + tempName);
		if (database.exists()) {
			throw new DatabaseAlreadyExistsException(tempName);
		}
		if (!database.mkdirs()) {
			ErrorHandler.printInternalError();
		}
		database.deleteOnExit();
		return new Database(tempName);
	}
	public void createTable(final Table table, final String databaseName) {
		creator.create(table, databaseName, path + File.separator);
	}
	public Table loadTable(final String databaseName, final String tableName)
			throws ColumnAlreadyExistsException, RepeatedColumnException,
			ColumnListTooLargeException, ColumnNotFoundException,
			ValueListTooLargeException, ValueListTooSmallException,
			TypeMismatchException {
		return parser.parse(tableName, databaseName, path + File.separator);
	}
	private ArrayList<String> findTables(final String databaseName) {
		final File database = new File(path + File.separator + databaseName);
		final File[] files = database.listFiles();
		final ArrayList<String> tables = new ArrayList<>();
		for (int i = 0 ; i < files.length ; i++) {
			final String fileName = files[i].getName();
			if (fileName.endsWith(XML_EXTENSION)) {
				tables.add(fileName.replaceAll(XML_EXTENSION, ""));
			}
		}
		return tables;
	}
}
