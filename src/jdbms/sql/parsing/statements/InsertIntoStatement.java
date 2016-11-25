package jdbms.sql.parsing.statements;

import jdbms.sql.parsing.expressions.TableNameColumnListExpression;
import jdbms.sql.parsing.expressions.TableNameValueListExpression;
import jdbms.sql.parsing.properties.InsertionParameters;
import jdbms.sql.parsing.statements.util.InitialStatementFactory;

public class InsertIntoStatement extends InitialStatement {
	private static final String STATEMENT_IDENTIFIER = "INSERT INTO";
	private static final String CLASS_ID = "INSERTINTOSTATEMENTCLASS";
	private InsertionParameters insertParameters;
	static {
		InitialStatementFactory.getInstance().registerStatement(CLASS_ID, InsertIntoStatement.class);
	}
	public InsertIntoStatement() {
		insertParameters = new InsertionParameters();
	}

	@Override
	public boolean interpret(String sqlExpression) {
		if (sqlExpression.startsWith(STATEMENT_IDENTIFIER)) {
			String restOfExpression = sqlExpression.replace(STATEMENT_IDENTIFIER, "").trim();
			if (new TableNameColumnListExpression(parameters).interpret(restOfExpression) ||
					new TableNameValueListExpression(parameters).interpret(restOfExpression)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void act() {
		buildParameters();
		//System.insert(insertParameters);
	}
	private void buildParameters() {
		insertParameters.setColumns(parameters.getColumns());
		insertParameters.setTableName(parameters.getTableName());
		insertParameters.setValues(parameters.getValues());
	}
}
