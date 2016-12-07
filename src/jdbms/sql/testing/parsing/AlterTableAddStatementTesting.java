package jdbms.sql.testing.parsing;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import jdbms.sql.data.ColumnIdentifier;
import jdbms.sql.parsing.parser.StringNormalizer;
import jdbms.sql.parsing.statements.AlterTableAddStatement;
import jdbms.sql.parsing.statements.InitialStatement;
import jdbms.sql.parsing.statements.Statement;
import jdbms.sql.parsing.statements.util.InitialStatementFactory;
import jdbms.sql.util.HelperClass;

public class AlterTableAddStatementTesting {

	private StringNormalizer normalizer;
	private Collection<Statement> statements;
	private InitialStatement alter;

	@Before
	public void executedBeforeEach() {
		normalizer = new StringNormalizer();
		statements = new ArrayList<>();
		alter = new AlterTableAddStatement();
		HelperClass.registerInitialStatements();
		for (String key : InitialStatementFactory.getInstance().getRegisteredStatements()) {
			statements.add(InitialStatementFactory.getInstance().createStatement(key));
		}
	}

	@Test
	public void testAlterTableDropColumn() {
		String SQLCommand = "ALTER TABLE table_name DROP COLUMN column_name;";
		SQLCommand = normalizer.normalizeCommand(SQLCommand);
		assertEquals(alter.interpret(SQLCommand), false);
	}

	@Test
	public void testAlterTableAddColumn() {
		String SQLCommand = "ALTER TABLE table_name ADD column_name INT;";
		SQLCommand = normalizer.normalizeCommand(SQLCommand);
		String name = "table_name";
		ArrayList <ColumnIdentifier> list = new ArrayList<>();
		list.add(new ColumnIdentifier("column_name", "INT"));
		assertEquals(alter.interpret(SQLCommand), true);
		assertEquals(name, alter.getParameters().getTableName());
		assertEquals(list, alter.getParameters().getColumnDefinitions());
	}
}
