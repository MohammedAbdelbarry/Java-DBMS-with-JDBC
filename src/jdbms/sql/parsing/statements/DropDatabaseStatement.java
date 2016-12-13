package jdbms.sql.parsing.statements;

import jdbms.sql.data.SQLData;
import jdbms.sql.exceptions.DatabaseNotFoundException;
import jdbms.sql.exceptions.FailedToDeleteDatabaseException;
import jdbms.sql.exceptions.FailedToDeleteTableException;
import jdbms.sql.exceptions.TableNotFoundException;
import jdbms.sql.parsing.expressions.database.DatabaseTerminatingExpression;
import jdbms.sql.parsing.properties.DatabaseDroppingParameters;
import jdbms.sql.parsing.statements.util.InitialStatementFactory;

/**
 * The Class Drop Database Statement.
 */
public class DropDatabaseStatement extends InitialStatement {

	private static final String STATEMENT_IDENTIFIER = "DROP DATABASE";
	private static final String CLASS_ID = "DROPDATABASESTATEMENTCLASS";
	private final DatabaseDroppingParameters dropDBParameters;
	static {
		InitialStatementFactory.getInstance().
		registerStatement(CLASS_ID,
				DropDatabaseStatement.class);
	}

	/**
	 * Instantiates a new drop database statement.
	 */
	public DropDatabaseStatement() {
		super();
		dropDBParameters
		= new DatabaseDroppingParameters();
	}

	@Override
	public boolean interpret(final String sqlExpression) {
		if (sqlExpression.startsWith(STATEMENT_IDENTIFIER)) {
			final String restOfExpression = sqlExpression.
					replaceFirst(STATEMENT_IDENTIFIER,
							"").trim();
			return new DatabaseTerminatingExpression(parameters).
					interpret(restOfExpression);
		}
		return false;
	}

	@Override
	public void act(final SQLData data)
			throws DatabaseNotFoundException,
			FailedToDeleteDatabaseException,
			TableNotFoundException,
			FailedToDeleteTableException {
		buildParameters();
		numberOfUpdates = data.dropDatabase(dropDBParameters);
	}

	/**
	 * Builds the parameters.
	 */
	private void buildParameters() {
		dropDBParameters.setDatabaseName(parameters.getDatabaseName());
	}
}
