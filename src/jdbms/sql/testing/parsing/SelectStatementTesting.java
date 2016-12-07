package jdbms.sql.testing.parsing;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import jdbms.sql.parsing.parser.StringNormalizer;
import jdbms.sql.parsing.statements.InitialStatement;
import jdbms.sql.parsing.statements.SelectStatement;
import jdbms.sql.parsing.statements.Statement;
import jdbms.sql.parsing.statements.util.InitialStatementFactory;
import jdbms.sql.util.HelperClass;

public class SelectStatementTesting {

	private StringNormalizer normalizer;
	private Collection<Statement> statements;
	private InitialStatement select;

	@Before
	public void executedBeforeEach() {
		normalizer = new StringNormalizer();
		statements = new ArrayList<>();
		select = new SelectStatement();
		HelperClass.registerInitialStatements();
		for (String key : InitialStatementFactory.getInstance().getRegisteredStatements()) {
			statements.add(InitialStatementFactory.getInstance().createStatement(key));
		}
	}

	@Test
	public void testSelectAll() {
		String sqlCommand = "SELECT * FROM table_name;";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		String name = "table_name";
		ArrayList<String> cols = new ArrayList<>();
		cols.add("*");		
		assertEquals(select.interpret(sqlCommand), true);
		assertEquals(name, select.getParameters().getTableName());
		assertEquals(cols, select.getParameters().getColumns());
		assertEquals(null, select.getParameters().getCondition());
	}

	@Test
	public void testSelectAllConditional() {
		String sqlCommand = "SELECT * FROM Customers WHERE CustomerID>=1;";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		String name = "Customers";
		ArrayList<String> cols = new ArrayList<>();
		cols.add("*");
		assertEquals(select.interpret(sqlCommand), true);
		assertEquals(name, select.getParameters().getTableName());
		assertEquals(cols, select.getParameters().getColumns());
		assertEquals("CustomerID", select.getParameters().getCondition().getLeftOperand());
		assertEquals("1", select.getParameters().getCondition().getRightOperand());
	}

	@Test
	public void testSelect() {
		String sqlCommand = "SELECT CustomerID,CustomerName,  Grades FROM Customers;";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		String name = "Customers";
		ArrayList<String> cols = new ArrayList<>();
		cols.add("CustomerID");
		cols.add("CustomerName");
		cols.add("Grades");
		assertEquals(select.interpret(sqlCommand), true);
		assertEquals(name, select.getParameters().getTableName());
		assertEquals(cols, select.getParameters().getColumns());
		assertEquals(null, select.getParameters().getCondition());
	}

	@Test
	public void testSelectConditionalVarchar() {
		String sqlCommand = "SELECT CustomerID,CustomerName FROM Customers WHERE Country = \"Germany\" ;";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		String name = "Customers";
		ArrayList<String> cols = new ArrayList<>();
		cols.add("CustomerID");
		cols.add("CustomerName");
		assertEquals(select.interpret(sqlCommand), true);
		assertEquals(name, select.getParameters().getTableName());
		assertEquals(cols, select.getParameters().getColumns());
		assertEquals("Country", select.getParameters().getCondition().getLeftOperand());
		assertEquals("\"Germany\"", select.getParameters().getCondition().getRightOperand());
	}

	@Test
	public void testSelectConditionalInt() {
		String sqlCommand = "SELECT CustomerID,CustomerName FROM Customers WHERE id    =   447 ;";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		ArrayList<String> columns = new ArrayList<>();
		columns.add("CustomerID");
		columns.add("CustomerName");
		assertEquals(select.interpret(sqlCommand), true);
		assertEquals(select.getParameters().getTableName(), "Customers");
		assertEquals(columns, select.getParameters().getColumns());
		assertEquals("id", select.getParameters().getCondition().getLeftOperand());
		assertEquals("447", select.getParameters().getCondition().getRightOperand());

	}

	@Test
	public void testSelectDistinct() {
		String sqlCommand = "SELECT DISTINCT column_name1,column_name2 FROM table_name;";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		String name = "table_name";
		ArrayList <String> list = new ArrayList<>();
		list.add("column_name1");
		list.add("column_name2");
		assertEquals(select.interpret(sqlCommand), true);
		assertEquals(name, select.getParameters().getTableName());
		assertEquals(list, select.getParameters().getColumns());
	}

	@Test
	public void testOrderBy() {
		String sqlCommand = "SELECT col1, col2 FROM table_name orDer BY column_name DESC;";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		String name = "table_name";
		ArrayList <String> list = new ArrayList<>();
		list.add("col1");
		list.add("col2");
		assertEquals(select.interpret(sqlCommand), true);
		assertEquals(false, select.getParameters().isAscending());
		assertEquals(name, select.getParameters().getTableName());
		assertEquals(list, select.getParameters().getColumns());
		assertEquals("column_name", select.getParameters().getSortingColumnName());
	}

	@Test
	public void testInvalidSelectAll() {
		String sqlCommand = "SELECT ** FROM table_name;";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		assertEquals(select.interpret(sqlCommand), false);
	}

	@Test
	public void testInvalidSelectAllConditional() {
		String sqlCommand = "SELECT * FROM Customers CustomerID>=1;";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		assertEquals(select.interpret(sqlCommand), false);
	}

	@Test
	public void testInvalidSelect() {
		String sqlCommand = "SELECT CustomerID,CustomerName,  Grades FROM ;";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		assertEquals(select.interpret(sqlCommand), false);
	}

	@Test
	public void testInvalidSelectConditionalVarchar() {
		String sqlCommand = "SELECT CustomerID,CustomerName Customers WHERE Country = \"Germany\" ;";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		assertEquals(select.interpret(sqlCommand), false);
	}

	@Test
	public void testInvalidSelectConditionalInt() {
		String sqlCommand = "SELECT CustomerID,CustomerName Customers WHERE id    =   447 ;";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		assertEquals(select.interpret(sqlCommand), false);
	}
}
