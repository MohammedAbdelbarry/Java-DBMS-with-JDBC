package jdbms.sql.parsing.statements.testing;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import jdbms.sql.data.ColumnIdentifier;
import jdbms.sql.parsing.parser.StringNormalizer;
import jdbms.sql.parsing.statements.CreateDatabaseStatement;
import jdbms.sql.parsing.statements.CreateTableStatement;
import jdbms.sql.parsing.statements.DeleteStatement;
import jdbms.sql.parsing.statements.DropDatabaseStatement;
import jdbms.sql.parsing.statements.DropTableStatement;
import jdbms.sql.parsing.statements.InitialStatement;
import jdbms.sql.parsing.statements.InsertIntoStatement;
import jdbms.sql.parsing.statements.SelectStatement;
import jdbms.sql.parsing.statements.Statement;
import jdbms.sql.parsing.statements.UpdateStatement;
import jdbms.sql.parsing.statements.util.InitialStatementFactory;

public class Statements {

	Collection<Statement> statements;
	boolean check;
	StringNormalizer p;

	@Before
	public void executedBeforeEach() {
		check = false;
		p = new StringNormalizer();
		statements = new ArrayList<>();
		try {
			Class.forName("jdbms.sql.parsing.statements.CreateDatabaseStatement");
			Class.forName("jdbms.sql.parsing.statements.CreateTableStatement");
			Class.forName("jdbms.sql.parsing.statements.DropDatabaseStatement");
			Class.forName("jdbms.sql.parsing.statements.DropTableStatement");
			Class.forName("jdbms.sql.parsing.statements.InsertIntoStatement");
			Class.forName("jdbms.sql.parsing.statements.DeleteStatement");
			Class.forName("jdbms.sql.parsing.statements.SelectStatement");
			Class.forName("jdbms.sql.parsing.statements.UpdateStatement");
			Class.forName("jdbms.sql.parsing.expressions.math.EqualsExpression");
			Class.forName("jdbms.sql.parsing.expressions.math.LargerThanEqualsExpression");
			Class.forName("jdbms.sql.parsing.expressions.math.LessThanEqualsExpression");
			Class.forName("jdbms.sql.parsing.expressions.math.LargerThanExpression");
			Class.forName("jdbms.sql.parsing.expressions.math.LessThanExpression");
			Class.forName("jdbms.sql.datatypes.IntSQLType");
			Class.forName("jdbms.sql.datatypes.VarcharSQLType");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		for (String key : InitialStatementFactory.getInstance().getRegisteredStatements()) {
			statements.add(InitialStatementFactory.getInstance().createStatement(key));
		}
	}

	@Test
	public void testCreateDataBase() {
		String SQLCommand = "   CREATE    DAtAbASE my_database  ;  ";
		SQLCommand = p.normalizeCommand(SQLCommand);
		InitialStatement createDb = new CreateDatabaseStatement();
		String name = "my_database";
		if (createDb.interpret(SQLCommand)) {
			check = true;
		}
		assertEquals(name, createDb.getParameters().getDatabaseName());
		assertEquals(check, true);
	}

	@Test
	public void testDropDataBase() {
		String SQLCommand = "Drop   database   my_SQLDatabase   ;";
		SQLCommand = p.normalizeCommand(SQLCommand);
		InitialStatement dropDb = new DropDatabaseStatement();
		String name = "my_SQLDatabase";
		if (dropDb.interpret(SQLCommand)) {
			check = true;
		}
		assertEquals(name, dropDb.getParameters().getDatabaseName());
		assertEquals(check, true);
	}

	@Test
	public void testCreateTable() {
		String SQLCommand = "CREATE TABLE NEWTABLE (ID INTEGER, AGE INTEGER, NAME TEXT) ;";
		SQLCommand = p.normalizeCommand(SQLCommand);
		InitialStatement create = new CreateTableStatement();
		ArrayList<ColumnIdentifier> columnId = new ArrayList<>();
		String name = "NEWTABLE";
		ColumnIdentifier cd = new ColumnIdentifier("ID", "INTEGER");
		columnId.add(cd);
		cd = new ColumnIdentifier("AGE", "INTEGER");
		columnId.add(cd);
		cd = new ColumnIdentifier("NAME", "TEXT");
		columnId.add(cd);
		if (create.interpret(SQLCommand)) {
			check = true;
		}
		assertEquals(check, true);
		assertEquals(create.getParameters().getTableName(), name);

	}

	@Test
	public void testDropTable() {
		String SQLCommand = "drop table My_SQLDatabase ;";
		SQLCommand = p.normalizeCommand(SQLCommand);
		InitialStatement dropTable = new DropTableStatement();
		String name = "My_SQLDatabase";
		if (dropTable.interpret(SQLCommand)) {
			check = true;
		}
		assertEquals(name, dropTable.getParameters().getTableName());
		assertEquals(check, true);
	}

	@Test
	public void testInsertInto() {
		String SQLCommand = "INSERT into my_TABLE(A_B,C_D) VALUES (\"x\",'y');";
		SQLCommand = p.normalizeCommand(SQLCommand);
		InitialStatement insertInto = new InsertIntoStatement();
		String name = "my_TABLE";
		ArrayList<String> cols = new ArrayList<>();
		cols.add("A_B");
		cols.add("C_D");
		ArrayList<ArrayList<String>> vals = new ArrayList<>();
		ArrayList<String> temp = new ArrayList<>();
		temp.add("\"x\"");
		temp.add("'y'");
		vals.add(temp);
		if (insertInto.interpret(SQLCommand)) {
			check = true;
		}
		assertEquals(check, true);
		assertEquals(insertInto.getParameters().getColumns(), cols);
		assertEquals(insertInto.getParameters().getTableName(), name);
		assertEquals(insertInto.getParameters().getValues(), vals);
	}

	@Test
	public void testInsertIntoGluedExpression() {
		String SQLCommand = "INSERT INTO Customers(CustomerName, ContactName, Address, City, PostalCode, Country)"
				+ "VALUES(12345,'Tom B. Erichsen','Skagen 21','Stavanger',4006,'Norway');";
		SQLCommand = p.normalizeCommand(SQLCommand);
		InitialStatement insertInto = new InsertIntoStatement();
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
		if (insertInto.interpret(SQLCommand)) {
			check = true;
		}
		assertEquals(check, true);
		assertEquals(insertInto.getParameters().getColumns(), cols);
		assertEquals(insertInto.getParameters().getTableName(), name);
		assertEquals(insertInto.getParameters().getValues(), vals);
	}

	@Test
	public void testInsertIntoWithoutColumns() {
		String SQLCommand = "INSERT INTO Customers   VALuES   "
				+ "(    12345  ,   'Tom B. Erichsen',   'Skagen 21','Stavanger',   4006,   'Norway PLEB'   )   ;   ";
		SQLCommand = p.normalizeCommand(SQLCommand);
		InitialStatement insertInto = new InsertIntoStatement();
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
		if (insertInto.interpret(SQLCommand)) {
			check = true;
		}
		assertEquals(check, true);
		assertEquals(insertInto.getParameters().getTableName(), name);
		assertEquals(insertInto.getParameters().getValues(), vals);
	}

	@Test
	public void testSelectAll() {
		String SQLCommand = "SELECT * FROM table_name;";
		SQLCommand = p.normalizeCommand(SQLCommand);
		InitialStatement select = new SelectStatement();
		String name = "table_name";
		ArrayList<String> cols = new ArrayList<>();
		cols.add("*");
		if (select.interpret(SQLCommand)) {
			check = true;
		}
		assertEquals(check, true);
		assertEquals(name, select.getParameters().getTableName());
		assertEquals(cols, select.getParameters().getColumns());
		assertEquals(null, select.getParameters().getCondition());
	}

	@Test
	public void testSelectAllConditional() {
		String SQLCommand = "SELECT * FROM Customers WHERE CustomerID>=1;";
		SQLCommand = p.normalizeCommand(SQLCommand);
		InitialStatement select = new SelectStatement();
		String name = "Customers";
		ArrayList<String> cols = new ArrayList<>();
		cols.add("*");
		if (select.interpret(SQLCommand)) {
			check = true;
		}
		assertEquals(check, true);
		assertEquals(name, select.getParameters().getTableName());
		assertEquals(cols, select.getParameters().getColumns());
		assertEquals("CustomerID", select.getParameters().getCondition().getLeftOperand());
		assertEquals("1", select.getParameters().getCondition().getRightOperand());
	}

	@Test
	public void testSelect() {
		String SQLCommand = "SELECT CustomerID,CustomerName,  Grades FROM Customers;";
		SQLCommand = p.normalizeCommand(SQLCommand);
		InitialStatement select = new SelectStatement();
		String name = "Customers";
		ArrayList<String> cols = new ArrayList<>();
		cols.add("CustomerID");
		cols.add("CustomerName");
		cols.add("Grades");
		if (select.interpret(SQLCommand)) {
			check = true;
		}
		assertEquals(check, true);
		assertEquals(name, select.getParameters().getTableName());
		assertEquals(cols, select.getParameters().getColumns());
		assertEquals(null, select.getParameters().getCondition());
	}

	@Test
	public void testSelectConditionalVarchar() {
		String SQLCommand = "SELECT CustomerID,CustomerName FROM Customers WHERE Country = \"Germany\" ;";
		SQLCommand = p.normalizeCommand(SQLCommand);
		InitialStatement select = new SelectStatement();
		String name = "Customers";
		ArrayList<String> cols = new ArrayList<>();
		cols.add("CustomerID");
		cols.add("CustomerName");
		if (select.interpret(SQLCommand)) {
			check = true;
		}
		assertEquals(check, true);
		assertEquals(name, select.getParameters().getTableName());
		assertEquals(cols, select.getParameters().getColumns());
		assertEquals("Country", select.getParameters().getCondition().getLeftOperand());
		assertEquals("\"Germany\"", select.getParameters().getCondition().getRightOperand());
	}

	@Test
	public void testSelectConditionalInt() {
		String SQLCommand = "SELECT CustomerID,CustomerName FROM Customers WHERE id    =   447 ;";
		SQLCommand = p.normalizeCommand(SQLCommand);
		SelectStatement statement = new SelectStatement();
		if (statement.interpret(SQLCommand)) {
			check = true;
		}
		assertEquals(statement.getParameters().getTableName(), "Customers");
		ArrayList<String> columns = new ArrayList<>();
		columns.add("CustomerID");
		columns.add("CustomerName");
		assertEquals(check, true);
		assertEquals(columns, statement.getParameters().getColumns());
		assertEquals("id", statement.getParameters().getCondition().getLeftOperand());
		assertEquals("447", statement.getParameters().getCondition().getRightOperand());

	}

	@Test
	public void testUpdate() {
		String SQLCommand = "UPDATE Customers set"
				+ " ContactName='Alfred Schmidt',City  = ' Ham\"bu\"rg' WHERE CustomerName = 'Alfr\"eds Futter\"kiste';";
		SQLCommand = p.normalizeCommand(SQLCommand);
		InitialStatement update = new UpdateStatement();
		String name = "Customers";
		if (update.interpret(SQLCommand)) {
			check = true;
		}
		assertEquals(check, true);
		assertEquals(name, update.getParameters().getTableName());
		assertEquals("ContactName", update.getParameters().getAssignmentList().get(0).getLeftOperand());
		assertEquals("'Alfred Schmidt'", update.getParameters().getAssignmentList().get(0).getRightOperand());
		assertEquals("City", update.getParameters().getAssignmentList().get(1).getLeftOperand());
		assertEquals("' Ham\"bu\"rg'", update.getParameters().getAssignmentList().get(1).getRightOperand());
		assertEquals("CustomerName", update.getParameters().getCondition().getLeftOperand());
		assertEquals("'Alfr\"eds Futter\"kiste'", update.getParameters().getCondition().getRightOperand());
	}

	@Test
	public void testUpdateAll() {
		String SQLCommand = "UPDATE CUSTOMERS SET ADDRESS='Pune',SALARY=1000;";
		SQLCommand = p.normalizeCommand(SQLCommand);
		InitialStatement update = new UpdateStatement();
		String name = "CUSTOMERS";
		if (update.interpret(SQLCommand)) {
			check = true;
		}
		assertEquals(check, true);
		assertEquals(name, update.getParameters().getTableName());
		assertEquals(null, update.getParameters().getCondition());
		assertEquals("ADDRESS", update.getParameters().getAssignmentList().get(0).getLeftOperand());
		assertEquals("'Pune'", update.getParameters().getAssignmentList().get(0).getRightOperand());
		assertEquals("SALARY", update.getParameters().getAssignmentList().get(1).getLeftOperand());
		assertEquals("1000", update.getParameters().getAssignmentList().get(1).getRightOperand());
	}

	@Test
	public void testDeleteAll() {
		String SQLCommand = "DELETE FROM table_name;";
		SQLCommand = p.normalizeCommand(SQLCommand);
		InitialStatement delete = new DeleteStatement();
		String name = "table_name";
		if (delete.interpret(SQLCommand)) {
			check = true;
		}
		assertEquals(check, true);
		assertEquals(name, delete.getParameters().getTableName());
		assertEquals(null, delete.getParameters().getCondition());
	}

	@Test
	public void testDeleteConditional() {
		String SQLCommand = "DELETE FROM Customers WHERE name='x y';";
		SQLCommand = p.normalizeCommand(SQLCommand);
		InitialStatement delete = new DeleteStatement();
		String name = "Customers";
		if (delete.interpret(SQLCommand)) {
			check = true;
		}
		assertEquals(check, true);
		assertEquals(name, delete.getParameters().getTableName());
		assertEquals("name", delete.getParameters().getCondition().getLeftOperand());
		assertEquals("'x y'", delete.getParameters().getCondition().getRightOperand());
	}
}