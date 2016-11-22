package jdbms.sql.parsing.statements;

import jdbms.sql.parsing.expressions.Expression;

public abstract class Statement {
	protected Expression nextExpression;
	protected String statementIdentifier;

	protected Statement(Expression nextExpression, String statementIndentifier) {
		this.nextExpression = nextExpression;
		this.statementIdentifier = statementIndentifier;
	}

	public boolean interpret(String sqlExpression) {
		if (sqlExpression.startsWith(statementIdentifier)) {
			if (nextExpression == null) {
				return true;
			} else {
				return nextExpression.interpret(sqlExpression);
			}
		} else {
			return false;
		}
	}
}
