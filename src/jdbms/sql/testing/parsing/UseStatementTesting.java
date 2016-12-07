package jdbms.sql.testing.parsing;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import jdbms.sql.parsing.parser.StringNormalizer;
import jdbms.sql.parsing.statements.InitialStatement;
import jdbms.sql.parsing.statements.Statement;
import jdbms.sql.parsing.statements.UseStatement;
import jdbms.sql.parsing.statements.util.InitialStatementFactory;
import jdbms.sql.util.HelperClass;

public class UseStatementTesting {

	private StringNormalizer normalizer;
	private Collection<Statement> statements;
	private InitialStatement use;

	@Before
	public void executedBeforeEach() {
		normalizer = new StringNormalizer();
		statements = new ArrayList<>();
		use = new UseStatement();
		HelperClass.registerInitialStatements();
		for (String key : InitialStatementFactory.getInstance().getRegisteredStatements()) {
			statements.add(InitialStatementFactory.getInstance().createStatement(key));
		}
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
