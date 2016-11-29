package jdbms.sql.parsing.parser;

import jdbms.sql.data.SQLData;
import jdbms.sql.errors.ErrorHandler;
import jdbms.sql.parsing.statements.InitialStatement;
import jdbms.sql.parsing.statements.util.InitialStatementFactory;

public class Parser {

	public Parser() {

	}
	public void parse(final String normalizedInput, final SQLData data) {
		boolean correctSyntax = false;
		for (final String key : InitialStatementFactory.
				getInstance().getRegisteredStatements()) {
			final InitialStatement statement =
					InitialStatementFactory.
					getInstance().createStatement(key);
			boolean interpreted;
			try {
				interpreted = statement.interpret(normalizedInput);
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
