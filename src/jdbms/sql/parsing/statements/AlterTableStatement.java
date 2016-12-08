package jdbms.sql.parsing.statements;

import jdbms.sql.parsing.expressions.Expression;

/**
 * The alter table class.
 */
public abstract class AlterTableStatement extends InitialStatement {

	private static final String STATEMENT_IDENTIFIER
	= "ALTER TABLE";

	private Expression nextExpression;
	private Statement nextStatement;

	/**
	 * Instantiates a new alter table statement.
	 */
	public AlterTableStatement() {
		super();
	}
	/**
	 * Sets the next expression to interpret the sql command.
	 */
	public void setNextExpression(final Expression nextExpression) {
		this.nextExpression = nextExpression;
	}
	/**
	 * Sets the next statement to interpret the sql command.
	 */
	public void setNextStatement(final Statement nextStatement) {
		this.nextStatement = nextStatement;
	}

	@Override
	public boolean interpret(final String sqlExpression) {
		if (sqlExpression.startsWith(STATEMENT_IDENTIFIER)) {
			final String restOfExpression = sqlExpression.replaceFirst(
					STATEMENT_IDENTIFIER, "").trim();
			if (this.nextExpression != null) {
				return this.nextExpression.interpret(restOfExpression);
			} else if (this.nextStatement != null) {
				return this.nextStatement.interpret(restOfExpression);
			}
		}
		return false;
	}
}
