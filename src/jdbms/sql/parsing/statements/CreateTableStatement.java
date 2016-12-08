package jdbms.sql.parsing.statements;

import jdbms.sql.data.SQLData;
import jdbms.sql.exceptions.ColumnAlreadyExistsException;
import jdbms.sql.exceptions.InvalidDateFormatException;
import jdbms.sql.exceptions.TableAlreadyExistsException;
import jdbms.sql.parsing.expressions.TableCreationTableNameExpression;
import jdbms.sql.parsing.properties.TableCreationParameters;
import jdbms.sql.parsing.statements.util.InitialStatementFactory;

/**
 * The Class Create Table Statement.
 */
public class CreateTableStatement extends InitialStatement {

	private static final String STATEMENT_IDENTIFIER
	= "CREATE TABLE";
	private static final String CLASS_ID
	= "CREATETABLESTATEMENTCLASS";
	private final TableCreationParameters createTableParameters;
	static {
		InitialStatementFactory.getInstance().
		registerStatement(CLASS_ID,
				CreateTableStatement.class);
	}

	/**
	 * Instantiates a new creates the table statement.
	 */
	public CreateTableStatement() {
		super();
		createTableParameters
		= new TableCreationParameters();
	}

	@Override
	public boolean interpret(final String sqlExpression) {
		if (sqlExpression.
				startsWith(STATEMENT_IDENTIFIER)) {
			final String restOfExpression = sqlExpression.
					replaceFirst(STATEMENT_IDENTIFIER, "").trim();
			return new TableCreationTableNameExpression(parameters).
					interpret(restOfExpression);
		}
		return false;
	}

	@Override
	public void act(final SQLData data)
			throws ColumnAlreadyExistsException,
			TableAlreadyExistsException,
			InvalidDateFormatException {
		buildParameters();
		numberOfUpdates = data.createTable(createTableParameters);
	}

	/**
	 * Builds the parameters.
	 */
	private void buildParameters() {
		createTableParameters.setTableName(parameters.getTableName());
		createTableParameters.setColumnDefinitions(
				parameters.getColumnDefinitions());
	}
}
