package jdbms.sql.testing.parsing;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import jdbms.sql.parsing.parser.StringNormalizer;
import jdbms.sql.parsing.statements.InitialStatement;
import jdbms.sql.parsing.statements.UpdateStatement;
import jdbms.sql.util.HelperClass;

public class UpdateStatementTesting {

	private StringNormalizer normalizer;
	private InitialStatement update;

	@Before
	public void executedBeforeEach() {
		normalizer = new StringNormalizer();
		update = new UpdateStatement();
		HelperClass.registerInitialStatements();
	}

	@Test
	public void testUpdate() {
		String SQLCommand = "UPDATE Customers set"
				+ " ContactName='Alfred Schmidt',City  = ' Ham\"bu\"rg' "
				+ "WHERE CustomerName = 'Alfr\"eds Futter\"kiste';";
		SQLCommand = normalizer.normalizeCommand(SQLCommand);
		String name = "Customers";
		assertEquals(update.interpret(SQLCommand), true);
		assertEquals(name, update.getParameters().getTableName());
		assertEquals("ContactName", update.getParameters().getAssignmentList().get(0).getLeftOperand());
		assertEquals("'Alfred Schmidt'", update.getParameters().getAssignmentList().get(0).getRightOperand());
		assertEquals("City", update.getParameters().getAssignmentList().get(1).getLeftOperand());
		assertEquals("' Ham\"bu\"rg'", update.getParameters().getAssignmentList().get(1).getRightOperand());
		assertEquals("CustomerName", update.getParameters().getCondition().getLeftOperand());
		assertEquals("'Alfr\"eds Futter\"kiste'", update.getParameters().getCondition().getRightOperand());
	}

	@Test
	public void testFloatUpdate() {
		String SQLCommand = "UPDATE Customers set float1 = 5.4, float2 = 3.010 WHERE float1 = 2.01;";
		SQLCommand = normalizer.normalizeCommand(SQLCommand);
		String name = "Customers";
		assertEquals(update.interpret(SQLCommand), true);
		assertEquals(name, update.getParameters().getTableName());
		assertEquals("float1", update.getParameters().getAssignmentList().get(0).getLeftOperand());
		assertEquals("5.4", update.getParameters().getAssignmentList().get(0).getRightOperand());
		assertEquals("float2", update.getParameters().getAssignmentList().get(1).getLeftOperand());
		assertEquals("3.010", update.getParameters().getAssignmentList().get(1).getRightOperand());
		assertEquals("float1", update.getParameters().getCondition().getLeftOperand());
		assertEquals("2.01", update.getParameters().getCondition().getRightOperand());
	}

	@Test
	public void testInvalidFloatUpdate1() {
		String SQLCommand = "UPDATE Customers set float1 = .4, float2 = 3.010 WHERE float1 = 2.01;";
		SQLCommand = normalizer.normalizeCommand(SQLCommand);
		assertEquals(update.interpret(SQLCommand), false);
	}

	@Test
	public void testInvalidFloatUpdate2() {
		String SQLCommand = "UPDATE Customers set float1 = 5.4, float2 = 3.010.10 WHERE float1 = 2.01;";
		SQLCommand = normalizer.normalizeCommand(SQLCommand);
		assertEquals(update.interpret(SQLCommand), false);
	}

	@Test
	public void testUpdateAll() {
		String SQLCommand = "UPDATE CUSTOMERS SET ADDRESS='Pune',SALARY=1000;";
		SQLCommand = normalizer.normalizeCommand(SQLCommand);
		String name = "CUSTOMERS";
		assertEquals(update.interpret(SQLCommand), true);
		assertEquals(name, update.getParameters().getTableName());
		assertEquals(null, update.getParameters().getCondition());
		assertEquals("ADDRESS", update.getParameters().getAssignmentList().get(0).getLeftOperand());
		assertEquals("'Pune'", update.getParameters().getAssignmentList().get(0).getRightOperand());
		assertEquals("SALARY", update.getParameters().getAssignmentList().get(1).getLeftOperand());
		assertEquals("1000", update.getParameters().getAssignmentList().get(1).getRightOperand());
	}

	@Test
	public void testInvalidUpdateAll() {
		String SQLCommand = "UPDATE CUSTOMERS ADDRESS='Pune',SALARY=1000;";
		SQLCommand = normalizer.normalizeCommand(SQLCommand);
		assertEquals(update.interpret(SQLCommand), false);
	}
}
