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
import jdbms.sql.parsing.expressions.AddColumnTableNameExpression;
import jdbms.sql.parsing.properties.AddColumnParameters;
import jdbms.sql.parsing.statements.util.InitialStatementFactory;

public class AlterTableAddStatement extends AlterTableStatement {

	private final AddColumnParameters addColumnParameters;

	private static final String CLASS_ID
	= "ALTERTABLEADDSTATEMENTCLASS";

	static {
		InitialStatementFactory.getInstance().
		registerStatement(CLASS_ID, AlterTableAddStatement.class);
	}

	public AlterTableAddStatement() {
		super();
		super.setNextExpression(new AddColumnTableNameExpression(parameters));
		addColumnParameters = new AddColumnParameters();
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
			TypeMismatchException,
			InvalidDateFormatException {
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
