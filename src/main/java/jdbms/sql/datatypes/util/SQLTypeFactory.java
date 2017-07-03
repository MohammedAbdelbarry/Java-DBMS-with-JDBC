package jdbms.sql.datatypes.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;

import jdbms.sql.datatypes.SQLType;
import jdbms.sql.exceptions.InvalidDateFormatException;

/**
 * A Singleton factory that is
 * responsible for creating objects
 * of SQLType.
 * @author Mohammed Abdelbarry
 */
public class SQLTypeFactory {
    /**
     * The Singleton Factory
     * Instance.
     */
    private static SQLTypeFactory factory
            = new SQLTypeFactory();
    /**
     * A Map between the SQL Classes and their
     * respective keys.
     */
    private HashMap<String, Class<? extends SQLType<?>>>
            registeredTypes = null;

    /**
     * Constructs a new SQL Type Factory.
     */
    private SQLTypeFactory() {
        registeredTypes = new HashMap<>();
    }

    /**
     * Gets the singleton factory instance.
     * @return the singleton factory instance
     */
    public static SQLTypeFactory getInstance() {
        return factory;
    }

    /**
     * Registers a SQL Type to the factory.
     * @param key       The key of the SQL type.
     * @param typeClass The class of the sql type.
     */
    public void registerType(final String key,
                             final Class<? extends SQLType<?>> typeClass) {
        registeredTypes.put(key, typeClass);
    }

    /**
     * Creates and Returns a new SQL Type
     * Object.
     * @param key   The key of the SQL Type.
     * @param value The Value of the SQL Object
     * @return The Created SQL Object.
     * @throws InvalidDateFormatException If the SQL Object is a Date Object and
     *                                    the given value is invalid.
     */
    public SQLType<?> getTypeObject(final String key,
                                    final String value) throws
            InvalidDateFormatException {
        final Class<? extends SQLType<?>> typeClass
                = registeredTypes.get(key);
        try {
            final Constructor<?> constructor
                    = typeClass.getConstructor(String.class);
            final SQLType<?> instance
                    = (SQLType<?>) constructor.newInstance(value);
            return instance;
        } catch (NoSuchMethodException
                | SecurityException
                | InstantiationException
                | IllegalAccessException
                | IllegalArgumentException e) {
            return null;
        } catch (final InvocationTargetException e) {
            throw new InvalidDateFormatException(e.getCause().getMessage());
        }
    }

    /**
     * Gets a Collection of All the Registered
     * Types in the Factory.
     * @return A Collection of All the Registered Types in the Factory.
     */
    public Collection<String> getRegisteredTypes() {
        return registeredTypes.keySet();
    }
}
