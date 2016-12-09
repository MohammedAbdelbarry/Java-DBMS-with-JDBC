package jdbms.sql.testing.parsing;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import jdbms.sql.parsing.parser.StringNormalizer;
import jdbms.sql.parsing.statements.AlterTableDropStatement;
import jdbms.sql.parsing.statements.InitialStatement;
import jdbms.sql.util.HelperClass;

public class AlterTableDropStatementTesting {

	private StringNormalizer normalizer;
	private InitialStatement alterDrop;

	@Before
	public void executedBeforeEach() {
		normalizer = new StringNormalizer();
		alterDrop = new AlterTableDropStatement();
		HelperClass.registerInitialStatements();
	}
	@Test
	public void testAlterTableDropColumn() {
		String SQLCommand = "ALTER TABLE table_name DROP COLUMN col1, col2, col3;";
		SQLCommand = normalizer.normalizeCommand(SQLCommand);
		final String name = "table_name";
		final ArrayList <String> list = new ArrayList<>();
		list.add("col1");
		list.add("col2");
		list.add("col3");
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
