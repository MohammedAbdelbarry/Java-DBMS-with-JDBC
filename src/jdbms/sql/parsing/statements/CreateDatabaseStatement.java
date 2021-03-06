package jdbms.sql.parsing.statements;

import jdbms.sql.data.SQLData;
import jdbms.sql.exceptions.DatabaseAlreadyExistsException;
import jdbms.sql.parsing.expressions.database.DatabaseTerminatingExpression;
import jdbms.sql.parsing.properties.DatabaseCreationParameters;
import jdbms.sql.parsing.statements.util.InitialStatementFactory;

/**
 * The Class CreateDatabaseStatement.
 */
public class CreateDatabaseStatement extends InitialStatement {

    private static final String STATEMENT_IDENTIFIER
            = "CREATE DATABASE";
    private static final String CLASS_ID
            = "CREATEDATABASESTATEMENTCLASS";
    private final DatabaseCreationParameters createDBParameters;

    static {
        InitialStatementFactory.
                getInstance().
                registerStatement(CLASS_ID, CreateDatabaseStatement.class);
    }

    /**
     * Instantiates a new create database statement.
     */
    public CreateDatabaseStatement() {
        super();
        createDBParameters = new DatabaseCreationParameters();
    }

    @Override
    public boolean interpret(final String sqlExpression) {
        if (sqlExpression.startsWith(STATEMENT_IDENTIFIER)) {
            final String restOfExpression = sqlExpression.
                    replaceFirst(STATEMENT_IDENTIFIER, "").trim();
            return new DatabaseTerminatingExpression(parameters).
                    interpret(restOfExpression);
        }
        return false;
    }

    @Override
    public void act(final SQLData data)
            throws DatabaseAlreadyExistsException {
        buildParameters();
        numberOfUpdates = data.createDatabase(createDBParameters);
    }

    /**
     * Builds the parameters.
     */
    private void buildParameters() {
        createDBParameters.setDatabaseName(parameters.
                getDatabaseName());
    }
}
