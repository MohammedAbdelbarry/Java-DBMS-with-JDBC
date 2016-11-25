package jdbms.sql.parsing.expressions.math.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;

import jdbms.sql.parsing.expressions.math.BooleanExpression;
import jdbms.sql.parsing.properties.InputParametersContainer;

public class BooleanExpressionFactory {
	private static BooleanExpressionFactory factory = new BooleanExpressionFactory();

	private HashMap<String, Class<? extends
			BooleanExpression>> registeredBooleanExpressions = null;

	private BooleanExpressionFactory() {
		registeredBooleanExpressions = new HashMap<>();
	}

	public static BooleanExpressionFactory getInstance() {
		return factory;
	}
	public void registerBoolExpression(String symbol,
			Class<? extends BooleanExpression> booleanClass) {
		registeredBooleanExpressions.put(symbol, booleanClass);
	}
	public BooleanExpression createBooleanExpression(String symbol,
			InputParametersContainer parameters) {
		Class<? extends BooleanExpression> booleanClass =
				registeredBooleanExpressions.get(symbol);
		try {
			Constructor<? extends BooleanExpression> booleanExpConstructor =
					booleanClass.getConstructor(InputParametersContainer.class);
			BooleanExpression boolExp = booleanExpConstructor.newInstance(parameters);
			return boolExp;
		} catch (NoSuchMethodException | SecurityException
				| InstantiationException
				| IllegalAccessException
				| IllegalArgumentException
				| InvocationTargetException e) {
			return null;
		}
	}
	public Collection<String> getRegisteredBooleanExpressions() {
		return registeredBooleanExpressions.keySet();
	}
}
