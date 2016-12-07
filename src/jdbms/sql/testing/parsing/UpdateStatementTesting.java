package jdbms.sql.testing.parsing;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import jdbms.sql.parsing.parser.StringNormalizer;
import jdbms.sql.parsing.statements.InitialStatement;
import jdbms.sql.parsing.statements.Statement;
import jdbms.sql.parsing.statements.UpdateStatement;
import jdbms.sql.parsing.statements.util.InitialStatementFactory;
import jdbms.sql.util.HelperClass;

public class UpdateStatementTesting {

	private StringNormalizer normalizer;
	private Collection<Statement> statements;
	private InitialStatement update;

	@Before
	public void executedBeforeEach() {
		normalizer = new StringNormalizer();
		statements = new ArrayList<>();
		update = new UpdateStatement();
		HelperClass.registerInitialStatements();
		for (String key : InitialStatementFactory.getInstance().getRegisteredStatements()) {
			statements.add(InitialStatementFactory.getInstance().createStatement(key));
		}
	}

	@Test
	public void testUpdate() {
		String SQLCommand = "UPDATE Customers set"
				+ " ContactName='Alfred Schmidt',City  = ' Ham\"bu\"rg' WHERE CustomerName = 'Alfr\"eds Futter\"kiste';";
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
