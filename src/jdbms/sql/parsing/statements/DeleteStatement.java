package jdbms.sql.parsing.statements;

import java.io.IOException;

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
import jdbms.sql.parsing.properties.DeletionParameters;
import jdbms.sql.parsing.statements.util.InitialStatementFactory;

/**
 * The Class Delete Statement.
 */
public class DeleteStatement extends InitialStatement {

	private static final String STATEMENT_IDENTIFIER = "DELETE";
	private static final String CLASS_ID = "DELETESTATEMENTCLASS";
	private final DeletionParameters deleteParameters;
	static {
		InitialStatementFactory.getInstance().
		registerStatement(CLASS_ID, DeleteStatement.class);
	}

	/**
	 * Instantiates a new delete statement.
	 */
	public DeleteStatement() {
		super();
		deleteParameters = new DeletionParameters();
	}

	@Override
	public boolean interpret(final String sqlExpression) {
		if (sqlExpression.startsWith(STATEMENT_IDENTIFIER)) {
			final String restOfExpression = sqlExpression.
					replaceFirst(STATEMENT_IDENTIFIER, "").
					trim();
			return new FromStatement(parameters).
					interpret(restOfExpression);
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
			InvalidDateFormatException,
			IOException {
		buildParameters();
		numberOfUpdates = data.deleteFrom(deleteParameters);
	}

	/**
	 * Builds the parameters.
	 */
	private void buildParameters() {
		deleteParameters.
		setTableName(parameters.getTableName());
		deleteParameters.
		setCondition(parameters.getCondition());
	}
}
