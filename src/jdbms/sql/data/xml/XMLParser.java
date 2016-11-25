package jdbms.sql.data.xml;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import jdbms.sql.data.ColumnIdentifier;
import jdbms.sql.data.Database;
import jdbms.sql.data.Table;
import jdbms.sql.data.TableIdentifier;
import jdbms.sql.exceptions.ColumnAlreadyExistsException;
import jdbms.sql.exceptions.ColumnListTooLargeException;
import jdbms.sql.exceptions.ColumnNotFoundException;
import jdbms.sql.exceptions.RepeatedColumnException;

public class XMLParser {

	/**.*/
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
		Database parent = tableIdentifier.getParent();
		table = new Table(tableName, parent);
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
		Database parent = new Database("School");
		TableIdentifier identifier = new TableIdentifier("Students", columns, parent);
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
			try {
				table.addTableColumn(col.getName(), col.getType());
			} catch (ColumnAlreadyExistsException e) {
				e.printStackTrace();
				//ErrorHandler.printColumnAlreadyExistsColumn();
			}
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
					valueAvailable = handleStartElement(values, event, valueAvailable);
					break;
				case XMLStreamConstants.CHARACTERS:
					handleCharacters(values, event, valueAvailable);
					break;
				case XMLStreamConstants.END_ELEMENT:
					valueAvailable = handleEndElement(values, event, valueAvailable);
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

	private boolean handleStartElement(ArrayList<String> values,
			XMLEvent event, boolean valueAvailable) {
		StartElement startElement = event.asStartElement();
		String colName = startElement.getName().getLocalPart();
		if (columns.contains(colName)) {
			valueAvailable = true;
		}
		return valueAvailable;
	}

	private boolean handleEndElement(ArrayList<String> values,
			XMLEvent event, boolean valueAvailable) {
		EndElement endElement = event.asEndElement();
		String name = endElement.getName().getLocalPart();
		if (name.equals("row")) {
			try {
				table.insertRow(columnNames, values);
			} catch (RepeatedColumnException e) {
				// ErrorHandler.printRepeatedColumnError()
				e.printStackTrace();
			} catch (ColumnListTooLargeException e) {
				// ErrorHandler.printColumnLargeColumnListError()
				e.printStackTrace();
			} catch (ColumnNotFoundException e) {
				// ErrorHandler.printColumnNotFoundError()
				e.printStackTrace();
			}
			valueAvailable = false;
			values.clear();
		} else if (columns.contains(name)) {
			valueAvailable = false;
		}
		return valueAvailable;
	}

	private void handleCharacters(ArrayList<String> values,
			XMLEvent event, boolean valueAvailable) {
		Characters characters = event.asCharacters();
		if (valueAvailable) {
			if (characters.getData() != null || !characters.getData().isEmpty()) {
				values.add(characters.getData());
			}
		}
	}
}
