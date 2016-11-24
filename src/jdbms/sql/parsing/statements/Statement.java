package jdbms.sql.parsing.statements;

public interface Statement {
	/**
	 * Attempts parsing the sql expression, returns
	 * true if the expression could be parsed successfully,
	 * false otherwise.
	 * @param expression the sql expression to be parsed
	 * @return true if the expression was
	 * parsed successfully, false otherwise
	 */
	public boolean interpret(String sqlExpression);

}
