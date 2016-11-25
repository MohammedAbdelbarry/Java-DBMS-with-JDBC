package jdbms.sql.parsing.expressions;

import jdbms.sql.parsing.expressions.math.AssignmentExpression;
import jdbms.sql.parsing.statements.Statement;

public class AssignmentListExpression implements Expression {

	private Expression nextExpression;
	private Statement nextStatement;

	public AssignmentListExpression(Expression nextExpression) {
		this.nextExpression = nextExpression;
	}

	public AssignmentListExpression(Statement nextStatement) {
		this.nextStatement = nextStatement;
	}

	@Override
	public boolean interpret(String sqlExpression) {
		String[] parts = sqlExpression.split(",");
		for (int i = 0; i < parts.length - 1; i++) {
			if (!new AssignmentExpression().interpret(parts[i].trim())) {
				return false;
			}
		}
		String[] operands = parts[parts.length - 1].trim().split("=");
		String restOfExpression = operands[1].trim().substring(operands[1].indexOf(" ") + 1);
		if (!new AssignmentExpression().
				interpret(operands[0].trim() + "=" + operands[1].trim().
						substring(0, operands[1].trim().indexOf(" "))));
		if (this.nextExpression != null) {
			return this.nextExpression.interpret(restOfExpression.trim());
		} else if (this.nextStatement != null) {
			return this.nextStatement.interpret(restOfExpression.trim());
		}
		return false;
	}

}
