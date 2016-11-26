package jdbms.sql.data.xml;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.omg.CORBA.ARG_IN;

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

public class XMLCreator {

	/**
	 * {@link Table} to be parsed to XML.
	 */
	private static Table table;
	/**
	 * Table columns.
	 */
	private Map<String, TableColumn> tableData;

	public XMLCreator(Table table) {
		this.table = table;
		tableData = this.table.getColumns();
	}

	public static void main(String[] args)
			throws ColumnAlreadyExistsException,
			RepeatedColumnException,
			ColumnListTooLargeException,
			ColumnNotFoundException {
		try {
			Class.forName("jdbms.sql.datatypes.IntSQLType");
			Class.forName("jdbms.sql.datatypes.VarcharSQLType");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		XMLCreator creator;
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
		}
		creator = new XMLCreator(table);
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

    /**
     * Returns the Indented version of the parsed XML string.
     * @param xml the string to be processed
     * @return the processed string
     */
    private String transform(String xml)
    		throws XMLStreamException, TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        transformerFactory.setAttribute("indent-number", 4);
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        Writer out = new StringWriter();
        transformer.transform(new StreamSource(new StringReader(xml)), new StreamResult(out));
        return out.toString();
    }
}

