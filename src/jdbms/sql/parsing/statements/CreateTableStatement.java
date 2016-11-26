package jdbms.sql.parsing.statements;

import jdbms.sql.data.SQLData;
import jdbms.sql.parsing.expressions.TableCreationTableNameExpression;
import jdbms.sql.parsing.properties.TableCreationParameters;
import jdbms.sql.parsing.statements.util.InitialStatementFactory;

public class CreateTableStatement extends InitialStatement {
	private static final String STATEMENT_IDENTIFIER = "CREATE TABLE";
	private static final String CLASS_ID = "CREATETABLESTATEMENTCLASS";
	private TableCreationParameters createTableParameters;
	static {
		InitialStatementFactory.getInstance().registerStatement(CLASS_ID, CreateTableStatement.class);
	}
	public CreateTableStatement() {
		createTableParameters = new TableCreationParameters();
	}

	@Override
	public boolean interpret(String sqlExpression) {
		if (sqlExpression.startsWith(STATEMENT_IDENTIFIER)) {
			String restOfExpression = sqlExpression.replace(STATEMENT_IDENTIFIER, "").trim();
			return new TableCreationTableNameExpression(parameters).interpret(restOfExpression);
		}
		return false;
	}

	@Override
	public void act(SQLData data) {
		buildParameters();
		data.createTable(createTableParameters);
	}
	private void buildParameters() {
		createTableParameters.setTableName(parameters.getTableName());
		createTableParameters.setColumnDefinitions(
				parameters.getColumnDefinitions());
	}
}
