package jdbms.sql.datatypes.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;

import jdbms.sql.datatypes.SQLType;
import jdbms.sql.exceptions.InvalidDateFormatException;

public class SQLTypeFactory {
	private static SQLTypeFactory factory
	= new SQLTypeFactory();
	private HashMap<String, Class<? extends SQLType<?>>>
	registeredTypes = null;
	private SQLTypeFactory() {
		registeredTypes = new HashMap<>();
	}
	public static SQLTypeFactory getInstance() {
		return factory;
	}
	public void registerType(final String key,
			final Class<? extends SQLType<?>> typeClass) {
		registeredTypes.put(key, typeClass);
	}
	public SQLType<?> getTypeObject(final String key,
			final String value) throws InvalidDateFormatException {
		final Class<? extends SQLType<?>> typeClass
				= registeredTypes.get(key);
		try {
			final Constructor<?> constructor
			= typeClass.getConstructor(String.class);
			final SQLType<?> instance
			= (SQLType<?>)constructor.newInstance(value);
			return instance;
		} catch(NoSuchMethodException
				| SecurityException
				| InstantiationException
				| IllegalAccessException
				| IllegalArgumentException e) {
			return null;
		} catch (final InvocationTargetException e) {
			throw new InvalidDateFormatException(e.getCause().getMessage());
		}
	}

	public Collection<String> getRegisteredTypes() {
		return registeredTypes.keySet();
	}
}
