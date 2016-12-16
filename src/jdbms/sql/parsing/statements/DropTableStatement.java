package jdbms.sql.parsing.statements;

import jdbms.sql.data.SQLData;
import jdbms.sql.exceptions.FailedToDeleteTableException;
import jdbms.sql.exceptions.TableNotFoundException;
import jdbms.sql.parsing.expressions.table.name.TerminatingTableExpression;
import jdbms.sql.parsing.properties.TableDroppingParameters;
import jdbms.sql.parsing.statements.util.InitialStatementFactory;

/**
 * The Class DropTableStatement.
 */
public class DropTableStatement extends InitialStatement {

    private static final String STATEMENT_IDENTIFIER
            = "DROP TABLE";
    private static final String CLASS_ID
            = "DROPTABLESTATEMENTCLASS";
    private final TableDroppingParameters dropTableParameters;

    static {
        InitialStatementFactory.
                getInstance().
                registerStatement(CLASS_ID,
                        DropTableStatement.class);
    }

    /**
     * Instantiates a new drop table statement.
     */
    public DropTableStatement() {
        super();
        dropTableParameters = new TableDroppingParameters();
    }

    @Override
    public boolean interpret(final String sqlExpression) {
        if (sqlExpression.startsWith(STATEMENT_IDENTIFIER)) {
            final String restOfExpression = sqlExpression.replaceFirst
                    (STATEMENT_IDENTIFIER, "").trim();
            return new TerminatingTableExpression(parameters).interpret
					(restOfExpression);
        }
        return false;
    }

    @Override
    public void act(final SQLData data)
            throws TableNotFoundException,
            FailedToDeleteTableException {
        buildParameters();
        numberOfUpdates = data.dropTable(dropTableParameters);
    }

    /**
     * Builds the parameters.
     */
    private void buildParameters() {
        dropTableParameters.setTableName(parameters.getTableName());
    }
}
