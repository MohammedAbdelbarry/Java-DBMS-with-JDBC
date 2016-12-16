package jdbms.sql.file.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import jdbms.sql.data.ColumnIdentifier;
import jdbms.sql.data.Table;
import jdbms.sql.exceptions.ColumnAlreadyExistsException;
import jdbms.sql.exceptions.ColumnListTooLargeException;
import jdbms.sql.exceptions.ColumnNotFoundException;
import jdbms.sql.exceptions.InvalidDateFormatException;
import jdbms.sql.exceptions.RepeatedColumnException;
import jdbms.sql.exceptions.TypeMismatchException;
import jdbms.sql.exceptions.ValueListTooLargeException;
import jdbms.sql.exceptions.ValueListTooSmallException;
import jdbms.sql.file.TableReader;
import jdbms.sql.parsing.properties.InsertionParameters;
import jdbms.sql.parsing.properties.TableCreationParameters;

public class XMLReader implements TableReader {
    /**
     * DTD Identifier to be inserted in the DTD file Name.
     */
    private static final String DTD_IDENTIFIER = "DTD";
    /**
     * DTD extension to be inserted in the DTD file.
     */
    private static final String DTD_EXTENSION = ".dtd";
    /**
     * XML extension to be inserted in the XML file.
     */
    private static final String XML_EXTENSION = ".xml";

    public XMLReader() {

    }

    /**
     * gets the Column Names from the DTD file.
     * @param tablename    the table name
     * @param databaseName the databaseName
     * @param path         path of the data main Directory
     * @return returns an ArrayList of column names
     */
    private ArrayList<String> getColumnNames(final String tablename,
                                             final String databaseName, final
                                             String path) {
        final DTDReader parser = new DTDReader();
        final ArrayList<String> columnNames = parser.parse(new File(path +
                databaseName + File.separator + tablename + DTD_IDENTIFIER
                + DTD_EXTENSION));
        return columnNames;
    }

    /**
     * Parses the XML file with the provided table name and database name.
     * @param tableName    the name of the table
     * @param databaseName the name of the database
     * @return table constructed using the parsed XML file
     * @throws InvalidDateFormatException
     */
    @Override
    public Table read(String tableName, final String databaseName,
                      final String path)
            throws ColumnAlreadyExistsException, RepeatedColumnException,
            ColumnListTooLargeException, ColumnNotFoundException,
            ValueListTooLargeException, ValueListTooSmallException,
            TypeMismatchException, InvalidDateFormatException {
        final ArrayList<String> columnNames
                = getColumnNames(tableName, databaseName, path);
        final ArrayList<ColumnIdentifier> columns = new ArrayList<>();
        try {
            final File inputFile = new File(path + databaseName + File.separator
                    + tableName + XML_EXTENSION);
            final DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            final Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            final Element root = doc.getDocumentElement();
            tableName = root.getNodeName();
            final NamedNodeMap columnMap = root.getAttributes();
            // loading column Data Types
            for (int i = 0; i < columnNames.size(); i++) {
                final Node columnName = columnMap.getNamedItem(
                        columnNames.get(i));
                columns.add(new ColumnIdentifier(columnNames.get(i),
                        columnName.getTextContent()));
            }
            final NodeList nList = root.getChildNodes();
            final ArrayList<ArrayList<String>> values
                    = new ArrayList<>();
            // extracting row values from the given XML file
            extractRows(values, nList, columnNames);
            // creating table based on the values and identifiers supplied
            return createTable(columns, values, tableName, columnNames);
        } catch (IOException | SAXException
                | ParserConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates the table after the parsing process has completed successfully.
     * @param columns     array of column identifiers
     * @param values      array of each column values
     * @param tableName   name of the table to be created
     * @param columnNames array of the column names
     * @return table the new created table
     * @throws InvalidDateFormatException
     */
    private Table createTable(final ArrayList<ColumnIdentifier> columns,
                              final ArrayList<ArrayList<String>> values,
							  final String tableName,
                              final ArrayList<String> columnNames)
            throws ColumnAlreadyExistsException,
            RepeatedColumnException,
            ColumnListTooLargeException,
            ColumnNotFoundException,
            ValueListTooLargeException,
            ValueListTooSmallException,
            TypeMismatchException,
            InvalidDateFormatException {
        final TableCreationParameters parameters = new
				TableCreationParameters();
        parameters.setColumnDefinitions(columns);
        parameters.setTableName(tableName);
        Table table;
        table = new Table(parameters);
        final InsertionParameters insert = new InsertionParameters();
        insert.setColumns(columnNames);
        insert.setValues(values);
        insert.setTableName(tableName);
        table.insertRows(insert);
        return table;
    }

    /**
     * Extracts the rows out of the XML file.
     * @param values      array of each column values
     * @param nList       array of the rows stored in the XML file
     * @param columnNames array of column names
     */
    private void extractRows(final ArrayList<ArrayList<String>> values,
                             final NodeList nList, final ArrayList<String> columnNames) {
        for (int i = 0; i < nList.getLength(); i++) {
            final Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                final Element element = (Element) node;
                final ArrayList<String> value = new ArrayList<>();
                for (final String column : columnNames) {
                    final String text = (element)
                            .getElementsByTagName(column)
                            .item(0)
                            .getTextContent();
                    value.add(text);
                }
                values.add(value);
            }
        }
    }
}
