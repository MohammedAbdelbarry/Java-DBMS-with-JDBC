package jdbms.sql.parsing.statements;

import jdbms.sql.parsing.expressions.DatabaseTerminatingExpression;
import jdbms.sql.parsing.expressions.TableTerminatingExpression;
import jdbms.sql.parsing.statements.util.StatementFactory;

public class CreateTableStatement implements Statement {
	private static final String STATEMENT_IDENTIFIER = "CREATE TABLE";
	private static final String CLASS_ID = "CREATETABLESTATEMENTCLASS";
	static {
		StatementFactory.getInstance().registerStatement(CLASS_ID, CreateTableStatement.class);
	}
	public CreateTableStatement() {
	}

	@Override
	public boolean interpret(String sqlExpresison) {
		if (sqlExpresison.startsWith(STATEMENT_IDENTIFIER)) {
			String restOfExpression = sqlExpresison.replace(STATEMENT_IDENTIFIER, "").trim();
			if (new TableTerminatingExpression())
		}
		return false;
	}
}
