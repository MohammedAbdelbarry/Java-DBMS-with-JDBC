package jdbms.sql.testing.parsing;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import jdbms.sql.parsing.parser.StringNormalizer;
import jdbms.sql.parsing.statements.CreateDatabaseStatement;
import jdbms.sql.parsing.statements.InitialStatement;
import jdbms.sql.util.ClassRegisteringHelper;

public class CreateDatabaseStatementTesting {

	private StringNormalizer normalizer;
	private InitialStatement createDb;

	@Before
	public void executedBeforeEach() {
		normalizer = new StringNormalizer();
		createDb = new CreateDatabaseStatement();
		ClassRegisteringHelper.registerInitialStatements();
	}

	@Test
	public void testCreateDataBase() {
		String sqlCommand = "   CREATE    DAtAbASE my_database  ;  ";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		String name = "my_database";
		assertEquals(createDb.interpret(sqlCommand), true);
		assertEquals(name, createDb.getParameters().getDatabaseName());
		
	}

	@Test
	public void testInvalidCreateDataBase() {
		String sqlCommand = "CREATE my_database  ;";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		assertEquals(createDb.interpret(sqlCommand), false);
	}
}
