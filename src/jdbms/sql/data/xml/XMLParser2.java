package jdbms.sql.data.xml;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import jdbms.sql.data.ColumnIdentifier;
import jdbms.sql.data.Database;
import jdbms.sql.data.Table;
import jdbms.sql.data.TableIdentifier;
import jdbms.sql.exceptions.ColumnAlreadyExistsException;
import jdbms.sql.exceptions.ColumnListTooLargeException;
import jdbms.sql.exceptions.ColumnNotFoundException;
import jdbms.sql.exceptions.RepeatedColumnException;
import jdbms.sql.exceptions.ValueListTooLargeException;
import jdbms.sql.exceptions.ValueListTooSmallException;
import jdbms.sql.parsing.properties.InsertionParameters;

public class XMLParser2 {

	private TableIdentifier tableIdentifier;
	private Table table;
	private ArrayList<ColumnIdentifier> columnIdentifiers;
	private Set<String> columns;
	private ArrayList<String> columnNames;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public XMLParser2(TableIdentifier tableIdentifier, Database parent) {
		this.tableIdentifier = tableIdentifier;
		String tableName = tableIdentifier.getTableName();
		columnIdentifiers = tableIdentifier.getColumnsIdentifiers();
		columns = new HashSet<>(tableIdentifier.getColumnNames());
		try {
			table = new Table(tableIdentifier);
		} catch (ColumnAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		columnNames = new ArrayList<>();
		initializeTable();
	}

	private void initializeTable() {
		for (ColumnIdentifier col : columnIdentifiers) {
			try {
				table.addTableColumn(col.getName(), col.getType());
			} catch (ColumnAlreadyExistsException e) {
				e.printStackTrace();
				//ErrorHandler.printColumnAlreadyExistsColumn();
			}
			columnNames.add(col.getName());
		}
	}

	public void parse() {
		try {
			File inputFile = new File("input.txt");
			DocumentBuilderFactory dbFactory 
			= DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc;
			doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			String tableName = doc.getDocumentElement().getNodeName();
			 NodeList nList = doc.getElementsByTagName(tableName);
			 ArrayList<ArrayList<String>> values
			 = new ArrayList<>();
			 for (int i = 0; i < nList.getLength(); i++) {
				 Node node = nList.item(i);
				 if (node.getNodeType() == Node.ELEMENT_NODE) {
					 Element element = (Element) node;
					 ArrayList<String> value = new ArrayList<>();
					 for (String column : columnNames) {
						 value.add(((Document) element)
				                  .getElementsByTagName(column)
				                  .item(0)
				                  .getTextContent());
					 }
					 values.add(value);
				 }
			 }
			 InsertionParameters insert = new InsertionParameters();
			 insert.setColumns(columnNames);
			 insert.setValues(values);
			 insert.setTableName(tableName);
			 table.insertRows(insert);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
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
		} catch (ValueListTooLargeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ValueListTooSmallException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
