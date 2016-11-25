package jdbms.sql.parsing.expressions;

import jdbms.sql.parsing.properties.InputParametersContainer;
import jdbms.sql.parsing.statements.Statement;

public abstract class ColumnListExpression implements Expression {

	private Expression nextExpression = null;
	private Statement nextStatement = null;
	protected InputParametersContainer parameters;
	public ColumnListExpression(Expression nextExpression,
			InputParametersContainer parameters) {
		this.nextExpression = nextExpression;
		this.parameters = parameters;
	}

	public ColumnListExpression(Statement nextStatement,
			InputParametersContainer parameters) {
		this.nextStatement = nextStatement;
		this.parameters = parameters;
	}

	@Override
	public boolean interpret(String sqlExpression) {
		if (nextExpression != null) {
			return nextExpression.interpret(sqlExpression);
		} else if (nextStatement != null) {
			return nextStatement.interpret(sqlExpression);
		}
		return false;
	}
}
