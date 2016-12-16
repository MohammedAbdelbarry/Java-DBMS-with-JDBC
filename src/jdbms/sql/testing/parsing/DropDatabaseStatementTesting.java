package jdbms.sql.testing.parsing;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import jdbms.sql.parsing.parser.StringNormalizer;
import jdbms.sql.parsing.statements.DropDatabaseStatement;
import jdbms.sql.parsing.statements.InitialStatement;
import jdbms.sql.util.ClassRegisteringHelper;

public class DropDatabaseStatementTesting {

	private StringNormalizer normalizer;
	private InitialStatement dropDb;

	@Before
	public void executedBeforeEach() {
		normalizer = new StringNormalizer();
		dropDb = new DropDatabaseStatement();
		ClassRegisteringHelper.registerInitialStatements();
	}

	@Test
	public void testDropDataBase() {
		String SQLCommand = "Drop   database   my_SQLDatabase   ;";
		SQLCommand = normalizer.normalizeCommand(SQLCommand);
		String name = "my_SQLDatabase";
		assertEquals(dropDb.interpret(SQLCommand), true);
		assertEquals(name, dropDb.getParameters().getDatabaseName());
	}

	@Test
	public void testInvalidDropDataBase() {
		String SQLCommand = "Drop my_SQLDatabase   ;";
		SQLCommand = normalizer.normalizeCommand(SQLCommand);
		assertEquals(dropDb.interpret(SQLCommand), false);
	}
}
