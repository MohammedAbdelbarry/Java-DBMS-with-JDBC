package jdbms.sql.parsing.statements;

import jdbms.sql.data.SQLData;
import jdbms.sql.errors.ErrorHandler;
import jdbms.sql.parsing.expressions.DatabaseTerminatingExpression;
import jdbms.sql.parsing.properties.DatabaseDroppingParameters;
import jdbms.sql.parsing.statements.util.InitialStatementFactory;

public class DropDatabaseStatement extends InitialStatement {
	private static final String STATEMENT_IDENTIFIER = "DROP DATABASE";
	private static final String CLASS_ID = "DROPDATABASESTATEMENTCLASS";
	private DatabaseDroppingParameters dropDBParameters;
	static {
		InitialStatementFactory.getInstance().registerStatement(CLASS_ID, DropDatabaseStatement.class);
	}
	public DropDatabaseStatement() {
		dropDBParameters = new DatabaseDroppingParameters();
	}

	@Override
	public boolean interpret(String sqlExpression) {
		if (sqlExpression.startsWith(STATEMENT_IDENTIFIER)) {
			String restOfExpression = sqlExpression.replaceFirst(STATEMENT_IDENTIFIER, "").trim();
			return new DatabaseTerminatingExpression(parameters).interpret(restOfExpression);
		}
		return false;
	}

	@Override
	public void act(SQLData data) {
		buildParameters();
		data.dropDatabase(dropDBParameters);
	}
	private void buildParameters() {
		dropDBParameters.setDatabaseName(parameters.getDatabaseName());
	}
}
