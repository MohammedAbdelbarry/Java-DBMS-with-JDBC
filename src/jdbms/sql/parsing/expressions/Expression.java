package jdbms.sql.parsing.expressions;

public interface Expression {

	/**
	 * Attempts parsing the sql expression, returns
	 * true if the expression could be parsed successfully
	 * , false otherwise.
	 * @param expression the sql expression to be
	 * parsed
	 * @return true if the expression was
	 * parsed successfully, false otherwise
	 */
	public boolean interpret(String expression);

	//public SqlStatementParameters getParameters();

}
