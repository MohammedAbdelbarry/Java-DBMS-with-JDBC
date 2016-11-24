package jdbms.sql.data.xml;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import jdbms.sql.data.Table;
import jdbms.sql.data.TableColumn;
import jdbms.sql.parsing.statements.CreateDatabaseStatement;

public class XMLCreator {

	/**
	 * {@link Table} to be parsed to XML.
	 */
	private Table table;
	/**
	 * Table columns.
	 */
	private Map<String, TableColumn> tableData;
	public XMLCreator(Table table) {
		this.table = table;
		tableData = this.table.getColumns();
	}

	public static void main(String[] args) {
		try {
			Class.forName("jdbms.sql.datatypes.IntSQLType");
			Class.forName("jdbms.sql.datatypes.VarcharSQLType");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Table newTable = new Table("Students");
		newTable.addTableColumn("Grade", "INTEGER");
		newTable.addTableColumn("Name", "TEXT");
		ArrayList<String> columnNames = new ArrayList<>();
		columnNames.add("Grade");
		columnNames.add("Name");
		ArrayList<String> values = new ArrayList<>();
		values.add("90");
		values.add("Ahmed");
		newTable.insertRow(columnNames, values);
		values.clear();
		values.add("250");
		values.add("HAMADA");
		newTable.insertRow(columnNames, values);
		XMLCreator creator = new XMLCreator(newTable);
		System.out.println(creator.create());;
   }

	/**
	 * Generates the XML String.
	 * @return xmlString the XML parsed string
	 */
    public String create() {
    	StringWriter stringWriter = new StringWriter();
        XMLOutputFactory xMLOutputFactory
        = XMLOutputFactory.newInstance();	
        XMLStreamWriter xMLStreamWriter;
		try {
			xMLStreamWriter
			= xMLOutputFactory.createXMLStreamWriter(stringWriter);
			xMLStreamWriter.writeStartDocument();
			xMLStreamWriter.writeStartElement(table.getName());
			buildRows(xMLStreamWriter);
			xMLStreamWriter.writeEndElement();
			xMLStreamWriter.writeEndDocument();
			xMLStreamWriter.flush();
	        xMLStreamWriter.close();
	        String xmlString
	        = stringWriter.getBuffer().toString();
	        stringWriter.close();
	        return transform(xmlString);
		} catch (XMLStreamException | IOException | TransformerException e) {
			e.printStackTrace();
			return null;
		}
    }

    /**
     * Builds the interior of the table rows.
     * @param xmlStreaWriter : the XML writer stream 
     */
    private void buildRows(XMLStreamWriter
    		xMLStreamWriter) throws XMLStreamException {
    	for (int i = 0; i < table.getNumberOfRows(); i++) {
			xMLStreamWriter.writeStartElement("row");
			for (String key : tableData.keySet()) {
				TableColumn current = tableData.get(key);
				String value = current.get(i).getStringValue();
				xMLStreamWriter.writeStartElement(key);
				xMLStreamWriter.writeCharacters(value);
				xMLStreamWriter.writeEndElement();
			}
			xMLStreamWriter.writeEndElement();
		}
    }

    private String transform(String xml) throws XMLStreamException, TransformerException
    {
        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        Writer out = new StringWriter();
        t.transform(new StreamSource(new StringReader(xml)), new StreamResult(out));
        
        return out.toString();
    }
    
}

