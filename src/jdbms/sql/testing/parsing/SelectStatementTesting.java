package jdbms.sql.testing.parsing;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import jdbms.sql.parsing.expressions.util.ColumnOrder;
import jdbms.sql.parsing.parser.StringNormalizer;
import jdbms.sql.parsing.statements.InitialStatement;
import jdbms.sql.parsing.statements.SelectStatement;
import jdbms.sql.util.HelperClass;

public class SelectStatementTesting {

	private StringNormalizer normalizer;
	private InitialStatement select;

	@Before
	public void executedBeforeEach() {
		normalizer = new StringNormalizer();
		select = new SelectStatement();
		HelperClass.registerInitialStatements();
	}

	@Test
	public void testSelectAll() {
		String sqlCommand = "SELECT * FROM table_name;";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		final String name = "table_name";
		final ArrayList<String> cols = new ArrayList<>();
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
		final String name = "Customers";
		final ArrayList<String> cols = new ArrayList<>();
		cols.add("*");
		assertEquals(select.interpret(sqlCommand), true);
		assertEquals(name, select.getParameters().getTableName());
		assertEquals(cols, select.getParameters().getColumns());
		assertEquals("CustomerID", select.getParameters().getCondition().getLeftOperand());
		assertEquals("1", select.getParameters().getCondition().getRightOperand());
	}

	@Test
	public void testFloatSelectAllConditional() {
		String sqlCommand = "SELECT * FROM Customers WHERE CustomerCoolness >= 1.512256;";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		final String name = "Customers";
		final ArrayList<String> cols = new ArrayList<>();
		cols.add("*");
		assertEquals(select.interpret(sqlCommand), true);
		assertEquals(name, select.getParameters().getTableName());
		assertEquals(cols, select.getParameters().getColumns());
		assertEquals("CustomerCoolness", select.getParameters().getCondition().getLeftOperand());
		assertEquals("1.512256", select.getParameters().getCondition().getRightOperand());
	}

	@Test
	public void SelectAllOrderBy() {
		String sqlCommand = "SELECT * FROM Customers WHERE CustomerCoolness != 1.512256 Order By col1, col2, col3 DESC;";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		final String name = "Customers";
		final ArrayList<String> cols = new ArrayList<>();
		cols.add("*");
		final ArrayList<ColumnOrder> list = new ArrayList<>();
		list.add(new ColumnOrder("col1", "ASC"));
		list.add(new ColumnOrder("col2", "ASC"));
		list.add(new ColumnOrder("col3", "DESC"));
		assertEquals(select.interpret(sqlCommand), true);
		assertEquals(name, select.getParameters().getTableName());
		assertEquals(cols, select.getParameters().getColumns());
		assertEquals(list, select.getParameters().getColumnsOrder());
		assertEquals("CustomerCoolness", select.getParameters().getCondition().getLeftOperand());
		assertEquals("1.512256", select.getParameters().getCondition().getRightOperand());
	}

	@Test
	public void testSelect() {
		String sqlCommand = "SELECT CustomerID,CustomerName,  Grades FROM Customers;";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		final String name = "Customers";
		final ArrayList<String> cols = new ArrayList<>();
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
		final String name = "Customers";
		final ArrayList<String> cols = new ArrayList<>();
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
		final ArrayList<String> columns = new ArrayList<>();
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
		final String name = "table_name";
		final ArrayList <String> list = new ArrayList<>();
		list.add("column_name1");
		list.add("column_name2");
		assertEquals(select.interpret(sqlCommand), true);
		assertEquals(name, select.getParameters().getTableName());
		assertEquals(list, select.getParameters().getColumns());
		assertEquals(select.getParameters().isDistinct(), true);
	}

	@Test
	public void testOrderBy() {
		String sqlCommand = "SELECT coll2, col4, hi_man FROM table_name orDer BY col1 ASC, col2, col3 DESC, col4 ASC;";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		final String name = "table_name";
		final ArrayList <String> list = new ArrayList<>();
		list.add("coll2");
		list.add("col4");
		list.add("hi_man");
		final ArrayList<ColumnOrder> myOrderList = new ArrayList<>();
		myOrderList.add(new ColumnOrder("col1", "ASC"));
		myOrderList.add(new ColumnOrder("col2", "ASC"));
		myOrderList.add(new ColumnOrder("col3", "DESC"));
		myOrderList.add(new ColumnOrder("col4", "ASC"));
		assertEquals(select.interpret(sqlCommand), true);
		assertEquals(name, select.getParameters().getTableName());
		assertEquals(list, select.getParameters().getColumns());
		assertEquals(myOrderList, select.getParameters().getColumnsOrder());
	}

	@Test
	public void testSelectColListOrderBy() {
		String sqlCommand = "SELECT col1, col2, hi_man FROM table_name where col1 != \"FROM\" orDer BY col1 ASC, col2, col3 DESC, col4 ASC;";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		final String name = "table_name";
		final ArrayList <String> list = new ArrayList<>();
		list.add("col1");
		list.add("col2");
		list.add("hi_man");
		final ArrayList<ColumnOrder> myOrderList = new ArrayList<>();
		myOrderList.add(new ColumnOrder("col1", "ASC"));
		myOrderList.add(new ColumnOrder("col2", "ASC"));
		myOrderList.add(new ColumnOrder("col3", "DESC"));
		myOrderList.add(new ColumnOrder("col4", "ASC"));
		assertEquals(select.interpret(sqlCommand), true);
		assertEquals(name, select.getParameters().getTableName());
		assertEquals(list, select.getParameters().getColumns());
		assertEquals(myOrderList, select.getParameters().getColumnsOrder());
		assertEquals("col1", select.getParameters().getCondition().getLeftOperand());
		assertEquals("\"FROM\"", select.getParameters().getCondition().getRightOperand());
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
