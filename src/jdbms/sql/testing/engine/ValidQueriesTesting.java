package jdbms.sql.testing.engine;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import jdbms.sql.data.SQLData;
import jdbms.sql.parsing.parser.StringNormalizer;
import jdbms.sql.parsing.statements.InitialStatement;
import jdbms.sql.parsing.statements.util.InitialStatementFactory;
import jdbms.sql.util.ClassRegisteringHelper;

public class ValidQueriesTesting {

	private StringNormalizer normalizer;
	private Collection<InitialStatement> statements;
	private SQLData data;

	@Before
	public void executedBeforeEach() {
		normalizer = new StringNormalizer();
		statements = new ArrayList<>();
		data = new SQLData();
		ClassRegisteringHelper.registerInitialStatements();
		for (final String key : InitialStatementFactory.
				getInstance().getRegisteredStatements()) {
			statements.add(InitialStatementFactory.
					getInstance().createStatement(key));
		}
	}

	@Test
	public void oneRowInsertion() {
		final String creationCommand = normalizer.
				normalizeCommand("create table t (a int, b float,"
						+ " c date, d datetime, e int); ");
		final String insertionCommand = normalizer.
				normalizeCommand("insert into t values (2, 2.0,"
						+ " 0001-01-01, '0000-01-01 00:00:01', -100);");
		final String selectionCommand = normalizer.
				normalizeCommand("select * from t;");
		final ArrayList<String> commands = new ArrayList<>();
		commands.add(creationCommand);
		commands.add(insertionCommand);
		commands.add(selectionCommand);
		boolean caughtException = false;
		int interpretedCount = 0;
		for (final String currCommand : commands) {
			for (final InitialStatement statement : statements) {
				if (statement.interpret(currCommand)) {
					interpretedCount++;
					try {
						statement.act(data);
					} catch (final Exception e) {
						caughtException = true;
					}
				}
			}
		}

		assertEquals(interpretedCount, 2);
		assertEquals(caughtException, false);
	}
}
