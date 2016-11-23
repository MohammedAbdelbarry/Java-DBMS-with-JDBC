package jdbms.sql.datatypes.util;

import java.lang.reflect.ParameterizedType;
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
	public void registerType(String key, Class<? extends SQLType<?>> typeClass) {
		registeredTypes.put(key, typeClass);
	}
	public SQLType<?> getTypeObject(String key, String value) {
		Class<? extends SQLType<?>> typeClass
				= registeredTypes.get(key);
		try {
			Class<?> genericClass = ((ParameterizedType) typeClass.getGenericSuperclass()).getActualTypeArguments()[0].getClass();
		} catch(Exception ex) {
			
		}
		return null;
	}
}
