package jdbms.sql.parsing.statements;

import jdbms.sql.parsing.expressions.DatabaseTerminatingExpression;
import jdbms.sql.parsing.properties.UseParameters;
import jdbms.sql.parsing.statements.util.InitialStatementFactory;

public class UseStatement extends InitialStatement{
	private static final String STATEMENT_IDENTIFIER = "USE";
	private static final String CLASS_ID = "USESTATEMENTCLASS";
	private UseParameters useParameters;
	static {
		InitialStatementFactory.getInstance().registerStatement(
				CLASS_ID, UpdateStatement.class);
	}
	public UseStatement() {
		useParameters = new UseParameters();
	}
	@Override
	public boolean interpret(String sqlExpression) {
		if (sqlExpression.startsWith(STATEMENT_IDENTIFIER)) {
			String restOfExpression
			= sqlExpression.replace(STATEMENT_IDENTIFIER, "").trim();
			return new DatabaseTerminatingExpression(parameters).
					interpret(restOfExpression);
		}
		return false;
	}
	@Override
	public void act() {
		buildParameters();
		//System.dropDatabase(useParameters);
	}
	private void buildParameters() {
		useParameters.setDatabaseName(parameters.getDatabaseName());
	}

}
