package jdbms.sql.parsing.statements;

import jdbms.sql.parsing.expressions.ColumnWildcardExpression;
import jdbms.sql.parsing.expressions.SelectColumnListExpression;
import jdbms.sql.parsing.properties.SelectionParameters;
import jdbms.sql.parsing.statements.util.InitialStatementFactory;

public class SelectStatement extends InitialStatement {
	private static final String STATEMENT_IDENTIFIER = "SELECT";
	private static final String CLASS_ID = "SELECTSTATEMENTCLASS";
	private SelectionParameters selectParameters;
	static {
		InitialStatementFactory.getInstance().registerStatement(CLASS_ID, SelectStatement.class);
	}
	public SelectStatement() {
		selectParameters = new SelectionParameters();
	}

	@Override
	public boolean interpret(String sqlExpression) {
		if (sqlExpression.startsWith(STATEMENT_IDENTIFIER)) {
			String restOfExpression = sqlExpression.replace(
					STATEMENT_IDENTIFIER, "").trim();
			if (new ColumnWildcardExpression(
					parameters).interpret(restOfExpression) ||
					new SelectColumnListExpression(
							parameters).interpret(restOfExpression)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void act() {
		buildParameters();
		//System.select(selectParameters);
	}
	private void buildParameters() {
		selectParameters.setColumns(parameters.getColumns());
		selectParameters.setTableName(parameters.getTableName());
		selectParameters.setCondition(parameters.getCondition());
	}
}
