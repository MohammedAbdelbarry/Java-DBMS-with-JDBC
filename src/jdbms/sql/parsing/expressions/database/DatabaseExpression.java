package jdbms.sql.parsing.expressions.database;

import jdbms.sql.parsing.expressions.Expression;
import jdbms.sql.parsing.properties.InputParametersContainer;
import jdbms.sql.parsing.statements.Statement;

/**
 * The database name expression class.
 */
public abstract class DatabaseExpression implements Expression {

	private Statement nextStatement;
	private Expression nextExpression;
	protected InputParametersContainer parameters;
	
	/**
	 * Instantiates a new database expression.
	 * @param nextStatement the next statement to be interpreted
	 * @param parameters the input parameters
	 */
	public DatabaseExpression(Statement nextStatement,
			InputParametersContainer parameters) {
		this.nextStatement = nextStatement;
		this.parameters = parameters;
	}

	/**
	 * Instantiates a new database expression.
	 * @param nextExpression the next expression to be interpreted
	 * @param parameters the parameters
	 */
	public DatabaseExpression(Expression nextExpression,
			InputParametersContainer parameters) {
		this.nextExpression = nextExpression;
		this.parameters = parameters;
	}

	@Override
	public boolean interpret(String sqlExpression) {
		if (this.nextStatement != null) {
			return this.nextStatement.
					interpret(sqlExpression.trim());
		} else if (this.nextExpression != null) {
			return this.nextExpression.
					interpret(sqlExpression.trim());
		}
		return false;
	}
}
