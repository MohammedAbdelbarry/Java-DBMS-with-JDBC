package jdbms.sql.parsing.expressions;

import jdbms.sql.parsing.properties.InputParametersContainer;
import jdbms.sql.parsing.statements.Statement;

public class ValueListExpression implements Expression {

	private Statement nextStatement;
	private Expression nextExpression;
	protected InputParametersContainer parameters;
	public ValueListExpression(Statement nextStatement,
			InputParametersContainer parameters) {
		this.nextStatement = nextStatement;
		this.parameters = parameters;
	}

	public ValueListExpression(Expression nextExpression,
			InputParametersContainer parameters) {
		this.nextExpression = nextExpression;
		this.parameters = parameters;
	}

	@Override
	public boolean interpret(String sqlExpression) {
		if (this.nextExpression != null) {
			return this.nextExpression.interpret(sqlExpression);
		} else if (this.nextStatement != null) {
			return this.nextStatement.interpret(sqlExpression);
		}
		return false;
	}
}
