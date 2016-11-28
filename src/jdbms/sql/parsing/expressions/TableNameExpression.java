package jdbms.sql.parsing.expressions;

import jdbms.sql.errors.ErrorHandler;
import jdbms.sql.parsing.properties.InputParametersContainer;
import jdbms.sql.parsing.statements.Statement;
import jdbms.sql.parsing.util.Constants;

public abstract class TableNameExpression implements Expression {

	private Statement nextStatement;
	private Expression nextExpression;
	protected InputParametersContainer parameters;
	public TableNameExpression(Expression nextExpression,
			InputParametersContainer parameters) {
		this.nextExpression = nextExpression;
		this.parameters = parameters;
	}

	public TableNameExpression(Statement nextStatement,
			InputParametersContainer parameters) {
		this.nextStatement = nextStatement;
		this.parameters = parameters;
	}

	@Override
	public boolean interpret(String sqlExpression) {
		String tableName = sqlExpression.substring(0, sqlExpression.indexOf(" ")).trim();
		String restOfExpression = sqlExpression.substring(sqlExpression.indexOf(" ") + 1).trim();
		if (tableName.matches("^[a-zA-Z_][a-zA-Z0-9_\\$]*$")) {
			if (Constants.RESERVED_KEYWORDS.contains(tableName.toUpperCase())) {
				ErrorHandler.printReservedKeywordError(tableName);
				return false;
			}
			parameters.setTableName(tableName);
			if (this.nextStatement != null) {
				return this.nextStatement.interpret(restOfExpression);
			} else if (this.nextExpression != null) {
				return this.nextExpression.interpret(restOfExpression);
			}
		}
		ErrorHandler.printSyntaxErrorNear("Table Name");
		return false;
	}
}
