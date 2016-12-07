package jdbms.sql.parsing.statements;

import jdbms.sql.data.SQLData;
import jdbms.sql.exceptions.ColumnAlreadyExistsException;
import jdbms.sql.exceptions.ColumnListTooLargeException;
import jdbms.sql.exceptions.ColumnNotFoundException;
import jdbms.sql.exceptions.RepeatedColumnException;
import jdbms.sql.exceptions.TableNotFoundException;
import jdbms.sql.exceptions.TypeMismatchException;
import jdbms.sql.exceptions.ValueListTooLargeException;
import jdbms.sql.exceptions.ValueListTooSmallException;
import jdbms.sql.parsing.expressions.AddColumnTableNameExpression;
import jdbms.sql.parsing.expressions.DropColumnTableNameExpression;
import jdbms.sql.parsing.properties.AddColumnParameters;
import jdbms.sql.parsing.statements.util.InitialStatementFactory;

/**
 * The alter table class.
 */
public class AlterTableStatement extends InitialStatement {

	private static final String STATEMENT_IDENTIFIER
	= "ALTER TABLE";

	private static final String CLASS_ID
	= "ALTERTABLESTATEMENTCLASS";

	private final AddColumnParameters addColumnParameters;
	static {
		InitialStatementFactory.getInstance().
		registerStatement(CLASS_ID, AlterTableStatement.class);
	}

	/**
	 * Instantiates a new alter table statement.
	 */
	public AlterTableStatement() {
		addColumnParameters = new AddColumnParameters();
	}

	@Override
	public boolean interpret(final String sqlExpression) {
		if (sqlExpression.startsWith(STATEMENT_IDENTIFIER)) {
			final String restOfExpression = sqlExpression.replaceFirst(
					STATEMENT_IDENTIFIER, "").trim();
			return new AddColumnTableNameExpression(parameters).
					interpret(restOfExpression)
					|| new DropColumnTableNameExpression(parameters).
					interpret(restOfExpression);
		}
		return false;
	}

	@Override
	public void act(final SQLData data)
			throws ColumnAlreadyExistsException,
			TableNotFoundException,
			RepeatedColumnException,
			ColumnListTooLargeException,
			ColumnNotFoundException,
			ValueListTooLargeException,
			ValueListTooSmallException,
			TypeMismatchException {
		buildParameters();
		data.addTableColumn(addColumnParameters);
	}

	/**
	 * Builds the parameters.
	 */
	private void buildParameters() {
		addColumnParameters.
		setTableName(parameters.getTableName());
		addColumnParameters.setColumnIdentifier(
				parameters.getColumnDefinitions().get(0));
	}
}
