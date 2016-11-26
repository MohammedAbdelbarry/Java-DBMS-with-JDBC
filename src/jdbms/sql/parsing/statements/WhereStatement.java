package jdbms.sql.parsing.statements;

import java.util.ArrayList;
import java.util.Collection;

import jdbms.sql.parsing.expressions.math.BooleanExpression;
import jdbms.sql.parsing.expressions.math.util.BooleanExpressionFactory;
import jdbms.sql.parsing.properties.InputParametersContainer;

public class WhereStatement implements Statement {
	private static final String STATEMENT_IDENTIFIER = "WHERE";
	private Collection<BooleanExpression> boolExpressions;
	private InputParametersContainer parameters;
	public WhereStatement(InputParametersContainer parameters) {
		this.parameters = parameters;
		boolExpressions = new ArrayList<>();
		for (String key : BooleanExpressionFactory.getInstance().
				getRegisteredBooleanExpressions()) {
			boolExpressions.add(BooleanExpressionFactory.
					getInstance().createBooleanExpression(key, parameters));
		}
	}

	@Override
	public boolean interpret(String sqlExpression) {
		if (sqlExpression.startsWith(STATEMENT_IDENTIFIER)) {
			String restOfExpression = sqlExpression.replace(STATEMENT_IDENTIFIER, "").trim();
			for (BooleanExpression exp : boolExpressions) {
				if (exp.interpret(restOfExpression)) {
					return true;
				}
			}
		}
		return false;
	}
}
