package jdbms.sql.parsing.expressions.math.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;

import jdbms.sql.parsing.expressions.math.BooleanExpression;
import jdbms.sql.parsing.properties.InputParametersContainer;

/**
 * A factory for creating BooleanExpression objects.
 */
public class BooleanExpressionFactory {

    /**
     * The boolean expression factory.
     */
    private static BooleanExpressionFactory factory
            = new BooleanExpressionFactory();

    /**
     * The registered boolean expressions.
     */
    private HashMap<String, Class<? extends
            BooleanExpression>> registeredBooleanExpressions = null;

    /**
     * Instantiates a new boolean expression factory.
     */
    private BooleanExpressionFactory() {
        registeredBooleanExpressions = new HashMap<>();
    }

    /**
     * Gets the single instance of BooleanExpressionFactory.
     * @return single instance of BooleanExpressionFactory
     */
    public static BooleanExpressionFactory getInstance() {
        return factory;
    }

    /**
     * Registers a boolean expression.
     * @param symbol       the boolean expression symbol
     * @param booleanClass the boolean expression class
     */
    public void registerBoolExpression(String symbol,
                                       Class<? extends BooleanExpression>
                                               booleanClass) {
        registeredBooleanExpressions.put(symbol, booleanClass);
    }

    /**
     * Creates a new BooleanExpression object.
     * @param symbol     the symbol
     * @param parameters the parameters
     * @return the boolean expression
     */
    public BooleanExpression createBooleanExpression(String symbol,
                                                     InputParametersContainer
															 parameters) {
        Class<? extends BooleanExpression> booleanClass
                = registeredBooleanExpressions.get(symbol);
        try {
            Constructor<? extends BooleanExpression>
                    booleanExpConstructor = booleanClass.
                    getConstructor(InputParametersContainer.class);
            BooleanExpression boolExp
                    = booleanExpConstructor.newInstance(parameters);
            return boolExp;
        } catch (NoSuchMethodException | SecurityException
                | InstantiationException
                | IllegalAccessException
                | IllegalArgumentException
                | InvocationTargetException e) {
            return null;
        }
    }

    /**
     * Gets the registered boolean expressions.
     * @return the registered boolean expressions
     */
    public Collection<String> getRegisteredBooleanExpressions() {
        return registeredBooleanExpressions.keySet();
    }
}
