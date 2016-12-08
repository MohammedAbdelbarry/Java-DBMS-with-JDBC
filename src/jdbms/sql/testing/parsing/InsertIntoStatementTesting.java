package jdbms.sql.testing.parsing;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import jdbms.sql.parsing.parser.StringNormalizer;
import jdbms.sql.parsing.statements.InitialStatement;
import jdbms.sql.parsing.statements.InsertIntoStatement;
import jdbms.sql.util.HelperClass;

public class InsertIntoStatementTesting {

	private StringNormalizer normalizer;
	private InitialStatement insertInto;

	@Before
	public void executedBeforeEach() {
		normalizer = new StringNormalizer();
		insertInto = new InsertIntoStatement();
		HelperClass.registerInitialStatements();
	}

	@Test
	public void testInsertInto() {
		String sqlCommand = "INSERT into my_TABLE(A_B,C_D) VALUES (\"x\",'y');";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		final String name = "my_TABLE";
		final ArrayList<String> cols = new ArrayList<>();
		cols.add("A_B");
		cols.add("C_D");
		final ArrayList<ArrayList<String>> vals = new ArrayList<>();
		final ArrayList<String> temp = new ArrayList<>();
		temp.add("\"x\"");
		temp.add("'y'");
		vals.add(temp);
		assertEquals(insertInto.interpret(sqlCommand), true);
		assertEquals(insertInto.getParameters().getColumns(), cols);
		assertEquals(insertInto.getParameters().getTableName(), name);
		assertEquals(insertInto.getParameters().getValues(), vals);
	}

	@Test
	public void testInsertIntoGluedExpression() {
		String sqlCommand = "INSERT INTO Customers(CustomerName, ContactName, Address, City, PostalCode, Country)"
				+ "VALUES(12345,'Tom B. Erichsen','Skagen 21','Stavanger',4006,'Norway');";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		final String name = "Customers";
		final ArrayList<String> cols = new ArrayList<>();
		cols.add("CustomerName");
		cols.add("ContactName");
		cols.add("Address");
		cols.add("City");
		cols.add("PostalCode");
		cols.add("Country");
		final ArrayList<ArrayList<String>> vals = new ArrayList<>();
		final ArrayList<String> temp = new ArrayList<>();
		temp.add("12345");
		temp.add("'Tom B. Erichsen'");
		temp.add("'Skagen 21'");
		temp.add("'Stavanger'");
		temp.add("4006");
		temp.add("'Norway'");
		vals.add(temp);
		assertEquals(insertInto.interpret(sqlCommand), true);
		assertEquals(insertInto.getParameters().getColumns(), cols);
		assertEquals(insertInto.getParameters().getTableName(), name);
		assertEquals(insertInto.getParameters().getValues(), vals);
	}

	@Test
	public void testInsertIntoWithoutColumns() {
		String sqlCommand = "INSERT INTO Customers   VALuES   "
				+ "(    12345  ,   'Tom B. Erichsen',   'Skagen 21','Stavanger',   4006,   'Norway PLEB'   )   ;   ";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		final String name = "Customers";
		final ArrayList<ArrayList<String>> vals = new ArrayList<>();
		final ArrayList<String> temp = new ArrayList<>();
		temp.add("12345");
		temp.add("'Tom B. Erichsen'");
		temp.add("'Skagen 21'");
		temp.add("'Stavanger'");
		temp.add("4006");
		temp.add("'Norway PLEB'");
		vals.add(temp);
		assertEquals(insertInto.interpret(sqlCommand), true);
		assertEquals(insertInto.getParameters().getTableName(), name);
		assertEquals(insertInto.getParameters().getValues(), vals);
	}

	@Test
	public void testFloatInsertInto() {
		String sqlCommand = "INSERT INTO Customers   VALuES   "
				+ "(12345.9039 , 'Tom B. Erichsen',   'Skagen 21','Stavanger',4006.00025,   'Norway PLEB'   )   ;   ";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		final String name = "Customers";
		final ArrayList<ArrayList<String>> vals = new ArrayList<>();
		final ArrayList<String> temp = new ArrayList<>();
		temp.add("12345.9039");
		temp.add("'Tom B. Erichsen'");
		temp.add("'Skagen 21'");
		temp.add("'Stavanger'");
		temp.add("4006.00025");
		temp.add("'Norway PLEB'");
		vals.add(temp);
		assertEquals(insertInto.interpret(sqlCommand), true);
		assertEquals(insertInto.getParameters().getTableName(), name);
		assertEquals(insertInto.getParameters().getValues(), vals);
	}

	@Test
	public void testDateTimeInsertion() {
		String sqlCommand = "insert into mytable (col1, col2) values(0001-01-01 11:10:10, 0001-01-01 11:11:11);";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		final String name = "mytable";
		final ArrayList<ArrayList<String>> vals = new ArrayList<>();
		final ArrayList<String> temp = new ArrayList<>();
		temp.add("0001-01-01 11:10:10");
		temp.add("0001-01-01 11:11:11");
		vals.add(temp);
		assertEquals(insertInto.interpret(sqlCommand), true);
		assertEquals(insertInto.getParameters().getTableName(), name);
		assertEquals(insertInto.getParameters().getValues(), vals);
	}

	@Test
	public void testDateFloatDateTimeInsertion() {
		String sqlCommand = "insert into mytAble values (1111-11-11, 1110-10-10 11:11:11, 45.66);";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		final String name = "mytAble";
		final ArrayList<ArrayList<String>> vals = new ArrayList<>();
		final ArrayList<String> temp = new ArrayList<>();
		temp.add("1111-11-11");
		temp.add("1110-10-10 11:11:11");
		temp.add("45.66");
		vals.add(temp);
		assertEquals(insertInto.interpret(sqlCommand), true);
		assertEquals(insertInto.getParameters().getTableName(), name);
		assertEquals(insertInto.getParameters().getValues(), vals);
	}

	@Test
	public void testInvalidInsertInto() {
		String sqlCommand = "INSERT my_TABLE(A_B,C_D) VALUES (\"x\",'y');";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		assertEquals(insertInto.interpret(sqlCommand), false);
	}
}
