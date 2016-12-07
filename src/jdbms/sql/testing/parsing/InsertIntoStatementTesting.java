package jdbms.sql.testing.parsing;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import jdbms.sql.parsing.parser.StringNormalizer;
import jdbms.sql.parsing.statements.InitialStatement;
import jdbms.sql.parsing.statements.InsertIntoStatement;
import jdbms.sql.parsing.statements.Statement;
import jdbms.sql.parsing.statements.util.InitialStatementFactory;
import jdbms.sql.util.HelperClass;

public class InsertIntoStatementTesting {

	private StringNormalizer normalizer;
	private Collection<Statement> statements;
	private InitialStatement insertInto;

	@Before
	public void executedBeforeEach() {
		normalizer = new StringNormalizer();
		statements = new ArrayList<>();
		insertInto = new InsertIntoStatement();
		HelperClass.registerInitialStatements();
		for (String key : InitialStatementFactory.getInstance().getRegisteredStatements()) {
			statements.add(InitialStatementFactory.getInstance().createStatement(key));
		}
	}

	@Test
	public void testInsertInto() {
		String SQLCommand = "INSERT into my_TABLE(A_B,C_D) VALUES (\"x\",'y');";
		SQLCommand = normalizer.normalizeCommand(SQLCommand);
		String name = "my_TABLE";
		ArrayList<String> cols = new ArrayList<>();
		cols.add("A_B");
		cols.add("C_D");
		ArrayList<ArrayList<String>> vals = new ArrayList<>();
		ArrayList<String> temp = new ArrayList<>();
		temp.add("\"x\"");
		temp.add("'y'");
		vals.add(temp);
		assertEquals(insertInto.interpret(SQLCommand), true);
		assertEquals(insertInto.getParameters().getColumns(), cols);
		assertEquals(insertInto.getParameters().getTableName(), name);
		assertEquals(insertInto.getParameters().getValues(), vals);
	}

	@Test
	public void testInsertIntoGluedExpression() {
		String SQLCommand = "INSERT INTO Customers(CustomerName, ContactName, Address, City, PostalCode, Country)"
				+ "VALUES(12345,'Tom B. Erichsen','Skagen 21','Stavanger',4006,'Norway');";
		SQLCommand = normalizer.normalizeCommand(SQLCommand);
		String name = "Customers";
		ArrayList<String> cols = new ArrayList<>();
		cols.add("CustomerName");
		cols.add("ContactName");
		cols.add("Address");
		cols.add("City");
		cols.add("PostalCode");
		cols.add("Country");
		ArrayList<ArrayList<String>> vals = new ArrayList<>();
		ArrayList<String> temp = new ArrayList<>();
		temp.add("12345");
		temp.add("'Tom B. Erichsen'");
		temp.add("'Skagen 21'");
		temp.add("'Stavanger'");
		temp.add("4006");
		temp.add("'Norway'");
		vals.add(temp);
		assertEquals(insertInto.interpret(SQLCommand), true);
		assertEquals(insertInto.getParameters().getColumns(), cols);
		assertEquals(insertInto.getParameters().getTableName(), name);
		assertEquals(insertInto.getParameters().getValues(), vals);
	}

	@Test
	public void testInsertIntoWithoutColumns() {
		String SQLCommand = "INSERT INTO Customers   VALuES   "
				+ "(    12345  ,   'Tom B. Erichsen',   'Skagen 21','Stavanger',   4006,   'Norway PLEB'   )   ;   ";
		SQLCommand = normalizer.normalizeCommand(SQLCommand);
		String name = "Customers";
		ArrayList<ArrayList<String>> vals = new ArrayList<>();
		ArrayList<String> temp = new ArrayList<>();
		temp.add("12345");
		temp.add("'Tom B. Erichsen'");
		temp.add("'Skagen 21'");
		temp.add("'Stavanger'");
		temp.add("4006");
		temp.add("'Norway PLEB'");
		vals.add(temp);
		assertEquals(insertInto.interpret(SQLCommand), true);
		assertEquals(insertInto.getParameters().getTableName(), name);
		assertEquals(insertInto.getParameters().getValues(), vals);
	}

	@Test
	public void testInvalidInsertInto() {
		String SQLCommand = "INSERT my_TABLE(A_B,C_D) VALUES (\"x\",'y');";
		SQLCommand = normalizer.normalizeCommand(SQLCommand);
		assertEquals(insertInto.interpret(SQLCommand), false);
	}
}
