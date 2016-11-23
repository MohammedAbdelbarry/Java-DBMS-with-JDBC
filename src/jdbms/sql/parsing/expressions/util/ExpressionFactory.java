package jdbms.sql.parsing.expressions.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import jdbms.sql.parsing.expressions.Expression;

public class ExpressionFactory {
	private static ExpressionFactory factory = new ExpressionFactory();

	private HashMap<String, Class<? extends Expression>> registeredExpressions = null;

	private ExpressionFactory() {
		registeredExpressions = new HashMap<>();
	}

	public static ExpressionFactory getInstance() {
		return factory;
	}
	public void registerExpression(String expressionID,
			Class<? extends Expression> expressionClass) {
		registeredExpressions.put(expressionID, expressionClass);
	}
	public Expression createExpression(String expressionID) {
		Class<? extends Expression> expressionClass =
				registeredExpressions.get(expressionID);
		try {
			Constructor<? extends Expression> expressionConstructor =
					expressionClass.getConstructor();
			Expression expression = expressionConstructor.newInstance();
			return expression;
		} catch (NoSuchMethodException | SecurityException
				| InstantiationException
				| IllegalAccessException
				| IllegalArgumentException
				| InvocationTargetException e) {
			return null;
		}
	}
	public Collection<String> getRegisteredExpressions() {
		return registeredExpressions.keySet();
	}
}
