package jdbms.sql.parsing.statements.testing;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import jdbms.sql.parsing.parser.Parser;
import jdbms.sql.parsing.statements.CreateTableStatement;
import jdbms.sql.parsing.statements.InitialStatement;
import jdbms.sql.parsing.statements.SelectStatement;
import jdbms.sql.parsing.statements.Statement;
import jdbms.sql.parsing.statements.util.StatementFactory;

public class Statements {

	Collection<Statement> statements = new ArrayList<>();
	boolean check = false;
	Parser p = new Parser();

	@Before
	public void executedBeforeEach() {
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
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		for (String key : StatementFactory.getInstance().getRegisteredStatements()) {
			statements.add(StatementFactory.getInstance().createStatement(key));
		}
	}

	@Test
	public void testCreateDataBase() {
		String SQLCommand = "CREATE DATABASE my_Database;";
		SQLCommand = p.normalizeCommand(SQLCommand);
		for (Statement statement : statements) {
			if (statement.interpret(SQLCommand)) {
				check = true;
			}
		}
		assertEquals(check, true);
	}

	@Test
	public void testDropDataBase() {
		String SQLCommand = "DROP database My_SQLDatabase ;";
		SQLCommand = p.normalizeCommand(SQLCommand);
		for (Statement statement : statements) {
			if (statement.interpret(SQLCommand)) {
				check = true;
			}
		}
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
		assertEquals(create.getParameters().getColumnDefinitions(), columnId);
		assertEquals(create.getParameters().getTableName(), name);
		assertEquals(check, true);
	}

	@Test
	public void testDropTable() {
		String SQLCommand = "drop table My_SQLDatabase ;";
		SQLCommand = p.normalizeCommand(SQLCommand);
		for (Statement statement : statements) {
			if (statement.interpret(SQLCommand)) {
				check = true;
			}
		}
		assertEquals(check, true);
	}

	@Test
	public void testInsertInto() {
		String SQLCommand = "INSERT into my_TABLE(A_B,C_D) VALUES (\"x\", 'y'), (z, m);";
		SQLCommand = p.normalizeCommand(SQLCommand);
		for (Statement statement : statements) {
			if (statement.interpret(SQLCommand)) {
				check = true;
			}
		}
		assertEquals(check, true);
	}

	@Test
	public void testInsertIntoGluedExpression() {
		String SQLCommand = "INSERT INTO Customers(CustomerName, ContactName, Address, City, PostalCode, Country)VALUES(12345,'Tom B. Erichsen','Skagen 21','Stavanger',4006,'Norway');";
		SQLCommand = p.normalizeCommand(SQLCommand);
		for (Statement statement : statements) {
			if (statement.interpret(SQLCommand)) {
				check = true;
			}
		}
		assertEquals(check, true);
	}

	@Test
	public void testInsertIntoWithoutColumns() {
		String SQLCommand = "INSERT INTO Customers VALUES(12345,'Tom B. Erichsen','Skagen 21','Stavanger',4006,'Norway');";
		SQLCommand = p.normalizeCommand(SQLCommand);
		for (Statement statement : statements) {
			if (statement.interpret(SQLCommand)) {
				check = true;
			}
		}
		assertEquals(check, true);
	}

	@Test
	public void testSelectAll() {
		String SQLCommand = "SELECT * FROM table_name;";
		SQLCommand = p.normalizeCommand(SQLCommand);
		for (Statement statement : statements) {
			if (statement.interpret(SQLCommand)) {
				check = true;
			}
		}
		assertEquals(check, true);
	}

	@Test
	public void testSelectAllConditional() {
		String SQLCommand = "SELECT * FROM Customers WHERE CustomerID>=1;";
		SQLCommand = p.normalizeCommand(SQLCommand);
		for (Statement statement : statements) {
			if (statement.interpret(SQLCommand)) {
				check = true;
			}
		}
		assertEquals(check, true);
	}

	@Test
	public void testSelect() {
		String SQLCommand = "SELECT CustomerID,CustomerName,  Grades FROM Customers;";
		SQLCommand = p.normalizeCommand(SQLCommand);
		for (Statement statement : statements) {
			if (statement.interpret(SQLCommand)) {
				check = true;
			}
		}
		assertEquals(check, true);
	}

	@Test
	public void testSelectConditionalVarchar() {
		String SQLCommand = "SELECT CustomerID,CustomerName FROM Customers WHERE Country = 'Germany' ;";
		SQLCommand = p.normalizeCommand(SQLCommand);
		for (Statement statement : statements) {
			if (statement.interpret(SQLCommand)) {
				check = true;
			}
		}
		assertEquals(check, true);
	}

	@Test
	public void testSelectConditionalInt() {
		String SQLCommand = "SELECT CustomerID,CustomerName FROM Customers WHERE id = 7 ;";
		SQLCommand = p.normalizeCommand(SQLCommand);
		SelectStatement statement = new SelectStatement();
		if (statement.interpret(SQLCommand)) {
			check = true;
		}
		assertEquals(statement.getParameters().getTableName(), "CUSTOMERS");
		ArrayList<String> columns = new ArrayList<>();
		columns.add("CUSTOMERID");
		columns.add("CUSTOMERNAME");
		assertEquals(columns, statement.getParameters().getColumns());
		assertEquals("ID", statement.getParameters().getCondition().getLeftOperand());
		assertEquals("7", statement.getParameters().getCondition().getRightOperand());
		assertEquals(check, true);

	}

	@Test
	public void testUpdate() {
		String SQLCommand = "UPDATE Customers SET ContactName='AlfredSchmidt',City='Hamburg' WHERE CustomerName='AlfredsFutterkiste';";
		SQLCommand = p.normalizeCommand(SQLCommand);
		for (Statement statement : statements) {
			if (statement.interpret(SQLCommand)) {
				check = true;
			}
		}
		assertEquals(check, true);
	}

	@Test
	public void testUpdateGlued() {
		String SQLCommand = "UPDATE Customers SET ContactName='AlfredSchmidt',City='Hamburg' WHERE CustomerName='AlfredsFutterkiste';";
		SQLCommand = p.normalizeCommand(SQLCommand);
		for (Statement statement : statements) {
			if (statement.interpret(SQLCommand)) {
				check = true;
			}
		}
		assertEquals(check, true);
	}

	@Test
	public void testUpdateAll() {
		String SQLCommand = "UPDATE CUSTOMERS SET ADDRESS='Pune',SALARY=1000;";
		SQLCommand = p.normalizeCommand(SQLCommand);
		for (Statement statement : statements) {
			if (statement.interpret(SQLCommand)) {
				check = true;
			}
		}
		assertEquals(check, true);
	}

	@Test
	public void testDeleteAll() {
		String SQLCommand = "DELETE FROM table_name;";
		SQLCommand = p.normalizeCommand(SQLCommand);
		for (Statement statement : statements) {
			if (statement.interpret(SQLCommand)) {
				check = true;
			}
		}
		assertEquals(check, true);
	}

	@Test
	public void testDeleteConditional() {
		String SQLCommand = "DELETE FROM Customers WHERE name='xy';";
		SQLCommand = p.normalizeCommand(SQLCommand);
		for (Statement statement : statements) {
			if (statement.interpret(SQLCommand)) {
				check = true;
			}
		}
		assertEquals(check, true);
	}

}