package jdbms.sql.data.xml;

import java.io.File;
import java.security.KeyStore.Entry.Attribute;
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
import org.w3c.dom.NamedNodeMap;

import jdbms.sql.data.ColumnIdentifier;
import jdbms.sql.data.Table;
import jdbms.sql.data.TableColumn;
import jdbms.sql.data.TableIdentifier;
import jdbms.sql.exceptions.ColumnAlreadyExistsException;
import jdbms.sql.exceptions.ColumnListTooLargeException;
import jdbms.sql.exceptions.ColumnNotFoundException;
import jdbms.sql.exceptions.RepeatedColumnException;
import jdbms.sql.exceptions.TypeMismatchException;
import jdbms.sql.exceptions.ValueListTooLargeException;
import jdbms.sql.exceptions.ValueListTooSmallException;
import jdbms.sql.parsing.properties.InsertionParameters;

public class XMLCreator2 {
	private static final String DTD_IDENTIFIER = "DTD";
	private static final String DTD_EXTENSION = ".dtd";
	public static void main(String[] args) throws ColumnAlreadyExistsException {
		try {
			Class.forName("jdbms.sql.datatypes.IntSQLType");
			Class.forName("jdbms.sql.datatypes.VarcharSQLType");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		XMLCreator2 creator;
		ArrayList<ColumnIdentifier> columns = new ArrayList<>();
		columns.add(new ColumnIdentifier("Grade", "INTEGER"));
		columns.add(new ColumnIdentifier("Name", "VARCHAR"));
		TableIdentifier identifier = new TableIdentifier("Students", columns);
		Table table = new Table(identifier);
		InsertionParameters insertParameters = new InsertionParameters();
		ArrayList<String> columnList = new ArrayList<>();
		columnList.add("Grade");
		columnList.add("Name");
		insertParameters.setColumns(columnList);
		ArrayList<ArrayList<String>> values = new ArrayList<>();
		ArrayList<String> row = new ArrayList<>();
		row.add("19");
		row.add("'Ahmed'");
		values.add(row);
		ArrayList<String> newRw = new ArrayList<>();
		newRw.add("18");
		newRw.add("'Mohamed'");
		values.add(newRw);
		insertParameters.setValues(values);
		try {
			table.insertRows(insertParameters);
			creator = new XMLCreator2();
			creator.create(table, "test", " ");
		} catch (ValueListTooLargeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ValueListTooSmallException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RepeatedColumnException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ColumnListTooLargeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ColumnNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TypeMismatchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public XMLCreator2() {
	}

	public void create(Table table, String databaseName, String path) {
		try {
			TableIdentifier identifier = table.getTableIdentifier();
			ArrayList<ColumnIdentifier> cols = identifier.getColumnsIdentifiers();
			DocumentBuilderFactory dbFactory =
					DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			//root element : Table Name
			Element root = doc.createElement(table.getName());
			for (ColumnIdentifier columnIdentifier : cols) {
				root.setAttribute(columnIdentifier.getName(), columnIdentifier.getType());
			}
			doc.appendChild(root);
			buildRows(doc, root, table.getColumns(), table);

			DOMSource source = new DOMSource(doc);
			File xmlPath = new File(path + databaseName + File.separator);
			File xmlFile = new File(path
	        		+ databaseName + File.separator
	        		+ table.getName() + ".xml");
			xmlPath.mkdirs();
			StreamResult fileResult =
			        new StreamResult(xmlFile);
			applyTransform(source, fileResult, doc, table.getName());
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		createDTD(table.getColumns(), table, databaseName, path);
	}

	private void createDTD(Map<String, TableColumn> tableData,
			Table table, String database, String path) {
		ArrayList<ColumnIdentifier> columns = new ArrayList<>();
		for (String name : tableData.keySet()) {
			columns.add(new ColumnIdentifier(name, tableData.get(name).getColumnDataType()));
		}
		TableIdentifier identifier = new TableIdentifier(table.getName(),columns);
		DTDCreator dtd = new DTDCreator();
		dtd.create(database, identifier, path);
	}

	private void buildRows(Document doc, Element root,
			Map<String, TableColumn> tableData, Table table) {
		for (int i = 0; i < table.getNumberOfRows(); i++) {
			Element row = doc.createElement("row");
			root.appendChild(row);
			for (String key : table.getColumns().keySet()) {
				TableColumn current = tableData.get(key);
				String value = current.get(i).getStringValue();
				Element col = doc.createElement(key);
				col.appendChild(doc.createTextNode(value));
				row.appendChild(col);
			}
		}
	}
	private void applyTransform(DOMSource source, StreamResult fileResult,
			Document document, String tableName) {
		TransformerFactory transformerFactory =
		        TransformerFactory.newInstance();
		transformerFactory.setAttribute("indent-number", 4);
		Transformer transformer;
		try {
			transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMImplementation domImpl = document.getImplementation();
			DocumentType doctype = domImpl.createDocumentType("doctype",
				    "Table",
				    tableName + DTD_IDENTIFIER + DTD_EXTENSION);
			transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, doctype.getPublicId());
			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());
			transformer.transform(source, fileResult);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
