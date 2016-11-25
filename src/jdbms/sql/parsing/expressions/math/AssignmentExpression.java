package jdbms.sql.parsing.expressions.math;

import jdbms.sql.parsing.expressions.util.ColumnExpression;
import jdbms.sql.parsing.expressions.util.ValueExpression;
import jdbms.sql.parsing.statements.WhereStatement;

public class AssignmentExpression extends BinaryExpression {
	private static final String SYMBOL = "=";
	private static final int NUMBER_OF_OPERANDS = 2;
	public AssignmentExpression() {
		super(SYMBOL, new WhereStatement());
	}

	@Override
	public boolean interpret(String assignmentExpression) {
		String[] parts = assignmentExpression.split("=");
		if (parts.length == NUMBER_OF_OPERANDS) {
			return new ColumnExpression(parts[0].trim()).isValidColumnName() &&
					new ValueExpression(parts[1].trim()).isValidExpressionName();
		}
		return false;
	}
}
