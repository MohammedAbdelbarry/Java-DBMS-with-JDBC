package jdbms.sql.testing.parsing;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import jdbms.sql.parsing.parser.StringNormalizer;
import jdbms.sql.parsing.statements.DropTableStatement;
import jdbms.sql.parsing.statements.InitialStatement;
import jdbms.sql.util.ClassRegisteringHelper;

public class DropTableStatementTesting {

    private StringNormalizer normalizer;
    private InitialStatement dropTable;

    @Before
    public void executedBeforeEach() {
        normalizer = new StringNormalizer();
        dropTable = new DropTableStatement();
        ClassRegisteringHelper.registerInitialStatements();
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
