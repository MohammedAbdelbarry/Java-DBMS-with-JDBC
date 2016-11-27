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
		sqlExpression = sqlExpression.trim();
		String[] parts = sqlExpression.split(",");
		for (int i = 0; i < parts.length - 1; i++) {
			assignmentList.add(new AssignmentExpression(parameters));
			if (!assignmentList.get(i).interpret(parts[i].trim())) {
				return false;
			}
		}
		parts[parts.length - 1] = parts[parts.length - 1].trim();
		String modifiedExpression = removeString(parts[parts.length - 1].trim()).trim();
		int seperatorIndex = modifiedExpression.indexOf("WHERE");
		if (seperatorIndex == -1) {
			seperatorIndex = modifiedExpression.indexOf(";");
		}
		String AssignmentExp = parts[parts.length - 1].trim().substring(0, seperatorIndex).trim();
		String restOfExpression = parts[parts.length - 1].trim().substring(seperatorIndex);
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
	private String removeString(String expression) {
		String stringless = "";
		for (int i = 0; i < expression.length(); i++) {
			stringless += expression.charAt(i);
			if (expression.charAt(i) == '\'') {
				for (int j = i + 1; j < expression.length(); j++) {
					if (expression.charAt(j) == '\'') {
						stringless += expression.charAt(j);
						i = j;
						break;
					}
					stringless += "s";
				}
			} else if (stringless.charAt(i) == '"') {
				for (int j = i + 1; j < expression.length(); j++) {
					if (expression.charAt(j) == '"') {
						stringless += expression.charAt(j);
						i = j;
						break;
					}
					stringless += "s";
				}
			}
		}
		return stringless;
	}
}
