package jdbms.sql.parsing.statements.testing;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import org.junit.Before;
import org.junit.Test;
import jdbms.sql.errors.ErrorHandler;
import jdbms.sql.parsing.parser.Parser;
import jdbms.sql.parsing.statements.InitialStatement;
import jdbms.sql.parsing.statements.Statement;
import jdbms.sql.parsing.statements.util.InitialStatementFactory;

public class FalseStatements {

	private Collection<Statement> statements;
	private Parser p;

	private boolean interpret(String SQLcommand){
		for (String key : InitialStatementFactory.getInstance().getRegisteredStatements()) {
			InitialStatement statement =
					InitialStatementFactory.
					getInstance().createStatement(key);
			boolean interpreted;
			try {
				interpreted = statement.interpret(SQLcommand);
			} catch (Exception e) {
				ErrorHandler.printSyntaxError();
				break;
			}
			if (interpreted) {
				return true;
			}
		}
		return false;
	}
	
	@Before
	public void executedBeforeEach() {
		this.p = new Parser();
		this.statements = new ArrayList<>();
		try {
			Class.forName("jdbms.sql.parsing.statements.CreateDatabaseStatement");
			Class.forName("jdbms.sql.parsing.statements.CreateTableStatement");
			Class.forName("jdbms.sql.parsing.statements.DropDatabaseStatement");
			Class.forName("jdbms.sql.parsing.statements.DropTableStatement");
			Class.forName("jdbms.sql.parsing.statements.InsertIntoStatement");
			Class.forName("jdbms.sql.parsing.statements.DeleteStatement");
			Class.forName("jdbms.sql.parsing.statements.SelectStatement");
			Class.forName("jdbms.sql.parsing.statements.UpdateStatement");
			Class.forName("jdbms.sql.parsing.statements.UseStatement");
			Class.forName("jdbms.sql.parsing.statements.AlterTableStatement");
			Class.forName("jdbms.sql.parsing.expressions.math.EqualsExpression");
			Class.forName("jdbms.sql.parsing.expressions.math.LargerThanEqualsExpression");
			Class.forName("jdbms.sql.parsing.expressions.math.LessThanEqualsExpression");
			Class.forName("jdbms.sql.parsing.expressions.math.LargerThanExpression");
			Class.forName("jdbms.sql.parsing.expressions.math.LessThanExpression");
			Class.forName("jdbms.sql.parsing.expressions.math.NotEqualsExpression");
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
		String SQLCommand = "CREATE my_database  ;";
		SQLCommand = p.normalizeCommand(SQLCommand);
		assertEquals(interpret(SQLCommand), false);
	}

	@Test
	public void testDropDataBase() {
		String SQLCommand = "Drop my_SQLDatabase   ;";
		SQLCommand = p.normalizeCommand(SQLCommand);
		assertEquals(interpret(SQLCommand), false);
	}

	@Test
	public void testCreateTable() {
		String SQLCommand = "CREATE TABLE (ID INTEGER, AGE INTEGER, NAME TEXT) ;";
		SQLCommand = p.normalizeCommand(SQLCommand);
		assertEquals(interpret(SQLCommand), false);
	}

	@Test
	public void testDropTable() {
		String SQLCommand = "drop My_SQLDatabase ;";
		SQLCommand = p.normalizeCommand(SQLCommand);
		assertEquals(interpret(SQLCommand), false);
	}

	@Test
	public void testInsertInto() {
		String SQLCommand = "INSERT my_TABLE(A_B,C_D) VALUES (\"x\",'y');";
		SQLCommand = p.normalizeCommand(SQLCommand);
		assertEquals(interpret(SQLCommand), false);
	}

	@Test
	public void testSelectAll() {
		String SQLCommand = "SELECT ** FROM table_name;";
		SQLCommand = p.normalizeCommand(SQLCommand);
		assertEquals(interpret(SQLCommand), false);
	}

	@Test
	public void testSelectAllConditional() {
		String SQLCommand = "SELECT * FROM Customers CustomerID>=1;";
		SQLCommand = p.normalizeCommand(SQLCommand);
		assertEquals(interpret(SQLCommand), false);
	}

	@Test
	public void testSelect() {
		String SQLCommand = "SELECT CustomerID,CustomerName,  Grades FROM ;";
		SQLCommand = p.normalizeCommand(SQLCommand);
		assertEquals(interpret(SQLCommand), false);
	}

	@Test
	public void testSelectConditionalVarchar() {
		String SQLCommand = "SELECT CustomerID,CustomerName Customers WHERE Country = \"Germany\" ;";
		SQLCommand = p.normalizeCommand(SQLCommand);
		assertEquals(interpret(SQLCommand), false);
	}

	@Test
	public void testSelectConditionalInt() {
		String SQLCommand = "SELECT CustomerID,CustomerName Customers WHERE id    =   447 ;";
		SQLCommand = p.normalizeCommand(SQLCommand);
		assertEquals(interpret(SQLCommand), false);
	}

	@Test
	public void testUpdateAll() {
		String SQLCommand = "UPDATE CUSTOMERS ADDRESS='Pune',SALARY=1000;";
		SQLCommand = p.normalizeCommand(SQLCommand);
		assertEquals(interpret(SQLCommand), false);
	}

	@Test
	public void testDeleteAll() {
		String SQLCommand = "DELETE table_name;";
		SQLCommand = p.normalizeCommand(SQLCommand);
		assertEquals(interpret(SQLCommand), false);
	}

	@Test
	public void testDeleteConditional() {
		String SQLCommand = "DELETE FROM Customers name='x y';";
		SQLCommand = p.normalizeCommand(SQLCommand);
		assertEquals(interpret(SQLCommand), false);
	}
}