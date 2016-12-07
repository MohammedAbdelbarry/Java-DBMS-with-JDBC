package jdbms.sql.testing.parsing;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import jdbms.sql.parsing.parser.StringNormalizer;
import jdbms.sql.parsing.statements.DropTableStatement;
import jdbms.sql.parsing.statements.InitialStatement;
import jdbms.sql.parsing.statements.Statement;
import jdbms.sql.parsing.statements.util.InitialStatementFactory;
import jdbms.sql.util.HelperClass;

public class DropTableStatementTesting {

	private StringNormalizer normalizer;
	private Collection<Statement> statements;
	private InitialStatement dropTable;

	@Before
	public void executedBeforeEach() {
		normalizer = new StringNormalizer();
		statements = new ArrayList<>();
		dropTable = new DropTableStatement();
		HelperClass.registerInitialStatements();
		for (String key : InitialStatementFactory.getInstance().getRegisteredStatements()) {
			statements.add(InitialStatementFactory.getInstance().createStatement(key));
		}
	}

	@Test
	public void testDropTable() {
		String SQLCommand = "drop table My_SQLDatabase ;";
		SQLCommand = normalizer.normalizeCommand(SQLCommand);
		String name = "My_SQLDatabase";
		assertEquals(dropTable.interpret(SQLCommand), true);
		assertEquals(name, dropTable.getParameters().getTableName());
	}

	@Test
	public void testInvalidDropTable() {
		String SQLCommand = "drop My_SQLDatabase ;";
		SQLCommand = normalizer.normalizeCommand(SQLCommand);
		assertEquals(dropTable.interpret(SQLCommand), false);
	}
}
