package jdbms.sql.data.xml;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import jdbms.sql.data.Table;
import jdbms.sql.data.TableColumn;
import jdbms.sql.parsing.statements.CreateDatabaseStatement;

public class XMLCreator {

	private Map<String, TableColumn> tableData;

	public XMLCreator(Map<String, TableColumn> tableData) {
		this.tableData = tableData;
	}

	public XMLCreator () {
		tableData = new HashMap<>();
	}
    
	public static void main(String[] args) {
		Table newTable = new Table("Students");
		newTable.addTableColumn("Grade", "String");
		newTable.addTableColumn("Name", "String");
		ArrayList<String> columnNames = new ArrayList<>();
		columnNames.add("Grade");
		columnNames.add("Name");
		ArrayList<String> values = new ArrayList<>();
		values.add("90");
		values.add("Ahmed");
		newTable.insertRow(columnNames, values);
		
		XMLCreator creattor = new XMLCreator(newTable.getColumns());
		creattor.create();
   }

    public void create() {
    	StringWriter stringWriter = new StringWriter();

        XMLOutputFactory xMLOutputFactory = XMLOutputFactory.newInstance();	
        XMLStreamWriter xMLStreamWriter;
		try {
			xMLStreamWriter = xMLOutputFactory.createXMLStreamWriter(stringWriter);
			xMLStreamWriter.writeStartDocument();
			xMLStreamWriter.writeStartElement("table");
			String sampleKey = null;
			for (String key : tableData.keySet()) {
				sampleKey = key;
				break;
			}
			TableColumn sample = tableData.get(sampleKey);
			for (int i = 0; i < sample.getSize(); i++) {
				xMLStreamWriter.writeStartElement("row");
				for (String key : tableData.keySet()) {
					TableColumn current = tableData.get(key);
					String value = current.get(i).getStringValue();
					xMLStreamWriter.writeAttribute(key, value);
				}
				xMLStreamWriter.writeEndElement();
			}
			xMLStreamWriter.writeEndElement();
			xMLStreamWriter.writeEndDocument();
			xMLStreamWriter.flush();
	        xMLStreamWriter.close();

	        String xmlString = stringWriter.getBuffer().toString();

	        stringWriter.close();

	        System.out.println(xmlString);

		} catch (XMLStreamException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  
    }
}

