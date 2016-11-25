package jdbms.sql.parsing.expressions;

import jdbms.sql.parsing.properties.InputParametersContainer;
import jdbms.sql.parsing.statements.Statement;

public abstract class DatabaseExpression implements Expression {

	private Statement nextStatement;
	private Expression nextExpression;
	protected InputParametersContainer parameters;
	public DatabaseExpression(Statement nextStatement,
			InputParametersContainer parameters) {
		this.nextStatement = nextStatement;
		this.parameters = parameters;
	}

	public DatabaseExpression(Expression nextExpression,
			InputParametersContainer parameters) {
		this.nextExpression = nextExpression;
		this.parameters = parameters;
	}

	@Override
	public boolean interpret(String sqlExpression) {
		if (this.nextStatement != null) {
			return this.nextStatement.interpret(sqlExpression.trim());
		} else if (this.nextExpression != null) {
			return this.nextExpression.interpret(sqlExpression.trim());
		}
		return false;
	}
}
