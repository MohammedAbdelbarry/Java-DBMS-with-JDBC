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
import jdbms.sql.parsing.expressions.table.name.AddColumnTableNameExpression;
import jdbms.sql.parsing.properties.AddColumnParameters;
import jdbms.sql.parsing.statements.util.InitialStatementFactory;

/**
 * The alter table add statement class.
 */
public class AlterTableAddStatement extends AlterTableStatement {

	/** The parameters for adding a column. */
	private final AddColumnParameters addColumnParameters;

	/** The ID of the class. */
	private static final String CLASS_ID
	= "ALTERTABLEADDSTATEMENTCLASS";

	static {
		InitialStatementFactory.getInstance().
		registerStatement(CLASS_ID, AlterTableAddStatement.class);
	}

	/**
	 * Instantiates a new alter table add statement.
	 */
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
			InvalidDateFormatException,
			IOException {
		buildParameters();
		numberOfUpdates = data.
				addTableColumn(addColumnParameters);
	}

	/**
	 * Builds the parameters of adding a column.
	 */
	private void buildParameters() {
		addColumnParameters.
		setTableName(parameters.getTableName());
		addColumnParameters.setColumnIdentifier(
				parameters.
				getColumnDefinitions().get(0));
	}
}
