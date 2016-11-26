package jdbms.sql.data.xml;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

import javax.management.monitor.StringMonitor;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import jdbms.sql.data.ColumnIdentifier;
import jdbms.sql.data.Database;
import jdbms.sql.data.Table;
import jdbms.sql.data.TableColumn;
import jdbms.sql.data.TableIdentifier;
import jdbms.sql.exceptions.ColumnAlreadyExistsException;
import jdbms.sql.exceptions.ColumnListTooLargeException;
import jdbms.sql.exceptions.ColumnNotFoundException;
import jdbms.sql.exceptions.RepeatedColumnException;
import jdbms.sql.exceptions.ValueListTooLargeException;
import jdbms.sql.exceptions.ValueListTooSmallException;
import jdbms.sql.parsing.properties.InsertionParameters;

public class XMLCreator2 {

	 /** {@link Table} to be parsed to XML.
	 */
	private static Table table;
	/**
	 * Table columns.
	 */
	private Map<String, TableColumn> tableData;
	private Database database;
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
		table = new Table(identifier);
		InsertionParameters insertParameters = new InsertionParameters();
		ArrayList<String> columnList = new ArrayList<>();
		columnList.add("Grade");
		columnList.add("Name");
		insertParameters.setColumns(columnList);
		ArrayList<ArrayList<String>> values = new ArrayList<>();
		ArrayList<String> row = new ArrayList<>();
		row.add("19");
		row.add("Ahmed");
		values.add(row);
		ArrayList<String> newRw = new ArrayList<>();
		newRw.add("18");
		newRw.add("Mohamed");
		values.add(newRw);
		insertParameters.setValues(values);
		try {
			table.insertRows(insertParameters);
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
		}
		creator = new XMLCreator2(table, null);
		creator.create();

	}
	public XMLCreator2(Table table, Database database) {
		this.table = table;
		tableData = table.getColumns();
		this.database = database;
	}

	public void create() {
				try {
					DocumentBuilderFactory dbFactory =
							DocumentBuilderFactory.newInstance();
					DocumentBuilder dBuilder;
					dBuilder = dbFactory.newDocumentBuilder();
					Document doc = dBuilder.newDocument();
					//root element : Table Name
					Element root = doc.createElement(table.getName());
					doc.appendChild(root);
					buildRows(doc, root);
					 TransformerFactory transformerFactory =
					         TransformerFactory.newInstance();
					 transformerFactory.setAttribute("indent-number", 4);
					 Transformer transformer =
					         transformerFactory.newTransformer();
					 transformer.setOutputProperty(OutputKeys.INDENT, "yes");
					 DOMSource source = new DOMSource(doc);
					 StreamResult consoleResult =
					         new StreamResult(System.out);
					 transformer.transform(source, consoleResult);
				} catch (ParserConfigurationException |
						TransformerException e) {
					e.printStackTrace();
				}
				createDTD();
	}

	private void createDTD() {
		ArrayList<ColumnIdentifier> columns = new ArrayList<>();
		for (String name : tableData.keySet()) {
			columns.add(new ColumnIdentifier(name, tableData.get(name).getColumnDataType()));
		}
		TableIdentifier identifier = new TableIdentifier(table.getName(),columns);
		DTDCreator dtd = new DTDCreator(database, identifier);
		dtd.create();
	}

	private void buildRows(Document doc,Element root) {
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
}
