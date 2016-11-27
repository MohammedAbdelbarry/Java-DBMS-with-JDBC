package jdbms.sql.parsing.statements;

import jdbms.sql.data.SQLData;
import jdbms.sql.parsing.properties.DeletionParameters;
import jdbms.sql.parsing.statements.util.InitialStatementFactory;

public class DeleteStatement extends InitialStatement {
	private static final String STATEMENT_IDENTIFIER = "DELETE";
	private static final String CLASS_ID = "DELETESTATEMENTCLASS";
	private DeletionParameters deleteParameters;
	static {
		InitialStatementFactory.getInstance().registerStatement(CLASS_ID, DeleteStatement.class);
	}
	public DeleteStatement() {
		deleteParameters = new DeletionParameters();
	}

	@Override
	public boolean interpret(String sqlExpression) {
		if (sqlExpression.startsWith(STATEMENT_IDENTIFIER)) {
			String restOfExpression = sqlExpression.replaceFirst(STATEMENT_IDENTIFIER, "").trim();
			return new FromStatement(parameters).interpret(restOfExpression);
		}
		return false;
	}

	@Override
	public void act(SQLData data) {
		buildParameters();
		data.deleteFrom(deleteParameters);
	}
	private void buildParameters() {
		deleteParameters.setTableName(parameters.getTableName());
		deleteParameters.setCondition(parameters.getCondition());
	}
}
