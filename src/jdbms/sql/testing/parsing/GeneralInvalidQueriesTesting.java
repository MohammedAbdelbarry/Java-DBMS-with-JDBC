package jdbms.sql.testing.parsing;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import jdbms.sql.parsing.parser.StringNormalizer;
import jdbms.sql.parsing.statements.InitialStatement;
import jdbms.sql.parsing.statements.util.InitialStatementFactory;
import jdbms.sql.util.ClassRegisteringHelper;

public class GeneralInvalidQueriesTesting {

    private StringNormalizer normalizer;
    private Collection<InitialStatement> statements;

    @Before
    public void executedBeforeEach() {
        normalizer = new StringNormalizer();
        statements = new ArrayList<>();
        ClassRegisteringHelper.registerInitialStatements();
        for (final String key : InitialStatementFactory.
                getInstance().getRegisteredStatements()) {
            statements.add(InitialStatementFactory.
                    getInstance().createStatement(key));
        }
    }

    @Test
    public void testMisplacedSemiColon() {
        String sqlCommand = "alter table add col; int";
        sqlCommand = normalizer.normalizeCommand(sqlCommand);
        int interpretedCount = 0;
        for (final InitialStatement statement : statements) {
            if (statement.interpret(sqlCommand)) {
                interpretedCount++;
            }
        }
        assertEquals(interpretedCount, 0);
    }

    @Test
    public void testMissingSemiColon() {
        String sqlCommand = "alter table add col int";
        sqlCommand = normalizer.normalizeCommand(sqlCommand);
        int interpretedCount = 0;
        for (final InitialStatement statement : statements) {
            if (statement.interpret(sqlCommand)) {
                interpretedCount++;
            }
        }
        assertEquals(interpretedCount, 0);
    }

    @Test
    public void testInvalidUse() {
        String sqlCommand = "use database db;";
        sqlCommand = normalizer.normalizeCommand(sqlCommand);
        int interpretedCount = 0;
        for (final InitialStatement statement : statements) {
            if (statement.interpret(sqlCommand)) {
                interpretedCount++;
            }
        }
        assertEquals(interpretedCount, 0);
    }

    @Test
    public void testInvalidTableName() {
        String sqlCommand = "drop table my!table;";
        sqlCommand = normalizer.normalizeCommand(sqlCommand);
        int interpretedCount = 0;
        for (final InitialStatement statement : statements) {
            if (statement.interpret(sqlCommand)) {
                interpretedCount++;
            }
        }
        assertEquals(interpretedCount, 0);
    }

    @Test
    public void testMissingKeyword() {
        String sqlCommand = "drop mytable;";
        sqlCommand = normalizer.normalizeCommand(sqlCommand);
        int interpretedCount = 0;
        for (final InitialStatement statement : statements) {
            if (statement.interpret(sqlCommand)) {
                interpretedCount++;
            }
        }
        assertEquals(interpretedCount, 0);
    }

    @Test
    public void testInvalidFloat() {
        String sqlCommand = "update tb set c = 1.5a3;";
        sqlCommand = normalizer.normalizeCommand(sqlCommand);
        int interpretedCount = 0;
        for (final InitialStatement statement : statements) {
            if (statement.interpret(sqlCommand)) {
                interpretedCount++;
            }
        }
        assertEquals(interpretedCount, 0);
    }

    @Test
    public void testInvalidDateDay() {
        String sqlCommand = "update tb set col = 1111-12-32;";
        sqlCommand = normalizer.normalizeCommand(sqlCommand);
        int interpretedCount = 0;
        for (final InitialStatement statement : statements) {
            if (statement.interpret(sqlCommand)) {
                interpretedCount++;
            }
        }
        assertEquals(interpretedCount, 0);
    }

    @Test
    public void testInvalidDateMonth() {
        String sqlCommand = "update tb set col = 1111-13-31;";
        sqlCommand = normalizer.normalizeCommand(sqlCommand);
        int interpretedCount = 0;
        for (final InitialStatement statement : statements) {
            if (statement.interpret(sqlCommand)) {
                interpretedCount++;
            }
        }
        assertEquals(interpretedCount, 0);
    }
}
