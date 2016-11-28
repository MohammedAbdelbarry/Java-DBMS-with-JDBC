package jdbms.sql.data.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

import jdbms.sql.data.ColumnIdentifier;
import jdbms.sql.data.Table;
import jdbms.sql.data.TableColumn;
import jdbms.sql.data.TableIdentifier;
import jdbms.sql.errors.ErrorHandler;

public class XMLCreator {
	/**DTD Identifier to be inserted in the DTD file Name.*/
	private static final String DTD_IDENTIFIER = "DTD";
	/**DTD extension to be inserted in the DTD file.*/
	private static final String DTD_EXTENSION = ".dtd";
	/**XML extension to be inserted in the XML file.*/
	private static final String XML_EXTENSION = ".xml";
	/**external resource for the indentation in XML files.*/
	private static final String INDENTATION = "{http://xml.apache.org/xslt}indent-amount";
	/**Number of indentation tabs in XML files as a String.*/
	private static final String INDENT_NUMBER = "4";

	public XMLCreator() {
	}

	/**
	 * Creates an XMl File for the given table
	 *  and stores it in the proper folder path.
	 *  @param table the table for which the XML file will be created
	 *  @param databaseName the name of the database to which the table belongs
	 *  @param path the path to which the XML file will be stored
	 */
	public void create(Table table, String databaseName, String path) {
		try {
			TableIdentifier identifier = table.getTableIdentifier();
			ArrayList<ColumnIdentifier> cols
			= identifier.getColumnsIdentifiers();
			DocumentBuilderFactory dbFactory =
					DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			Element root = doc.createElement(table.getName());
			for (ColumnIdentifier columnIdentifier : cols) {
				root.setAttribute(columnIdentifier.getName(),
						columnIdentifier.getType());
			}
			doc.appendChild(root);
			buildRows(doc, root,
					table.getColumns(), table, table.getColumnNames());
			DOMSource source = new DOMSource(doc);
			File xmlFile = new File(path
	        		+ databaseName + File.separator
	        		+ table.getName().toUpperCase() + XML_EXTENSION);
			StreamResult fileResult =
			        new StreamResult(xmlFile);
			applyTransform(source, fileResult, doc, table.getName());
		} catch (ParserConfigurationException e) {
			ErrorHandler.printInternalError();
		}
		createDTD(table.getColumnNames(), table, databaseName, path);
	}

	/**Creates the DTD file for the
	 * given XML file given the table data.
	 * @param columnNames array of column names
	 * @param table the table for which the DTD will be created
	 * @param database the name of the database
	 * @param path the path of the database directory
	 */	
	private void createDTD(ArrayList<String> columnNames,
			Table table, String database, String path) {
		DTDCreator dtd = new DTDCreator();
		dtd.create(database, columnNames,
				table.getName().toUpperCase(), path);
	}

	/**Extract the rows from the table and insert them
	 * into the XML file.
	 * @param doc the document to which the rows elements will be inserted
	 * @param root the root element to which the rows would be appended
	 * @param tableData columns of the table
	 * @param table the table for which the XML file will be created
	 * @param columnNames array of the column names
	 */
	private void buildRows(Document doc, Element root,
			Map<String, TableColumn> tableData, Table table
			, ArrayList<String> columnNames) {
		for (int i = 0; i < table.getNumberOfRows(); i++) {
			Element row = doc.createElement("row");
			root.appendChild(row);
			for (String key : columnNames) {
				TableColumn current = tableData.get(key.toUpperCase());
				String value = current.get(i).getStringValue();
				Element col = doc.createElement(key);
				col.appendChild(doc.createTextNode(value));
				row.appendChild(col);
			}
		}
	}

	/** 
	 * 	Transforms the XML file from a single line
	 *  String to fully indented file.
	 *  @param source the file source that will be transformed
	 *  @param fileResult a stream of the XML file generated
	 *  @param document the document that contains the XML tree
	 *  @param tableName the table name
	 */
	private void applyTransform(DOMSource source, StreamResult fileResult,
			Document document, String tableName) {
		TransformerFactory transformerFactory =
		        TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(INDENTATION, INDENT_NUMBER);
			DOMImplementation domImpl = document.getImplementation();
			DocumentType doctype = domImpl.createDocumentType("doctype",
				    "Table",
				    tableName + DTD_IDENTIFIER + DTD_EXTENSION);
			transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC,
					doctype.getPublicId());
			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,
					doctype.getSystemId());
			transformer.transform(source, fileResult);
		} catch (TransformerException e) {
			ErrorHandler.printInternalError();
		}
	}
}
