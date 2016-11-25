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
		int whereIndex = parts[parts.length - 1].trim().indexOf(" WHERE "),
				semiColon = parts[parts.length - 1].indexOf(" ;");
		if (semiColon == -1 && whereIndex == -1) {
			return false;
		}
		String restOfExpression, AssignmentExp;
		if (whereIndex == -1) {
			restOfExpression = ";";
			AssignmentExp = parts[parts.length - 1].substring(0, semiColon).trim();
		} else {
			restOfExpression = parts[parts.length - 1].substring(whereIndex).trim();
			AssignmentExp = parts[parts.length - 1].substring(0, whereIndex).trim();
		}
		
		if (!new AssignmentExpression().interpret(AssignmentExp.trim())) {
			return false;
		}
		if (this.nextExpression != null) {
			return this.nextExpression.interpret(restOfExpression.trim());
		} else if (this.nextStatement != null) {
			return this.nextStatement.interpret(restOfExpression.trim());
		}
		return false;
	}
}
