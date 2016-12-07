package jdbms.sql.parsing.statements;


import jdbms.sql.data.SQLData;
import jdbms.sql.data.query.SelectQueryOutput;
import jdbms.sql.exceptions.ColumnAlreadyExistsException;
import jdbms.sql.exceptions.ColumnListTooLargeException;
import jdbms.sql.exceptions.ColumnNotFoundException;
import jdbms.sql.exceptions.RepeatedColumnException;
import jdbms.sql.exceptions.TableNotFoundException;
import jdbms.sql.exceptions.TypeMismatchException;
import jdbms.sql.exceptions.ValueListTooLargeException;
import jdbms.sql.exceptions.ValueListTooSmallException;
import jdbms.sql.parsing.expressions.ColumnWildcardExpression;
import jdbms.sql.parsing.expressions.SelectColumnListExpression;
import jdbms.sql.parsing.properties.SelectionParameters;
import jdbms.sql.parsing.statements.util.InitialStatementFactory;

/**
 * The Class SelectStatement.
 */
public class SelectStatement extends InitialStatement {

	private static final String STATEMENT_IDENTIFIER
	= "SELECT";
	private static final String CLASS_ID
	= "SELECTSTATEMENTCLASS";
	private final SelectionParameters selectParameters;
	static {
		InitialStatementFactory.getInstance().
		registerStatement(CLASS_ID,
				SelectStatement.class);
	}

	/**
	 * Instantiates a new select statement.
	 */
	public SelectStatement() {
		selectParameters = new SelectionParameters();
	}

	@Override
	public boolean interpret(final String sqlExpression) {
		if (sqlExpression.startsWith(STATEMENT_IDENTIFIER)) {
			final String restOfExpression = sqlExpression.replaceFirst(
					STATEMENT_IDENTIFIER, "").trim();
			if (new ColumnWildcardExpression(
					parameters).interpret(restOfExpression) ||
					new SelectColumnListExpression(
							parameters).interpret(restOfExpression)
					|| new DistinctStatement(parameters).interpret(restOfExpression)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void act(final SQLData data)
			throws ColumnNotFoundException,
			TypeMismatchException,
			TableNotFoundException,
			ColumnAlreadyExistsException,
			RepeatedColumnException,
			ColumnListTooLargeException,
			ValueListTooLargeException,
			ValueListTooSmallException {
		buildParameters();
		final SelectQueryOutput output = data.selectFrom(selectParameters);
		if (output == null) {
			return;
		}
		output.printOutput();
	}

	private void buildParameters() {
		selectParameters.setColumns(parameters.getColumns());
		selectParameters.setTableName(parameters.getTableName());
		selectParameters.setCondition(parameters.getCondition());
	}
}
