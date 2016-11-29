package jdbms.sql.parsing.statements;

import jdbms.sql.data.SQLData;
import jdbms.sql.parsing.expressions.TerminatingTableExpression;
import jdbms.sql.parsing.properties.TableDroppingParameters;
import jdbms.sql.parsing.statements.util.InitialStatementFactory;

/**
 * The Class DropTableStatement.
 */
public class DropTableStatement extends InitialStatement {
	
	private static final String STATEMENT_IDENTIFIER
	= "DROP TABLE";
	private static final String CLASS_ID
	= "DROPTABLESTATEMENTCLASS";	
	private TableDroppingParameters dropTableParameters;
	static {
		InitialStatementFactory.
		getInstance().
		registerStatement(CLASS_ID,
				DropTableStatement.class);
	}

	/**
	 * Instantiates a new drop table statement.
	 */
	public DropTableStatement() {
		dropTableParameters = new TableDroppingParameters();
	}

	@Override
	public boolean interpret(String sqlExpression) {
		if (sqlExpression.startsWith(STATEMENT_IDENTIFIER)) {
			String restOfExpression = sqlExpression.replaceFirst(STATEMENT_IDENTIFIER, "").trim();
			return new TerminatingTableExpression(parameters).interpret(restOfExpression);
		}
		return false;
	}

	@Override
	public void act(SQLData data) {
		buildParameters();
		data.dropTable(dropTableParameters);
	}
	
	/**
	 * Builds the parameters.
	 */
	private void buildParameters() {
		dropTableParameters.setTableName(parameters.getTableName());
	}
}
