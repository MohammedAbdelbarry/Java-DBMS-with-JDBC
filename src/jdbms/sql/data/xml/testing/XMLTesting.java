package jdbms.sql.data.xml.testing;

import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import jdbms.sql.data.ColumnIdentifier;
import jdbms.sql.data.Table;
import jdbms.sql.data.TableIdentifier;
import jdbms.sql.data.xml.XMLCreator2;
import jdbms.sql.data.xml.XMLParser2;
import jdbms.sql.parsing.properties.InsertionParameters;

public class XMLTesting {

	XMLCreator2 creator;
	XMLParser2 parser;
	Table table;
	@Before
	public void setUp() throws Exception {
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
		columnList.clear();
		columnList.add("19");
		columnList.add("Ahmed");
		values.add(columnList);
		columnList.clear();
		columnList.add("19");
		columnList.add("Ahmed");
		values.add(columnList);
		insertParameters.setValues(values);
		table.insertRows(insertParameters);
	}

	@Test
	public void test() {
		//creator = new XMLCreator(table);
		//System.out.println(creator.create());
	}

}
