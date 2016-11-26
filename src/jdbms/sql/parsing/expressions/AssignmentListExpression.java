package jdbms.sql.parsing.expressions;

import java.util.ArrayList;

import jdbms.sql.parsing.expressions.math.AssignmentExpression;
import jdbms.sql.parsing.properties.InputParametersContainer;
import jdbms.sql.parsing.statements.Statement;

public abstract class AssignmentListExpression implements Expression {

	private Expression nextExpression;
	private Statement nextStatement;
	protected InputParametersContainer parameters;
	ArrayList<AssignmentExpression> assignmentList;
	public AssignmentListExpression(Expression nextExpression,
			InputParametersContainer parameters) {
		this.nextExpression = nextExpression;
		this.parameters = parameters;
		this.assignmentList = new ArrayList<>();
	}

	public AssignmentListExpression(Statement nextStatement,
			InputParametersContainer parameters) {
		this.nextStatement = nextStatement;
		this.parameters = parameters;
		this.assignmentList = new ArrayList<>();
	}

	@Override
	public boolean interpret(String sqlExpression) {
		String[] parts = sqlExpression.split(",");
		for (int i = 0; i < parts.length - 1; i++) {
			assignmentList.add(new AssignmentExpression(parameters));
			if (!assignmentList.get(i).interpret(parts[i].trim())) {
				return false;
			}
		}
		int whereIndex = parts[parts.length - 1].trim().indexOf(" WHERE "),
				semiColon = parts[parts.length - 1].trim().indexOf(" ;");
		if (semiColon == -1 && whereIndex == -1) {
			return false;
		}
		String restOfExpression, AssignmentExp;
		if (whereIndex == -1) {
			restOfExpression = ";";
			AssignmentExp = parts[parts.length - 1].trim().substring(0, semiColon).trim();
		} else {
			restOfExpression = parts[parts.length - 1].trim().substring(whereIndex).trim();
			AssignmentExp = parts[parts.length - 1].trim().substring(0, whereIndex).trim();
		}
		assignmentList.add(new AssignmentExpression(parameters));
		if (!assignmentList.get(assignmentList.size() - 1).interpret(AssignmentExp.trim())) {
			return false;
		}
		parameters.setAssignmentList(this.assignmentList);
		if (this.nextExpression != null) {
			return this.nextExpression.interpret(restOfExpression.trim());
		} else if (this.nextStatement != null) {
			return this.nextStatement.interpret(restOfExpression.trim());
		}
		return false;
	}
}
