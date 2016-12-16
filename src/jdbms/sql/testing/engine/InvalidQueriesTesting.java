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

public class InvalidQueriesTesting {

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
	public void testInvalidInsertInto() {
		String sqlCommand = "insert into t (a, b) values (1, 2, 3);";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		int interpretedCount = 0;
		boolean caughtException = false;
		for (final InitialStatement statement : statements) {
			if (statement.interpret(sqlCommand)) {
				interpretedCount++;
				try {
					statement.act(data);
				} catch (final Exception e) {
					caughtException = true;
				}
			}
		}
		assertEquals(interpretedCount, 1);
		assertEquals(caughtException, true);
	}

	@Test
	public void testColumn() {
		String sqlCommand = "insert into t (a, b) values (1, 2, 3);";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		int interpretedCount = 0;
		boolean caughtException = false;
		for (final InitialStatement statement : statements) {
			if (statement.interpret(sqlCommand)) {
				interpretedCount++;
				try {
					statement.act(data);
				} catch (final Exception e) {
					caughtException = true;
				}
			}
		}
		assertEquals(interpretedCount, 1);
		assertEquals(caughtException, true);
	}
}
