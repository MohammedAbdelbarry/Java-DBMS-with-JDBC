package jdbms.sql.parsing.statements;

import jdbms.sql.data.SQLData;
import jdbms.sql.parsing.expressions.TableUpdateTableNameExpression;
import jdbms.sql.parsing.properties.UpdatingParameters;
import jdbms.sql.parsing.statements.util.InitialStatementFactory;

public class UpdateStatement extends InitialStatement {
	private static final String STATEMENT_IDENTIFIER = "UPDATE";
	private static final String CLASS_ID = "UPDATESTATEMENTCLASS";
	private UpdatingParameters updateParameters;
	static {
		InitialStatementFactory.getInstance().registerStatement(
				CLASS_ID, UpdateStatement.class);
	}
	public UpdateStatement() {
		updateParameters = new UpdatingParameters();
	}

	@Override
	public boolean interpret(String sqlExpression) {
		if (sqlExpression.startsWith(STATEMENT_IDENTIFIER)) {
			String restOfExpression = sqlExpression.replaceFirst(
					STATEMENT_IDENTIFIER, "").trim();
			return new TableUpdateTableNameExpression(
					parameters).interpret(restOfExpression);
		}
		return false;
	}

	@Override
	public void act(SQLData data) {
		buildParameters();
		data.updateTable(updateParameters);
	}
	private void buildParameters() {
		updateParameters.setAssignmentList(parameters.getAssignmentList());
		updateParameters.setCondition(parameters.getCondition());
		updateParameters.setTableName(parameters.getTableName());
	}
}
