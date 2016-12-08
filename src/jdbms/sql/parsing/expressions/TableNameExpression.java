package jdbms.sql.parsing.expressions;

import jdbms.sql.parsing.properties.InputParametersContainer;
import jdbms.sql.parsing.statements.Statement;
import jdbms.sql.parsing.util.Constants;

/**
 * The Class Table Name Expression.
 */
public abstract class TableNameExpression implements Expression {

	private Statement nextStatement;
	private Expression nextExpression;
	protected InputParametersContainer parameters;

	/**
	 * Instantiates a new table name expression.
	 * @param nextExpression the next expression to be interpreted
	 * @param parameters the input parameters
	 */
	public TableNameExpression(final Expression nextExpression,
			final InputParametersContainer parameters) {
		this.nextExpression = nextExpression;
		this.parameters = parameters;
	}

	/**
	 * Instantiates a new table name expression.
	 *
	 * @param nextStatement the next statement to be interpreted
	 * @param parameters the input parameters
	 */
	public TableNameExpression(final Statement nextStatement,
			final InputParametersContainer parameters) {
		this.nextStatement = nextStatement;
		this.parameters = parameters;
	}

	@Override
	public boolean interpret(final String sqlExpression) {
		String tableName, restOfExpression;
		try {
			tableName = sqlExpression.substring(0, sqlExpression.indexOf(" ")).trim();
			restOfExpression = sqlExpression.substring(sqlExpression.indexOf(" ") + 1).trim();
		} catch (final Exception e) {
			return false;
		}

		if (tableName.matches(Constants.COLUMN_REGEX)) {
			if (Constants.RESERVED_KEYWORDS.contains(tableName.toUpperCase())) {
				return false;
			}
			parameters.setTableName(tableName);
			if (this.nextStatement != null) {
				return this.nextStatement.interpret(restOfExpression);
			} else if (this.nextExpression != null) {
				return this.nextExpression.interpret(restOfExpression);
			}
		}
		return false;
	}
}
