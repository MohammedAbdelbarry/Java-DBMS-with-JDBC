package jdbms.sql.datatypes.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;

import jbdms.sql.datatypes.SQLType;

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
	public void registerType(String key,
			Class<? extends SQLType<?>> typeClass) {
		registeredTypes.put(key, typeClass);
	}
	public SQLType<?> getTypeObject(String key, String value) {
		Class<? extends SQLType<?>> typeClass
				= registeredTypes.get(key);
		try {
			Constructor<?> constructor
			= typeClass.getConstructor(String.class);
			SQLType<?> instance
			= (SQLType<?>)constructor.newInstance(value);
			return instance;
		} catch(NoSuchMethodException
				| SecurityException
				| InstantiationException
				| IllegalAccessException
				| IllegalArgumentException
				| InvocationTargetException ex) {
			return null;
		}
	}
	public Collection<String> getRegisteredTypes() {
		return registeredTypes.keySet();
	}
}
