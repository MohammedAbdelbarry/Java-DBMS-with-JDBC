package jdbms.sql.parsing.expressions;

public class ColumnListExpression implements Expression {

	@Override
	public boolean interpret(String expression) {
		String[] columns = expression.split(",");
		return false;
	}
}
