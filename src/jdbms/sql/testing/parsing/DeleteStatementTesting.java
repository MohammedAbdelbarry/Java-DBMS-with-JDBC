package jdbms.sql.testing.parsing;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import jdbms.sql.parsing.parser.StringNormalizer;
import jdbms.sql.parsing.statements.DeleteStatement;
import jdbms.sql.parsing.statements.InitialStatement;
import jdbms.sql.util.HelperClass;

public class DeleteStatementTesting {

	private StringNormalizer normalizer;
	private InitialStatement delete;

	@Before
	public void executedBeforeEach() {
		normalizer = new StringNormalizer();
		delete = new DeleteStatement();
		HelperClass.registerInitialStatements();
	}

	@Test
	public void testDeleteAll() {
		String sqlCommand = "DELETE FROM table_name;";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		final String name = "table_name";
		assertEquals(delete.interpret(sqlCommand), true);
		assertEquals(name, delete.getParameters().getTableName());
		assertEquals(null, delete.getParameters().getCondition());
	}

	@Test
	public void testDeleteConditional() {
		String sqlCommand = "DELETE FROM Customers WHERE name='x y';";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		final String name = "Customers";
		assertEquals(delete.interpret(sqlCommand), true);
		assertEquals(name, delete.getParameters().getTableName());
		assertEquals("name", delete.getParameters().getCondition().getLeftOperand());
		assertEquals("'x y'", delete.getParameters().getCondition().getRightOperand());
	}

	@Test
	public void testFloatDeleteConditional() {
		String sqlCommand = "DELETE FROM Customers WHERE grade = 5.6584;";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		final String name = "Customers";
		assertEquals(delete.interpret(sqlCommand), true);
		assertEquals(name, delete.getParameters().getTableName());
		assertEquals("grade", delete.getParameters().getCondition().getLeftOperand());
		assertEquals("5.6584", delete.getParameters().getCondition().getRightOperand());
	}

	@Test
	public void testInvalidFloatDeleteConditional() {
		String sqlCommand = "DELETE FROM Customers WHERE grade = 5.65.84;";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		assertEquals(delete.interpret(sqlCommand), false);
	}

	@Test
	public void testInvalidFloatDelete() {
		String sqlCommand = "DELETE FROM Customers WHERE grade = .84;";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		assertEquals(delete.interpret(sqlCommand), true);
	}

	@Test
	public void testInvalidDeleteAll() {
		String sqlCommand = "DELETE table_name;";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		assertEquals(delete.interpret(sqlCommand), false);
	}

	@Test
	public void testInvalidDeleteConditional() {
		String sqlCommand = "DELETE FROM Customers name='x y';";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		assertEquals(delete.interpret(sqlCommand), false);
	}
}
