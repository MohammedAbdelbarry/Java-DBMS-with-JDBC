package jdbms.sql.file;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.util.ArrayList;

import jdbms.sql.data.Database;
import jdbms.sql.data.Table;
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
import jdbms.sql.file.json.JSONReader;
import jdbms.sql.file.json.JSONWriter;
import jdbms.sql.file.xml.XMLCreator;
import jdbms.sql.file.xml.XMLParser;
import jdbms.sql.parsing.parser.ParserMain;

public class FileHandler {
	private String path;
	private final String schemaExtension;
	private final String fileExtension;
	private final FileReader reader;
	private final FileWriter writer;
	private static final String XML_EXTENSION = ".xml";
	private static final String DTD_IDENTIFIER = "DTD";
	private static final String DTD_EXTENSION = ".dtd";
	private static final String JSON_EXTENSION = ".json";
	private static final String DATA_DIRECTORY = "Data";
	private static final String XML_PROTOCOL = "xmldb";
	private static final String JSON_PROTOCOL = "altdb";
	public FileHandler() {
		try {
			final CodeSource codeSource = ParserMain.class.
					getProtectionDomain().getCodeSource();
			final File jarFile = new File(
					codeSource.getLocation().toURI().getPath());
			path = jarFile.getParentFile().getPath()
					+ File.separator + DATA_DIRECTORY;
		} catch (final URISyntaxException e) {
			throw new RuntimeException();
		}
		reader = new XMLParser();
		writer = new XMLCreator();
		schemaExtension = DTD_IDENTIFIER + DTD_EXTENSION;
		fileExtension = XML_EXTENSION;
	}
	public FileHandler(final String fileType, final String filePath)
			throws FileFormatNotSupportedException {
		path = filePath + File.separator + DATA_DIRECTORY;
		if (fileType.equalsIgnoreCase(XML_PROTOCOL)) {
			reader = new XMLParser();
			writer = new XMLCreator();
			schemaExtension = DTD_IDENTIFIER + DTD_EXTENSION;
			fileExtension = XML_EXTENSION;
		} else if (fileType.equalsIgnoreCase(JSON_PROTOCOL)) {
			reader = new JSONReader();
			writer = new JSONWriter();
			schemaExtension = null;
			fileExtension = JSON_EXTENSION;
		} else {
			throw new FileFormatNotSupportedException(
					fileType);
		}
	}
	public void deleteDatabase(final String databaseName)
			throws DatabaseNotFoundException,
			FailedToDeleteDatabaseException,
			TableNotFoundException, FailedToDeleteTableException {
		final File database = new File(path + File.separator
				+ databaseName.toLowerCase());
		if (!database.exists()) {
			throw new DatabaseNotFoundException(databaseName);
		}
		for (final String table
				: findTables(databaseName.toLowerCase())) {
			deleteTable(table, databaseName);
		}
		database.setWritable(true);
		if (!database.delete()) {
			throw new FailedToDeleteDatabaseException(databaseName);
		}
	}
	public void deleteTable(final String tableName,
			final String databaseName)
					throws TableNotFoundException,
					FailedToDeleteTableException {
		final File tableFile = new File(path + File.separator
				+ databaseName.toLowerCase() + File.separator
				+ tableName.toLowerCase() + fileExtension);

		if (!tableFile.exists()) {
			throw new TableNotFoundException(tableName);
		}
		tableFile.setWritable(true);
		if (!tableFile.delete()) {
			throw new FailedToDeleteTableException(tableName);
		}
		if (schemaExtension != null) {
			final File tableSchema = new File(path + File.separator
					+ databaseName.toLowerCase()
					+ File.separator + tableName.toLowerCase() +
					schemaExtension);
			tableSchema.setWritable(true);
			if (!tableSchema.delete()) {
				throw new FailedToDeleteTableException(tableName);
			}
		}
	}
	public Database loadDatabase(final String databaseName)
			throws DatabaseNotFoundException, TableAlreadyExistsException {
		final File database = new File(path + File.separator + databaseName.toLowerCase());
		if (!database.exists()) {
			throw new DatabaseNotFoundException(databaseName);
		}
		final ArrayList<String> tables = findTables(databaseName.toLowerCase());
		final Database newDatabase = new Database(databaseName);
		for (final String table : tables) {
			newDatabase.addTableName(table);
		}
		return newDatabase;
	}
	public void createDatabase(final String databaseName)
			throws DatabaseAlreadyExistsException {
		final File database = new File(path + File.separator
				+ databaseName.toLowerCase());
		if (database.exists()) {
			throw new DatabaseAlreadyExistsException(databaseName);
		}
		if (!database.mkdirs()) {
			throw new RuntimeException();
		}
	}
	public Database createTemporaryDatabase(final String tempName)
			throws DatabaseAlreadyExistsException {
		final File database = new File(path + File.separator
				+ tempName.toLowerCase());
		if (database.exists()) {
			throw new DatabaseAlreadyExistsException(tempName);
		}
		if (!database.mkdirs()) {
			throw new RuntimeException(database.getAbsolutePath());
		}
		database.deleteOnExit();
		return new Database(tempName);
	}
	public void createTable(final Table table, final String databaseName)
			throws IOException {
		writer.create(table, databaseName.toLowerCase(), path + File.separator);
	}
	public Table loadTable(final String databaseName, final String tableName)
			throws ColumnAlreadyExistsException, RepeatedColumnException,
			ColumnListTooLargeException, ColumnNotFoundException,
			ValueListTooLargeException, ValueListTooSmallException,
			TypeMismatchException, InvalidDateFormatException,
			IOException {
		return reader.parse(tableName.toLowerCase(),
				databaseName.toLowerCase(), path + File.separator);
	}
	private ArrayList<String> findTables(final String databaseName) {
		final File database = new File(path + File.separator + databaseName);
		final File[] files = database.listFiles();
		final ArrayList<String> tables = new ArrayList<>();
		for (int i = 0 ; i < files.length ; i++) {
			final String fileName = files[i].getName();
			if (fileName.endsWith(fileExtension)) {
				tables.add(fileName.replaceAll(fileExtension, ""));
			}
		}
		return tables;
	}
}
