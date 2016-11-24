package jdbms.sql.data.xml;

import jdbms.sql.data.ColumnIdentifier;
import jdbms.sql.data.Table;
import jdbms.sql.data.TableIdentifier;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.print.DocFlavor.STRING;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class XMLParser {

	private TableIdentifier tableIdentifier;
	private Table table;
	private ArrayList<ColumnIdentifier> columnIdentifiers;
	private Set<String> columns;
	private ArrayList<String> columnNames;

	public XMLParser(TableIdentifier tableIdentifier) {
		this.tableIdentifier = tableIdentifier;
		String tableName = tableIdentifier.getTableName();
		columnIdentifiers = tableIdentifier.getColumnsIdentifiers();
		columns = new HashSet<>(tableIdentifier.getColumnNames());
		table = new Table(tableName);
		columnNames = new ArrayList<>();
		initializeTable();
	}

	public static void main(String[] args) {
		try {
			Class.forName("jdbms.sql.datatypes.IntSQLType");
			Class.forName("jdbms.sql.datatypes.VarcharSQLType");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		ArrayList<ColumnIdentifier> columns = new ArrayList<>();
		columns.add(new ColumnIdentifier("Name", "VARCHAR"));
		columns.add(new ColumnIdentifier("ID", "INTEGER"));
		TableIdentifier identifier = new TableIdentifier("Students", columns);
		XMLParser parser = new XMLParser(identifier);
		XMLInputFactory factory = XMLInputFactory.newInstance();
		try {
			XMLEventReader eventReader = factory.createXMLEventReader(new FileReader("input.txt"));
			Table result = parser.parse(eventReader);
			XMLCreator creator = new XMLCreator(result);
			System.out.println(creator.create());
		} catch (FileNotFoundException | XMLStreamException e) {
			e.printStackTrace();
		}
	}

	private void initializeTable() {
		for (ColumnIdentifier col : columnIdentifiers) {
			table.addTableColumn(col.getName(), col.getType());
			columnNames.add(col.getName());
		}
	}

	public Table parse(XMLEventReader eventReader) {
		try {
			ArrayList<String> values = new ArrayList<>();
			boolean valueAvailable = false;
			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();
				switch (event.getEventType()) {
				case XMLStreamConstants.START_ELEMENT:
					StartElement startElement = event.asStartElement();
					String colName = startElement.getName().getLocalPart();
					if (columns.contains(colName)) {
						valueAvailable = true;
					}
					break;
				case XMLStreamConstants.CHARACTERS:
					Characters characters = event.asCharacters();
					if (valueAvailable) {
						if (characters.getData() != null || !characters.getData().isEmpty()) {
							values.add(characters.getData());
						}
					}
					break;
				case XMLStreamConstants.END_ELEMENT:
					EndElement endElement = event.asEndElement();
					String name = endElement.getName().getLocalPart();
					if (name.equals("row")) {
						table.insertRow(columnNames, values);
						valueAvailable = false;
						values.clear();
					} else if (columns.contains(name)) {
						valueAvailable = false;
					}
					break;
				}
			}
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return table;
	}
}
