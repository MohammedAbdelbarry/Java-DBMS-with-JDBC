package jdbms.sql.parsing.expressions;

public class TerminalExpression implements Expression {

	public TerminalExpression() {}

	@Override
	public boolean interpret(String sqlExpression) {
		if (sqlExpression.equals(";")) {
			return true;
		}
		return false;
	}
}
