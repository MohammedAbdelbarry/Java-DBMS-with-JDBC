package jdbms.sql.parsing.statements;

import jdbms.sql.data.SQLData;
import jdbms.sql.parsing.expressions.AddColumnTableNameExpression;
import jdbms.sql.parsing.statements.util.InitialStatementFactory;

public class AlterTableStatement extends InitialStatement {

	private static final String STATEMENT_IDENTIFIER
	= "ALTER TABLE";
	private static final String CLASS_ID
	= "ALTERTABLESTATEMENTCLASS";
	//private AddColumnParameters addColumnParameters;
	static {
		InitialStatementFactory.getInstance().
		registerStatement(CLASS_ID, AlterTableStatement.class);
	}
	public AlterTableStatement() {
		//addColumnParameters = new AddColumnParameters();
	}

	@Override
	public boolean interpret(String sqlExpression) {
		if (sqlExpression.startsWith(STATEMENT_IDENTIFIER)) {
			String restOfExpression = sqlExpression.replaceFirst(
					STATEMENT_IDENTIFIER, "").trim();
			return new AddColumnTableNameExpression(parameters).
					interpret(restOfExpression);
		}
		return false;
	}

	@Override
	public void act(SQLData data) {
		// TODO Auto-generated method stub
		
	}
}
