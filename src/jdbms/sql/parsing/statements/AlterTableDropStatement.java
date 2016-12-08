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
import jdbms.sql.parsing.expressions.DropColumnTableNameExpression;
import jdbms.sql.parsing.properties.DropColumnParameters;
import jdbms.sql.parsing.statements.util.InitialStatementFactory;

public class AlterTableDropStatement extends AlterTableStatement {

	private final DropColumnParameters dropColumnParameters;

	private static final String CLASS_ID
	= "ALTERTABLEDROPSTATEMENTCLASS";

	static {
		InitialStatementFactory.getInstance().
		registerStatement(CLASS_ID, AlterTableDropStatement.class);
	}

	public AlterTableDropStatement() {
		super();
		super.setNextExpression(new DropColumnTableNameExpression(parameters));
		dropColumnParameters = new DropColumnParameters();
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
		numberOfUpdates = data.dropTableColumn(dropColumnParameters);
	}

	/**
	 * Builds the parameters.
	 */
	private void buildParameters() {
		dropColumnParameters.
		setTableName(parameters.getTableName());
		dropColumnParameters.setColumnList(parameters.getColumns());
	}
}
