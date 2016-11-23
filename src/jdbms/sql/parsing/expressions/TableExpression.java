package jdbms.sql.parsing.expressions;

public class TableExpression implements Expression {

	public TableExpression() {
	}
	@Override
	public boolean interpret(String sqlExpression) {
		if (sqlExpression.matches("^[a-zA-Z_][a-zA-Z0-9_\\$]*$")) {
			return true;
		}
		return false;
	}
}
