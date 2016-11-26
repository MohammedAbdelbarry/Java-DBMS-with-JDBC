package jdbms.sql.parsing.statements.testing;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import jdbms.sql.parsing.expressions.math.AssignmentExpression;
import jdbms.sql.parsing.parser.Parser;
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
	Parser p;

	@Before
	public void executedBeforeEach() {
		check = false;
		p = new Parser();
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
		String SQLCommand = "      CREATE    DAtAbASE my_database  ;  ";
		SQLCommand = p.normalizeCommand(SQLCommand);
		InitialStatement createDb = new CreateDatabaseStatement();
		String name = "MY_DATABASE";
		if (createDb.interpret(SQLCommand)) {	
			check = true;
		}
		assertEquals(name, createDb.getParameters().getDatabaseName());
		assertEquals(check, true);
	}

	@Test
	public void testDropDataBase() {
		String SQLCommand = "DrOP   database   my_SQLDatabase   ;";
		SQLCommand = p.normalizeCommand(SQLCommand);
		InitialStatement dropDb = new DropDatabaseStatement();
		String name = "MY_SQLDATABASE";
		if (dropDb.interpret(SQLCommand)) {
			check = true;
		}
		assertEquals(name, dropDb.getParameters().getDatabaseName());
		assertEquals(check, true);
	}

	@Test
	public void testCreateTable() {
		String SQLCommand = "CREATE table Students(StudentID int,LastName varchar,FirstName varchar,Address varchar,Grades int);";
		SQLCommand = p.normalizeCommand(SQLCommand);
		InitialStatement create = new CreateTableStatement();
		HashMap<String, String> columnId = new HashMap<>();
		String name = "STUDENTS";
		columnId.put("STUDENTID", "INT");
		columnId.put("LASTNAME", "VARCHAR");
		columnId.put("FIRSTNAME", "VARCHAR");
		columnId.put("ADDRESS", "VARCHAR");
		columnId.put("GRADES", "INT");
		if (create.interpret(SQLCommand)) {
			check = true;
		}
		assertEquals(check, true);
		assertEquals(create.getParameters().getColumnDefinitions(), columnId);
		assertEquals(create.getParameters().getTableName(), name);
	}

	@Test
	public void testDropTable() {
		String SQLCommand = "drop table My_SQLDatabase ;";
		SQLCommand = p.normalizeCommand(SQLCommand);
		InitialStatement dropTable = new DropTableStatement();
		String name = "MY_SQLDATABASE";
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
		String name = "MY_TABLE";
		ArrayList<String> cols = new ArrayList<String>();
		cols.add("A_B");
		cols.add("C_D");
		ArrayList<ArrayList<String>> vals = new ArrayList<>();
		ArrayList<String> temp = new ArrayList<>();
		temp.add("\"X\"");
		temp.add("'Y'");
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
		String SQLCommand = "INSERT INTO Customers(CustomerName, ContactName, Address, City, PostalCode, Country)VALUES(12345,'Tom B. Erichsen','Skagen 21','Stavanger',4006,'Norway');";
		SQLCommand = p.normalizeCommand(SQLCommand);
		InitialStatement insertInto = new InsertIntoStatement();
		String name = "CUSTOMERS";
		ArrayList<String> cols = new ArrayList<String>();
		cols.add("CUSTOMERNAME");
		cols.add("CONTACTNAME");
		cols.add("ADDRESS");
		cols.add("CITY");
		cols.add("POSTALCODE");
		cols.add("COUNTRY");
		ArrayList<ArrayList<String>> vals = new ArrayList<>();
		ArrayList<String> temp = new ArrayList<>();
		temp.add("12345");
		temp.add("'TOM B. ERICHSEN'");
		temp.add("'SKAGEN 21'");
		temp.add("'STAVANGER'");
		temp.add("4006");
		temp.add("'NORWAY'");
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
		String SQLCommand = "INSERT INTO Customers   VALuES   (    12345  ,   'Tom B. Erichsen',   'Skagen 21','Stavanger',   4006,   'Norway PLEB'   )   ;   ";
		SQLCommand = p.normalizeCommand(SQLCommand);
		InitialStatement insertInto = new InsertIntoStatement();
		String name = "CUSTOMERS";
		ArrayList<ArrayList<String>> vals = new ArrayList<>();
		ArrayList<String> temp = new ArrayList<>();
		temp.add("12345");
		temp.add("'TOM B. ERICHSEN'");
		temp.add("'SKAGEN 21'");
		temp.add("'STAVANGER'");
		temp.add("4006");
		temp.add("'NORWAY PLEB'");
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
		String name = "TABLE_NAME";
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
		String name = "CUSTOMERS";
		ArrayList<String> cols = new ArrayList<>();
		cols.add("*");
		if (select.interpret(SQLCommand)) {
			check = true;
		}
		assertEquals(check, true);
		assertEquals(name, select.getParameters().getTableName());
		assertEquals(cols, select.getParameters().getColumns());
		assertEquals("CUSTOMERID", select.getParameters().getCondition().getLeftOperand());
		assertEquals("1", select.getParameters().getCondition().getRightOperand());
	}

	@Test
	public void testSelect() {
		String SQLCommand = "SELECT CustomerID,CustomerName,  Grades FROM Customers;";
		SQLCommand = p.normalizeCommand(SQLCommand);
		InitialStatement select = new SelectStatement();
		String name = "CUSTOMERS";
		ArrayList<String> cols = new ArrayList<>();
		cols.add("CUSTOMERID");
		cols.add("CUSTOMERNAME");
		cols.add("GRADES");
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
		String name = "CUSTOMERS";
		ArrayList<String> cols = new ArrayList<>();
		cols.add("CUSTOMERID");
		cols.add("CUSTOMERNAME");
		if (select.interpret(SQLCommand)) {
			check = true;
		}
		assertEquals(check, true);
		assertEquals(name, select.getParameters().getTableName());
		assertEquals(cols, select.getParameters().getColumns());
		assertEquals("COUNTRY", select.getParameters().getCondition().getLeftOperand());
		assertEquals("\"GERMANY\"", select.getParameters().getCondition().getRightOperand());
	}

	@Test
	public void testSelectConditionalInt() {
		String SQLCommand = "SELECT CustomerID,CustomerName FROM Customers WHERE id    =   447 ;";
		SQLCommand = p.normalizeCommand(SQLCommand);
		SelectStatement statement = new SelectStatement();
		if (statement.interpret(SQLCommand)) {
			check = true;
		}
		assertEquals(statement.getParameters().getTableName(), "CUSTOMERS");
		ArrayList<String> columns = new ArrayList<>();
		columns.add("CUSTOMERID");
		columns.add("CUSTOMERNAME");
		assertEquals(check, true);
		assertEquals(columns, statement.getParameters().getColumns());
		assertEquals("ID", statement.getParameters().getCondition().getLeftOperand());
		assertEquals("447", statement.getParameters().getCondition().getRightOperand());

	}

	@Test
	public void testUpdate() {
		String SQLCommand = "UPDATE Customers set ContactName='Alfred Schmidt',City  = ' Ham\'bu\'rg' WHERE CustomerName = 'Alfr\"eds Futter\"kiste';";
		SQLCommand = p.normalizeCommand(SQLCommand);
		InitialStatement update = new UpdateStatement();
		String name = "CUSTOMERS";
		if (update.interpret(SQLCommand)) {
			check = true;
		}
		assertEquals(check, true);
		assertEquals(name, update.getParameters().getTableName());
		assertEquals("CUSTOMERNAME", update.getParameters().getCondition().getLeftOperand());
		assertEquals("'ALFR\"EDS FUTTER\"KISTE'", update.getParameters().getCondition().getRightOperand());
		assertEquals("CITY", update.getParameters().getAssignment().getLeftOperand());
		assertEquals("' HAM\'BU\'RG'", update.getParameters().getAssignment().getRightOperand());
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
		assertEquals("SALARY", update.getParameters().getAssignment().getLeftOperand());
		assertEquals("1000", update.getParameters().getAssignment().getRightOperand());
	}

	@Test
	public void testDeleteAll() {
		String SQLCommand = "DELETE FROM table_name;";
		SQLCommand = p.normalizeCommand(SQLCommand);
		InitialStatement delete = new DeleteStatement();
		String name = "TABLE_NAME";
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
		String name = "CUSTOMERS";
		if (delete.interpret(SQLCommand)) {	
			check = true;
		}
		assertEquals(check, true);
		assertEquals(name, delete.getParameters().getTableName());
		assertEquals("NAME", delete.getParameters().getCondition().getLeftOperand());
		assertEquals("'X Y'", delete.getParameters().getCondition().getRightOperand());
	}
}