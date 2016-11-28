package jdbms.sql.data.xml;

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
import jdbms.sql.errors.ErrorHandler;
import jdbms.sql.exceptions.ColumnAlreadyExistsException;
import jdbms.sql.exceptions.ColumnListTooLargeException;
import jdbms.sql.exceptions.ColumnNotFoundException;
import jdbms.sql.exceptions.RepeatedColumnException;
import jdbms.sql.exceptions.TypeMismatchException;
import jdbms.sql.exceptions.ValueListTooLargeException;
import jdbms.sql.exceptions.ValueListTooSmallException;
import jdbms.sql.parsing.properties.InsertionParameters;
import jdbms.sql.parsing.properties.TableCreationParameters;

public class XMLParser {
	private static final String DTD_IDENTIFIER = "DTD";
	private static final String DTD_EXTENSION = ".dtd";
	private static final String XML_EXTENSION = ".xml";
	public XMLParser() {

	}

	/**
	 * gets the Column Names from the DTD file.
	 * @param tablename  the table name
	 * @param databaseName the databaseName
	 * @param path path of the data main Directory
	 * @return returns an ArrayList of column names
	 * */
	private ArrayList<String> getColumnNames(String tablename, String databaseName, String path) {
		DTDParser parser = new DTDParser();
		ArrayList<String> columnNames = parser.parse(new File(path +
				databaseName + File.separator + tablename + DTD_IDENTIFIER
				+ DTD_EXTENSION));
		return columnNames;
	}

	public Table parse(String tableName, String databaseName,
			String path)
		throws ColumnAlreadyExistsException, RepeatedColumnException,
		ColumnListTooLargeException, ColumnNotFoundException,
		ValueListTooLargeException, ValueListTooSmallException,
		TypeMismatchException {
		ArrayList<String> columnNames = getColumnNames(tableName, databaseName, path);
		ArrayList<ColumnIdentifier> columns = new ArrayList<>();
		try {
			File inputFile = new File(path + databaseName + File.separator
					+ tableName + XML_EXTENSION);
			DocumentBuilderFactory dbFactory
			= DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc;
			doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			Element root = doc.getDocumentElement();
			tableName = root.getNodeName();
			NamedNodeMap columnMap = root.getAttributes();
			// loading column Data Types
			for (int i = 0; i < columnNames.size(); i++) {
				Node columnName = columnMap.item(i);
				columns.add(new ColumnIdentifier(columnNames.get(i), columnName.getTextContent()));
			}
			NodeList nList = root.getChildNodes();
			ArrayList<ArrayList<String>> values
			= new ArrayList<>();
			// extracting row values from the given XML file
			extractRows(values, nList, columnNames);
			// creating table based on the values and identifiers supplied
			return createTable(columns, values, tableName, columnNames);
		} catch (IOException | SAXException
				| ParserConfigurationException e) {
			ErrorHandler.printInternalError();
		}
		return null;
	}

	private Table createTable(ArrayList<ColumnIdentifier> columns,
			ArrayList<ArrayList<String>> values, String tableName,
			ArrayList<String> columnNames)
			throws ColumnAlreadyExistsException,
			RepeatedColumnException,
			ColumnListTooLargeException,
			ColumnNotFoundException,
			ValueListTooLargeException,
			ValueListTooSmallException,
			TypeMismatchException {
		TableCreationParameters parameters = new TableCreationParameters();
		parameters.setColumnDefinitions(columns);
		parameters.setTableName(tableName);
		Table table = null;
		table = new Table(parameters);
		InsertionParameters insert = new InsertionParameters();
		insert.setColumns(columnNames);
		insert.setValues(values);
		insert.setTableName(tableName);
		table.insertRows(insert);
		return table;
	}

	private void extractRows(ArrayList<ArrayList<String>> values,
			NodeList nList, ArrayList<String> columnNames) {
		for (int i = 0; i < nList.getLength(); i++) {
			 Node node = nList.item(i);
			 if (node.getNodeType() == Node.ELEMENT_NODE) {
				 Element element = (Element) node;
				 ArrayList<String> value = new ArrayList<>();
				 for (String column : columnNames) {
					 String text = (element)
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
