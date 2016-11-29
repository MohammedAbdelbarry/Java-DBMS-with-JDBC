package jdbms.sql.parsing.parser.testing;

import java.io.IOException;
import java.util.Scanner;

import jdbms.sql.data.SQLData;
import jdbms.sql.errors.ErrorHandler;
import jdbms.sql.parsing.expressions.util.StringModifier;
import jdbms.sql.parsing.parser.Parser;
import jdbms.sql.parsing.statements.InitialStatement;
import jdbms.sql.parsing.statements.util.InitialStatementFactory;

public class TestingMain {
	private static final String QUIT = "QUIT;";
	public TestingMain() {

	}

	public static void main(final String[] args) throws IOException {
		try {
			Class.forName("jdbms.sql.parsing.statements.CreateDatabaseStatement");
			Class.forName("jdbms.sql.parsing.statements.CreateTableStatement");
			Class.forName("jdbms.sql.parsing.statements.DropDatabaseStatement");
			Class.forName("jdbms.sql.parsing.statements.DropTableStatement");
			Class.forName("jdbms.sql.parsing.statements.InsertIntoStatement");
			Class.forName("jdbms.sql.parsing.statements.DeleteStatement");
			Class.forName("jdbms.sql.parsing.statements.SelectStatement");
			Class.forName("jdbms.sql.parsing.statements.UpdateStatement");
			Class.forName("jdbms.sql.parsing.statements.UseStatement");
			Class.forName("jdbms.sql.parsing.statements.AlterTableStatement");
			Class.forName("jdbms.sql.parsing.expressions.math.EqualsExpression");
			Class.forName("jdbms.sql.parsing.expressions.math.LargerThanEqualsExpression");
			Class.forName("jdbms.sql.parsing.expressions.math.LessThanEqualsExpression");
			Class.forName("jdbms.sql.parsing.expressions.math.LargerThanExpression");
			Class.forName("jdbms.sql.parsing.expressions.math.LessThanExpression");
			Class.forName("jdbms.sql.parsing.expressions.math.NotEqualsExpression");
			Class.forName("jdbms.sql.datatypes.IntSQLType");
			Class.forName("jdbms.sql.datatypes.VarcharSQLType");
		} catch (final ClassNotFoundException e) {
			System.err.println("Internal Error");
		}
		final SQLData data = new SQLData();
		final Parser parser = new Parser();
		final Scanner in = new Scanner(System.in);
		while (true) {
			final StringBuilder stringBuilder = new StringBuilder();
			String sql = null;
			boolean invalid = false;
			final StringModifier modifier = new StringModifier();
			while (in.hasNextLine()) {
				stringBuilder.append(in.nextLine());
				String modifiedExpression = null;
				try {
					modifiedExpression = modifier.
							modifyString(stringBuilder.toString()).trim();
					if (modifiedExpression.indexOf(";") != -1) {
						sql = stringBuilder.substring(0,
								modifiedExpression.indexOf(";") + 1);
						break;
					}
				} catch (final IndexOutOfBoundsException e) {
					invalid = true;
					break;
				}
			}
			if (invalid) {
				ErrorHandler.printSyntaxError();
				continue;
			}
			if (sql.trim().toUpperCase().equals(QUIT)) {
				in.close();
				break;
			}
			String normalizedOutput;
			try {
				normalizedOutput = parser.normalizeCommand(sql);
			} catch (final IndexOutOfBoundsException e) {
				ErrorHandler.printSyntaxError();
				continue;
			}
			boolean correctSyntax = false;
			for (final String key : InitialStatementFactory.getInstance().getRegisteredStatements()) {
				final InitialStatement statement =
						InitialStatementFactory.
						getInstance().createStatement(key);
				boolean interpreted;
				try {
					interpreted = statement.interpret(normalizedOutput);
				} catch (final Exception e) {
					ErrorHandler.printSyntaxError();
					break;
				}
				correctSyntax = correctSyntax || interpreted;
				if (interpreted) {
					try {
						statement.act(data);
					} catch (final Exception e) {
						ErrorHandler.printInternalError();
						break;
					}
				}
			}
			if (!correctSyntax) {
				ErrorHandler.printSyntaxError();
			}
		}
	}
}
