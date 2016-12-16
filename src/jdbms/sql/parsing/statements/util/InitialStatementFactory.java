package jdbms.sql.parsing.statements.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;

import jdbms.sql.parsing.statements.InitialStatement;

/**
 * A factory for creating InitialStatement objects.
 */
public class InitialStatementFactory {

    private static InitialStatementFactory factory = new
            InitialStatementFactory();
    private HashMap<String, Class<? extends InitialStatement>>
			registeredStatements = null;

    /**
     * Instantiates a new initial statement factory.
     */
    private InitialStatementFactory() {
        registeredStatements = new HashMap<>();
    }

    /**
     * Gets the single instance of InitialStatementFactory.
     * @return single instance of InitialStatementFactory
     */
    public static InitialStatementFactory getInstance() {
        return factory;
    }

    /**
     * Register statement.
     * @param statementID    the statement id
     * @param statementClass the statement class
     */
    public void registerStatement(String statementID,
                                  Class<? extends InitialStatement>
										  statementClass) {
        registeredStatements.put(statementID, statementClass);
    }

    /**
     * Creates a new InitialStatement object.
     * @param statementID the statement id
     * @return the initial statement
     */
    public InitialStatement createStatement(String statementID) {
        Class<? extends InitialStatement> statementClass =
                registeredStatements.get(statementID);
        try {
            Constructor<? extends InitialStatement> statementConstructor =
                    statementClass.getConstructor();
            InitialStatement statement = statementConstructor.newInstance();
            return statement;
        } catch (NoSuchMethodException | SecurityException
                | InstantiationException
                | IllegalAccessException
                | IllegalArgumentException
                | InvocationTargetException e) {
            return null;
        }
    }

    /**
     * Gets the registered statements.
     * @return the registered statements
     */
    public Collection<String> getRegisteredStatements() {
        return registeredStatements.keySet();
    }
}
