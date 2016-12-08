package jdbms.sql.parsing.statements;

import jdbms.sql.data.SQLData;
import jdbms.sql.exceptions.ColumnAlreadyExistsException;
import jdbms.sql.exceptions.ColumnListTooLargeException;
import jdbms.sql.exceptions.ColumnNotFoundException;
import jdbms.sql.exceptions.InvalidDateFormatException;
import jdbms.sql.exceptions.RepeatedColumnException;
import jdbms.sql.exceptions.TableNotFoundException;
import jdbms.sql.exceptions.TypeMismatchException;
import jdbms.sql.exceptions.ValueListTooLargeException;
import jdbms.sql.exceptions.ValueListTooSmallException;
import jdbms.sql.parsing.expressions.TableNameColumnListExpression;
import jdbms.sql.parsing.expressions.TableNameValueListExpression;
import jdbms.sql.parsing.properties.InsertionParameters;
import jdbms.sql.parsing.statements.util.InitialStatementFactory;

/**
 * The Class InsertIntoStatement.
 */
public class InsertIntoStatement extends InitialStatement {

	private static final String STATEMENT_IDENTIFIER
	= "INSERT INTO";
	private static final String CLASS_ID
	= "INSERTINTOSTATEMENTCLASS";
	private final InsertionParameters insertParameters;
	static {
		InitialStatementFactory.getInstance().
		registerStatement(CLASS_ID,
				InsertIntoStatement.class);
	}

	/**
	 * Instantiates a new insert into statement.
	 */
	public InsertIntoStatement() {
		super();
		insertParameters = new InsertionParameters();
	}

	@Override
	public boolean interpret(final String sqlExpression) {
		if (sqlExpression.
				startsWith(STATEMENT_IDENTIFIER)) {
			final String restOfExpression
			= sqlExpression.
			replaceFirst(STATEMENT_IDENTIFIER, "").trim();
			if (new TableNameColumnListExpression(parameters).
					interpret(restOfExpression)
					|| new TableNameValueListExpression(parameters).
					interpret(restOfExpression)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void act(final SQLData data)
			throws ColumnAlreadyExistsException,
			RepeatedColumnException,
			ColumnListTooLargeException,
			ColumnNotFoundException,
			ValueListTooLargeException,
			ValueListTooSmallException,
			TableNotFoundException,
			TypeMismatchException,
			InvalidDateFormatException {
		buildParameters();
		numberOfUpdates = data.insertInto(insertParameters);
	}

	/**
	 * Builds the parameters.
	 */
	private void buildParameters() {
		insertParameters.setColumns(parameters.getColumns());
		insertParameters.setTableName(parameters.getTableName());
		insertParameters.setValues(parameters.getValues());
	}
}
