package jdbms.sql.parsing.statements;

import jdbms.sql.data.SQLData;
import jdbms.sql.exceptions.DatabaseNotFoundException;
import jdbms.sql.exceptions.TableAlreadyExistsException;
import jdbms.sql.parsing.expressions.database.DatabaseTerminatingExpression;
import jdbms.sql.parsing.properties.UseParameters;
import jdbms.sql.parsing.statements.util.InitialStatementFactory;

/**
 * The Class UseStatement.
 */
public class UseStatement extends InitialStatement{

	private static final String STATEMENT_IDENTIFIER = "USE";
	private static final String CLASS_ID = "USESTATEMENTCLASS";
	private final UseParameters useParameters;
	static {
		InitialStatementFactory.getInstance().registerStatement(
				CLASS_ID, UseStatement.class);
	}

	/**
	 * Instantiates a new use statement.
	 */
	public UseStatement() {
		super();
		useParameters = new UseParameters();
	}

	@Override
	public boolean interpret(final String sqlExpression) {
		if (sqlExpression.startsWith(STATEMENT_IDENTIFIER)) {
			final String restOfExpression
			= sqlExpression.
			replaceFirst(STATEMENT_IDENTIFIER, "").trim();
			return new DatabaseTerminatingExpression(parameters).
					interpret(restOfExpression);
		}
		return false;
	}

	@Override
	public void act(final SQLData data)
			throws DatabaseNotFoundException,
			TableAlreadyExistsException {
		buildParameters();
		numberOfUpdates = data.setActiveDatabase(useParameters);
	}

	/**
	 * Builds the parameters.
	 */
	private void buildParameters() {
		useParameters.
		setDatabaseName(parameters.getDatabaseName());
	}
}
