package jdbms.sql.parsing.statements;

import jdbms.sql.parsing.expressions.TerminatingTableExpression;
import jdbms.sql.parsing.properties.TableDroppingParameters;
import jdbms.sql.parsing.statements.util.InitialStatementFactory;

public class DropTableStatement extends InitialStatement {
	private static final String STATEMENT_IDENTIFIER = "DROP TABLE";
	private static final String CLASS_ID = "DROPTABLESTATEMENTCLASS";
	TableDroppingParameters dropTableParameters;
	static {
		InitialStatementFactory.getInstance().registerStatement(CLASS_ID, DropTableStatement.class);
	}

	public DropTableStatement() {
		dropTableParameters = new TableDroppingParameters();
	}

	@Override
	public boolean interpret(String sqlExpression) {
		if (sqlExpression.startsWith(STATEMENT_IDENTIFIER)) {
			String restOfExpression = sqlExpression.replace(STATEMENT_IDENTIFIER, "").trim();
			return new TerminatingTableExpression(parameters).interpret(restOfExpression);
		}
		return false;
	}

	@Override
	public void act() {
		buildParameters();
		//System.dropTable(dropTableParameters);
	}
	private void buildParameters() {
		dropTableParameters.setTableName(parameters.getTableName());
	}
}
