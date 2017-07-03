package jdbms.sql.parsing.expressions.database;

import jdbms.sql.parsing.expressions.terminal.TerminalExpression;
import jdbms.sql.parsing.properties.InputParametersContainer;
import jdbms.sql.parsing.util.Constants;

/**
 * The Class DatabaseTerminatingExpression.
 */
public class DatabaseTerminatingExpression extends DatabaseExpression {

    /**
     * Instantiates a new database terminating expression.
     * @param parameters the input parameters
     */
    public DatabaseTerminatingExpression(
            final InputParametersContainer parameters) {
        super(new TerminalExpression(parameters), parameters);
    }

    @Override
    public boolean interpret(final String sqlExpression) {
        final String databaseName = sqlExpression.
                substring(0, sqlExpression.indexOf(" "));
        final String restOfExpression
                = sqlExpression.substring(sqlExpression.
                indexOf(" ") + 1);
        if (databaseName.matches(Constants.COLUMN_REGEX)) {
            if (Constants.RESERVED_KEYWORDS.
                    contains(databaseName.toUpperCase())) {
                return false;
            }
            parameters.setDatabaseName(databaseName);
            return super.interpret(restOfExpression);
        }
        return false;
    }
}
