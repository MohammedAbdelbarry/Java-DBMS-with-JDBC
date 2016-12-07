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
import jdbms.sql.parsing.expressions.TableUpdateTableNameExpression;
import jdbms.sql.parsing.properties.UpdatingParameters;
import jdbms.sql.parsing.statements.util.InitialStatementFactory;

/**
 * The Class Update Statement.
 */
public class UpdateStatement extends InitialStatement {

	private static final String STATEMENT_IDENTIFIER
	= "UPDATE";
	private static final String CLASS_ID
	= "UPDATESTATEMENTCLASS";
	private final UpdatingParameters updateParameters;
	static {
		InitialStatementFactory.getInstance().registerStatement(
				CLASS_ID,
				UpdateStatement.class);
	}

	/**
	 * Instantiates a new update statement.
	 */
	public UpdateStatement() {
		updateParameters = new UpdatingParameters();
	}

	@Override
	public boolean interpret(final String sqlExpression) {
		if (sqlExpression.startsWith(STATEMENT_IDENTIFIER)) {
			final String restOfExpression = sqlExpression.replaceFirst(
					STATEMENT_IDENTIFIER, "").trim();
			return new TableUpdateTableNameExpression(
					parameters).interpret(restOfExpression);
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
			ValueListTooSmallException,
			InvalidDateFormatException {
		buildParameters();
		data.updateTable(updateParameters);
	}

	/**
	 * Builds the parameters.
	 */
	private void buildParameters() {
		updateParameters.setAssignmentList(parameters.getAssignmentList());
		updateParameters.setCondition(parameters.getCondition());
		updateParameters.setTableName(parameters.getTableName());
	}
}
