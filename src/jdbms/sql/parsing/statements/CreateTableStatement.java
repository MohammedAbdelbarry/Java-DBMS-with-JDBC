package jdbms.sql.parsing.statements;

import jdbms.sql.parsing.expressions.TableCreationTableNameExpression;
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
	public boolean interpret(String sqlExpression) {
		if (sqlExpression.startsWith(STATEMENT_IDENTIFIER)) {
			String restOfExpression = sqlExpression.replace(STATEMENT_IDENTIFIER, "").trim();
			return new TableCreationTableNameExpression().interpret(restOfExpression);
		}
		return false;
	}
}
