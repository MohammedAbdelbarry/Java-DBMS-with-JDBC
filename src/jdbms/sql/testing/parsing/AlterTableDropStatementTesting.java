package jdbms.sql.testing.parsing;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import jdbms.sql.parsing.parser.StringNormalizer;
import jdbms.sql.parsing.statements.AlterTableDropStatement;
import jdbms.sql.parsing.statements.InitialStatement;
import jdbms.sql.parsing.statements.Statement;
import jdbms.sql.parsing.statements.util.InitialStatementFactory;
import jdbms.sql.util.HelperClass;

public class AlterTableDropStatementTesting {

	private StringNormalizer normalizer;
	private Collection<Statement> statements;
	private InitialStatement alterDrop;

	@Before
	public void executedBeforeEach() {
		normalizer = new StringNormalizer();
		statements = new ArrayList<>();
		alterDrop = new AlterTableDropStatement();
		HelperClass.registerInitialStatements();
		for (String key : InitialStatementFactory.getInstance().getRegisteredStatements()) {
			statements.add(InitialStatementFactory.getInstance().createStatement(key));
		}
	}
	@Test
	public void testAlterTableDropColumn() {
		String SQLCommand = "ALTER TABLE table_name DROP COLUMN column_name;";
		SQLCommand = normalizer.normalizeCommand(SQLCommand);
		String name = "table_name";
		ArrayList <String> list = new ArrayList<>();
		list.add("column_name");
		assertEquals(alterDrop.interpret(SQLCommand), true);
		assertEquals(name, alterDrop.getParameters().getTableName());
		assertEquals(list, alterDrop.getParameters().getColumns());
	}

	@Test
	public void testAlterTableAddColumn() {
		String SQLCommand = "ALTER TABLE table_name ADD column_name INT;";
		SQLCommand = normalizer.normalizeCommand(SQLCommand);
		assertEquals(alterDrop.interpret(SQLCommand), false);
	}
}
