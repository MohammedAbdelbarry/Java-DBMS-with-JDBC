package jdbms.sql.parsing.statements;

import java.io.IOException;

import jdbms.sql.data.SQLData;
import jdbms.sql.exceptions.AllColumnsDroppingException;
import jdbms.sql.exceptions.ColumnAlreadyExistsException;
import jdbms.sql.exceptions.ColumnListTooLargeException;
import jdbms.sql.exceptions.ColumnNotFoundException;
import jdbms.sql.exceptions.InvalidDateFormatException;
import jdbms.sql.exceptions.RepeatedColumnException;
import jdbms.sql.exceptions.TableNotFoundException;
import jdbms.sql.exceptions.TypeMismatchException;
import jdbms.sql.exceptions.ValueListTooLargeException;
import jdbms.sql.exceptions.ValueListTooSmallException;
import jdbms.sql.parsing.expressions.table.name.DropColumnTableNameExpression;
import jdbms.sql.parsing.properties.DropColumnParameters;
import jdbms.sql.parsing.statements.util.InitialStatementFactory;

/**
 * The alter drop column statement.
 */
public class AlterTableDropStatement extends AlterTableStatement {

    /**
     * The drop column parameters.
     */
    private final DropColumnParameters dropColumnParameters;

    /**
     * The ID class.
     */
    private static final String CLASS_ID
            = "ALTERTABLEDROPSTATEMENTCLASS";

    static {
        InitialStatementFactory.getInstance().
                registerStatement(CLASS_ID, AlterTableDropStatement.class);
    }

    /**
     * Instantiates a new alter table drop statement.
     */
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
            IOException,
            AllColumnsDroppingException {
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
