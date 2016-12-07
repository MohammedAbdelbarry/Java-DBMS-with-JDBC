package jdbms.sql.testing.parsing;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import jdbms.sql.parsing.parser.StringNormalizer;
import jdbms.sql.parsing.statements.DeleteStatement;
import jdbms.sql.parsing.statements.InitialStatement;
import jdbms.sql.parsing.statements.Statement;
import jdbms.sql.parsing.statements.util.InitialStatementFactory;
import jdbms.sql.util.HelperClass;

public class DeleteStatementTesting {

	private StringNormalizer normalizer;
	private Collection<Statement> statements;
	private InitialStatement delete;

	@Before
	public void executedBeforeEach() {
		normalizer = new StringNormalizer();
		statements = new ArrayList<>();
		delete = new DeleteStatement();
		HelperClass.registerInitialStatements();
		for (String key : InitialStatementFactory.getInstance().getRegisteredStatements()) {
			statements.add(InitialStatementFactory.getInstance().createStatement(key));
		}
	}

	@Test
	public void testDeleteAll() {
		String sqlCommand = "DELETE FROM table_name;";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		String name = "table_name";
		assertEquals(delete.interpret(sqlCommand), true);
		assertEquals(name, delete.getParameters().getTableName());
		assertEquals(null, delete.getParameters().getCondition());
	}

	@Test
	public void testDeleteConditional() {
		String sqlCommand = "DELETE FROM Customers WHERE name='x y';";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		InitialStatement delete = new DeleteStatement();
		String name = "Customers";
		assertEquals(delete.interpret(sqlCommand), true);
		assertEquals(name, delete.getParameters().getTableName());
		assertEquals("name", delete.getParameters().getCondition().getLeftOperand());
		assertEquals("'x y'", delete.getParameters().getCondition().getRightOperand());
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
