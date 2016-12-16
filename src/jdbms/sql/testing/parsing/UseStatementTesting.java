package jdbms.sql.testing.parsing;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import jdbms.sql.parsing.parser.StringNormalizer;
import jdbms.sql.parsing.statements.InitialStatement;
import jdbms.sql.parsing.statements.UseStatement;
import jdbms.sql.util.ClassRegisteringHelper;

public class UseStatementTesting {

	private StringNormalizer normalizer;
	private InitialStatement use;

	@Before
	public void executedBeforeEach() {
		normalizer = new StringNormalizer();
		use = new UseStatement();
		ClassRegisteringHelper.registerInitialStatements();
	}

	@Test
	public void testUseStatement() {
		String SQLCommand = "Use dbName;";
		SQLCommand = normalizer.normalizeCommand(SQLCommand);
		String name = "dbName";
		assertEquals(use.interpret(SQLCommand), true);
		assertEquals(name, use.getParameters().getDatabaseName());
	}

	@Test
	public void testInvalidUseStatement() {
		String sqlCommand = "Use database dbName;";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		assertEquals(use.interpret(sqlCommand), false);
	}
}
