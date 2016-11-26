package jdbms.sql.parsing.expressions.math;

import jdbms.sql.parsing.expressions.util.ColumnExpression;
import jdbms.sql.parsing.expressions.util.ValueExpression;
import jdbms.sql.parsing.properties.InputParametersContainer;
import jdbms.sql.parsing.statements.WhereStatement;

public class AssignmentExpression extends BinaryExpression {
	private static final String SYMBOL = "=";
	private static final int NUMBER_OF_OPERANDS = 2;
	public AssignmentExpression(
			InputParametersContainer parameters) {
		super(SYMBOL, new WhereStatement(parameters), parameters);
	}

	@Override
	public boolean interpret(String assignmentExpression) {
		String[] parts = assignmentExpression.split("=");
		if (parts.length == NUMBER_OF_OPERANDS) {
			if ( new ColumnExpression(parts[0].trim()).isValidColumnName() &&
					new ValueExpression(parts[1].trim()).isValidExpressionName()) {
				setLeftOperand(parts[0].trim());
				setRightOperand(parts[1].trim());
				return true;
			}
		}
		return false;
	}
}
