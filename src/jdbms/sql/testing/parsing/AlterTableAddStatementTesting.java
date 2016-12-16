package jdbms.sql.testing.parsing;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import jdbms.sql.data.ColumnIdentifier;
import jdbms.sql.parsing.parser.StringNormalizer;
import jdbms.sql.parsing.statements.AlterTableAddStatement;
import jdbms.sql.parsing.statements.InitialStatement;
import jdbms.sql.util.ClassRegisteringHelper;

public class AlterTableAddStatementTesting {

	private StringNormalizer normalizer;
	private InitialStatement alter;

	@Before
	public void executedBeforeEach() {
		normalizer = new StringNormalizer();
		alter = new AlterTableAddStatement();
		ClassRegisteringHelper.registerInitialStatements();
	}

	@Test
	public void testAlterTableDropColumn() {
		String sqlCommand = "ALTER TABLE table_name DROP COLUMN column_name;";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		assertEquals(alter.interpret(sqlCommand), false);
	}

	@Test
	public void testAlterTableAddColumn() {
		String sqlCommand = "ALTER TABLE table_name ADD column_name INT;";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		String name = "table_name";
		ArrayList <ColumnIdentifier> list = new ArrayList<>();
		list.add(new ColumnIdentifier("column_name", "INT"));
		assertEquals(alter.interpret(sqlCommand), true);
		assertEquals(name, alter.getParameters().getTableName());
		assertEquals(list, alter.getParameters().getColumnDefinitions());
	}

	@Test
	public void testFloatAlterTableAddColumn() {
		String sqlCommand = "ALTER TABLE tableName ADD columnName real;";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		String name = "tableName";
		ArrayList <ColumnIdentifier> list = new ArrayList<>();
		list.add(new ColumnIdentifier("columnName", "REAL"));
		assertEquals(alter.interpret(sqlCommand), true);
		assertEquals(name, alter.getParameters().getTableName());
		assertEquals(list, alter.getParameters().getColumnDefinitions());
	}
}
