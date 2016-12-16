package jdbms.sql.testing.parsing;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import jdbms.sql.parsing.parser.StringNormalizer;
import jdbms.sql.parsing.statements.InitialStatement;
import jdbms.sql.parsing.statements.UpdateStatement;
import jdbms.sql.util.ClassRegisteringHelper;

public class UpdateStatementTesting {

    private StringNormalizer normalizer;
    private InitialStatement update;

    @Before
    public void executedBeforeEach() {
        normalizer = new StringNormalizer();
        update = new UpdateStatement();
        ClassRegisteringHelper.registerInitialStatements();
    }

    @Test
    public void testUpdate() {
        String sqlCommand = "UPDATE Customers set"
                + " ContactName='Alfred Schmidt',City  = ' Ham\"bu\"rg' "
                + "WHERE CustomerName = 'Alfr\"eds Futter\"kiste';";
        sqlCommand = normalizer.normalizeCommand(sqlCommand);
        final String name = "Customers";
        assertEquals(update.interpret(sqlCommand), true);
        assertEquals(name, update.getParameters().getTableName());
        assertEquals("ContactName", update.getParameters().getAssignmentList
                ().get(0).getLeftOperand());
        assertEquals("'Alfred Schmidt'", update.getParameters()
                .getAssignmentList().get(0).getRightOperand());
        assertEquals("City", update.getParameters().getAssignmentList().get
                (1).getLeftOperand());
        assertEquals("' Ham\"bu\"rg'", update.getParameters()
                .getAssignmentList().get(1).getRightOperand());
        assertEquals("CustomerName", update.getParameters().getCondition()
                .getLeftOperand());
        assertEquals("'Alfr\"eds Futter\"kiste'", update.getParameters()
                .getCondition().getRightOperand());
    }

    @Test
    public void testFloatUpdate() {
        String sqlCommand = "UPDATE Customers set float1 = 5.4, float2 = "
                + "3.010 WHERE float1 = 2.01;";
        sqlCommand = normalizer.normalizeCommand(sqlCommand);
        final String name = "Customers";
        assertEquals(update.interpret(sqlCommand), true);
        assertEquals(name, update.getParameters().getTableName());
        assertEquals("float1", update.getParameters().getAssignmentList().get
                (0).getLeftOperand());
        assertEquals("5.4", update.getParameters().getAssignmentList().get(0)
                .getRightOperand());
        assertEquals("float2", update.getParameters().getAssignmentList().get
                (1).getLeftOperand());
        assertEquals("3.010", update.getParameters().getAssignmentList().get
                (1).getRightOperand());
        assertEquals("float1", update.getParameters().getCondition()
                .getLeftOperand());
        assertEquals("2.01", update.getParameters().getCondition()
                .getRightOperand());
    }

    @Test
    public void testDateTimeUpdate() {
        String sqlCommand = "update mytable set "
                + "col3 = 46.466446, col1 = "
                + "'1011-11-11 01:01:01' where col2 = "
                + "'1011-11-11 01:01:01';";
        sqlCommand = normalizer.normalizeCommand(sqlCommand);
        final String name = "mytable";
        assertEquals(update.interpret(sqlCommand), true);
        assertEquals(name, update.getParameters().getTableName());
        assertEquals("col3", update.getParameters().getAssignmentList().get
                (0).getLeftOperand());
        assertEquals("46.466446", update.getParameters().getAssignmentList()
                .get(0).getRightOperand());
        assertEquals("col1", update.getParameters().getAssignmentList().get
                (1).getLeftOperand());
        assertEquals("'1011-11-11 01:01:01'", update.getParameters()
                .getAssignmentList().get(1).getRightOperand());
        assertEquals("col2", update.getParameters().getCondition()
                .getLeftOperand());
        assertEquals("'1011-11-11 01:01:01'", update.getParameters()
                .getCondition().getRightOperand());
    }

    @Test
    public void testDateTimeConditionalUpdate() {
        String sqlCommand = "update tt set d = '1010-10-10' where dt > "
                + "'1111-11-10 02:02:01' order by d, f, l;";
        sqlCommand = normalizer.normalizeCommand(sqlCommand);
        final String name = "tt";
        assertEquals(update.interpret(sqlCommand), true);
        assertEquals(name, update.getParameters().getTableName());
        assertEquals("d", update.getParameters().getAssignmentList().get(0)
                .getLeftOperand());
        assertEquals("'1010-10-10'", update.getParameters().getAssignmentList
                ().get(0).getRightOperand());
        assertEquals("dt", update.getParameters().getCondition()
                .getLeftOperand());
        assertEquals("'1111-11-10 02:02:01'", update.getParameters()
                .getCondition().getRightOperand());
    }

    @Test
    public void testInvalidFloatUpdate1() {
        String sqlCommand = "UPDATE Customers set float1 = .4, float2 = "
                + "3.0.10 WHERE float1 = 2.01;";
        sqlCommand = normalizer.normalizeCommand(sqlCommand);
        assertEquals(update.interpret(sqlCommand), false);
    }

    @Test
    public void testInvalidFloatUpdate2() {
        String sqlCommand = "UPDATE Customers set float1 = 5.4, float2 = "
                + "3.010.10 WHERE float1 = 2.01;";
        sqlCommand = normalizer.normalizeCommand(sqlCommand);
        assertEquals(update.interpret(sqlCommand), false);
    }

    @Test
    public void testUpdateAll() {
        String sqlCommand = "UPDATE CUSTOMERS SET ADDRESS='Pune',SALARY=1000;";
        sqlCommand = normalizer.normalizeCommand(sqlCommand);
        final String name = "CUSTOMERS";
        assertEquals(update.interpret(sqlCommand), true);
        assertEquals(name, update.getParameters().getTableName());
        assertEquals(null, update.getParameters().getCondition());
        assertEquals("ADDRESS", update.getParameters().getAssignmentList()
                .get(0).getLeftOperand());
        assertEquals("'Pune'", update.getParameters().getAssignmentList().get
                (0).getRightOperand());
        assertEquals("SALARY", update.getParameters().getAssignmentList().get
                (1).getLeftOperand());
        assertEquals("1000", update.getParameters().getAssignmentList().get
                (1).getRightOperand());
    }

    @Test
    public void testInvalidUpdateAll() {
        String sqlCommand = "UPDATE CUSTOMERS ADDRESS='Pune',SALARY=1000;";
        sqlCommand = normalizer.normalizeCommand(sqlCommand);
        assertEquals(update.interpret(sqlCommand), false);
    }
}
