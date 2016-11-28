package jdbms.sql.parsing.expressions;

import jdbms.sql.errors.ErrorHandler;
import jdbms.sql.parsing.properties.InputParametersContainer;
import jdbms.sql.parsing.statements.Statement;

public abstract class ColumnsDatatypesExpression implements Expression {

	private Expression nextExpression;
	private Statement nextStatement;
	protected InputParametersContainer parameters;
	public ColumnsDatatypesExpression(Expression nextExpression,
			InputParametersContainer parameters) {
		this.nextExpression = nextExpression;
		this.parameters = parameters;
	}

	public ColumnsDatatypesExpression(Statement nextStatement,
			InputParametersContainer parameters) {
		this.nextStatement = nextStatement;
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
