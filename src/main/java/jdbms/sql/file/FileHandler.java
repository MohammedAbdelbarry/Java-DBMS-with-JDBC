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
import jdbms.sql.file.protobuff.ProtocolBufferReader;
import jdbms.sql.file.protobuff.ProtocolBufferWriter;
import jdbms.sql.file.xml.XMLReader;
import jdbms.sql.file.xml.XMLWriter;
import jdbms.sql.parsing.parser.ParserMain;

/**
 * The class that handles IO operations
 * for the table and the database
 * @author Mohammed Abdelbarry
 */
public class FileHandler {
    /**
     * The path of the user's data.
     **/
    private String path;
    /**
     * The extension of the schema file
     * for the table.
     */
    private final String schemaExtension;
    /**
     * The extension of the table file.
     */
    private final String fileExtension;
    /**
     * The {@link TableReader} responsible for
     * reading a table from the disk
     */
    private final TableReader reader;
    /**
     * The {@link TableWriter} responsible for
     * writing a table to the disk
     */
    private final TableWriter writer;
    /**
     * The extension of an XML file.
     */
    private static final String XML_EXTENSION = ".xml";
    /**
     * The Identifier of a DTD file.
     */
    private static final String DTD_IDENTIFIER = "DTD";
    /**
     * The extension of a DTD schema file.
     */
    private static final String DTD_EXTENSION = ".dtd";
    /**
     * The extension of a json file.
     */
    private static final String JSON_EXTENSION = ".json";
    /**
     * The extension of a Protocol Buffer file.
     */
    private static final String PROTOCOL_BUFFER_EXTENSION
            = ".protobuff";
    /**
     * The user's Data Directory name.
     */
    private static final String DATA_DIRECTORY = "Data";
    /**
     * The protocol for the xml file.
     */
    private static final String XML_PROTOCOL = "xmldb";
    /**
     * The protocol for the json file.
     */
    private static final String JSON_PROTOCOL = "altdb";
    /**
     * The protocol for the Protocol Buffer file.
     */
    private static final String PROTOBUFF_PROTOCOL = "pbdb";

    /**
     * Constructs a new FileHandler.
     */
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
        reader = new XMLReader();
        writer = new XMLWriter();
        schemaExtension = DTD_IDENTIFIER + DTD_EXTENSION;
        fileExtension = XML_EXTENSION;
    }

    /**
     * Constructs a new FileHandler.
     * @param fileType The type of the back-end parser to be used
     * @param filePath The path of the user data
     * @throws FileFormatNotSupportedException If the type of the back-end
     *                                         parser was not supported
     */
    public FileHandler(final String fileType, final String filePath)
            throws FileFormatNotSupportedException {
        path = filePath + File.separator + DATA_DIRECTORY;
        if (fileType.equalsIgnoreCase(XML_PROTOCOL)) {
            reader = new XMLReader();
            writer = new XMLWriter();
            schemaExtension = DTD_IDENTIFIER + DTD_EXTENSION;
            fileExtension = XML_EXTENSION;
        } else if (fileType.equalsIgnoreCase(JSON_PROTOCOL)) {
            reader = new JSONReader();
            writer = new JSONWriter();
            schemaExtension = null;
            fileExtension = JSON_EXTENSION;
        } else if (fileType.equalsIgnoreCase(PROTOBUFF_PROTOCOL)) {
            reader = new ProtocolBufferReader();
            writer = new ProtocolBufferWriter();
            schemaExtension = null;
            fileExtension = PROTOCOL_BUFFER_EXTENSION;
        } else {
            throw new FileFormatNotSupportedException(
                    fileType);
        }
    }

    /**
     * Deletes a database from the disk.
     * @param databaseName The name of the database
     * @throws DatabaseNotFoundException       If the database was not found on
     *                                         the disk
     * @throws FailedToDeleteDatabaseException If the database couldn't be
     *                                         deleted
     * @throws TableNotFoundException          If a table in the database
     *                                         couldn't be found
     * @throws FailedToDeleteTableException    If a table in the database
     *                                         couldn't be deleted
     */
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

    /**
     * Deletes a table from the disk.
     * @param tableName    The name of the table
     * @param databaseName The name of the database
     * @throws TableNotFoundException       If the table to be deleted couldn't
     *                                      be found
     * @throws FailedToDeleteTableException If the table couldn't be deleted
     */
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

    /**
     * Loads a database from the disk
     * and returns it.
     * @param databaseName The name of the database to be loaded
     * @return The loaded {@link Database}
     * @throws DatabaseNotFoundException   If the database to be loaded was not
     *                                     found
     * @throws TableAlreadyExistsException If the database has repeated tables
     */
    public Database loadDatabase(final String databaseName)
            throws DatabaseNotFoundException, TableAlreadyExistsException {
        final File database = new File(path + File.separator + databaseName
                .toLowerCase());
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

    /**
     * Creates a database on the disk.
     * @param databaseName the database name
     * @throws DatabaseAlreadyExistsException If the database already exists on
     *                                        the disk
     */
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

    /**
     * Creates a temporary database that will
     * be deleted after the program terminates.
     * @param tempName The name of the temporary database
     * @return The created {@link Database}
     * @throws DatabaseAlreadyExistsException If a database with the same name
     *                                        already exists
     */
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

    /**
     * Writes a table to the disk.
     * @param table        The {@link Table} to be written
     * @param databaseName The name of the database
     * @throws IOException If the table could not be written
     */
    public void writeTable(final Table table, final String databaseName)
            throws IOException {
        writer.write(table, databaseName.toLowerCase(), path + File.separator);
    }

    /**
     * Reads a table from the disk.
     * @param databaseName The name of the database
     * @param tableName    The name of the table
     * @return The loaded {@link Table}
     * @throws ColumnAlreadyExistsException If a column already exists in the
     *                                      table
     * @throws RepeatedColumnException      If a column was repeated while
     *                                      inserting to the table
     * @throws ColumnListTooLargeException  If the column list was too large
     *                                      while inserting to the table
     * @throws ColumnNotFoundException      If a column could not be found
     * while
     *                                      inserting to the table
     * @throws ValueListTooLargeException   If the value list was too large
     *                                      while inserting to the table
     * @throws ValueListTooSmallException   If the value list was too small
     *                                      while inserting to the table
     * @throws TypeMismatchException        If the parser tried inserting a
     *                                      value into a column of the wrong
     *                                      type
     * @throws InvalidDateFormatException   If the values contained an invalid
     *                                      date
     * @throws IOException                  If the parser couldn't read the
     *                                      table from the disk
     */
    public Table readTable(final String databaseName, final String tableName)
            throws ColumnAlreadyExistsException, RepeatedColumnException,
            ColumnListTooLargeException, ColumnNotFoundException,
            ValueListTooLargeException, ValueListTooSmallException,
            TypeMismatchException, InvalidDateFormatException,
            IOException {
        return reader.read(tableName.toLowerCase(),
                databaseName.toLowerCase(), path + File.separator);
    }

    /**
     * Finds and returns the names of all
     * the tables in a database.
     * @param databaseName the name of the database
     * @return The names of the tables in the database
     */
    private ArrayList<String> findTables(final String databaseName) {
        final File database = new File(path + File.separator + databaseName);
        final File[] files = database.listFiles();
        final ArrayList<String> tables = new ArrayList<>();
        if (files == null) {
            return tables;
        }
        for (int i = 0; i < files.length; i++) {
            final String fileName = files[i].getName();
            if (fileName.endsWith(fileExtension)) {
                tables.add(fileName.replaceAll(fileExtension, ""));
            }
        }
        return tables;
    }
}
