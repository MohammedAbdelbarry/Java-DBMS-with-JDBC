package jdbms.sql.parsing.statements;

import jdbms.sql.data.SQLData;
import jdbms.sql.parsing.expressions.DatabaseTerminatingExpression;
import jdbms.sql.parsing.properties.UseParameters;
import jdbms.sql.parsing.statements.util.InitialStatementFactory;

/**
 * The Class UseStatement.
 */
public class UseStatement extends InitialStatement{
	
	private static final String STATEMENT_IDENTIFIER = "USE";
	private static final String CLASS_ID = "USESTATEMENTCLASS";
	private UseParameters useParameters;
	static {
		InitialStatementFactory.getInstance().registerStatement(
				CLASS_ID, UseStatement.class);
	}
	
	/**
	 * Instantiates a new use statement.
	 */
	public UseStatement() {
		useParameters = new UseParameters();
	}

	@Override
	public boolean interpret(String sqlExpression) {
		if (sqlExpression.startsWith(STATEMENT_IDENTIFIER)) {
			String restOfExpression
			= sqlExpression.
			replaceFirst(STATEMENT_IDENTIFIER, "").trim();
			return new DatabaseTerminatingExpression(parameters).
					interpret(restOfExpression);
		}
		return false;
	}
	
	@Override
	public void act(SQLData data) {
		buildParameters();
		data.setActiveDatabase(useParameters);
	}
	
	/**
	 * Builds the parameters.
	 */
	private void buildParameters() {
		useParameters.
		setDatabaseName(parameters.getDatabaseName());
	}
}
