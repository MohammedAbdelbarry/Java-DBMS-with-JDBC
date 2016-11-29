package jdbms.sql.parsing.expressions;

import jdbms.sql.parsing.properties.InputParametersContainer;
import jdbms.sql.parsing.statements.Statement;

/**
 * The Class Value List Expression.
 */
public class ValueListExpression implements Expression {

	private Statement nextStatement;
	private Expression nextExpression;
	protected InputParametersContainer parameters;
	
	/**
	 * Instantiates a new value list expression.
	 * @param nextStatement the next statement to be interpreted
	 * @param parameters the input parameters
	 */
	public ValueListExpression(Statement nextStatement,
			InputParametersContainer parameters) {
		this.nextStatement = nextStatement;
		this.parameters = parameters;
	}

	/**
	 * Instantiates a new value list expression.
	 * @param nextExpression the next expression to be interpreted
	 * @param parameters the input parameters
	 */
	public ValueListExpression(Expression nextExpression,
			InputParametersContainer parameters) {
		this.nextExpression = nextExpression;
		this.parameters = parameters;
	}

	@Override
	public boolean interpret(String
			sqlExpression) {
		if (this.nextExpression != null) {
			return this.nextExpression.
					interpret(sqlExpression);
		} else if (this.nextStatement != null) {
			return this.nextStatement.interpret(sqlExpression);
		}
		return false;
	}
}
