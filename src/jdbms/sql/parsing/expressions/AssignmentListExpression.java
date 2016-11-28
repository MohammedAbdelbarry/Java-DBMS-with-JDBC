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
		String modifiedExpression = removeString(sqlExpression).trim();
		while (modifiedExpression.indexOf(",") != -1) {
			String currAssignmentExp = sqlExpression.substring(0,
					modifiedExpression.indexOf(","));
			String modifiedAssignment = modifiedExpression.substring(0,
					modifiedExpression.indexOf(","));
			sqlExpression = sqlExpression.replaceFirst(sqlExpression.substring(0,
					modifiedExpression.indexOf(",")) + ",", "").trim();
			modifiedExpression = modifiedExpression.replaceFirst(modifiedAssignment + ",", "").trim();
			assignmentList.add(new AssignmentExpression(parameters));
			if (!assignmentList.get(assignmentList.size() - 1).interpret(currAssignmentExp.trim())) {
				return false;
			}
		}
		int seperatorIndex = modifiedExpression.indexOf("WHERE");
		if (seperatorIndex == -1) {
			seperatorIndex = modifiedExpression.indexOf(";");
		}
		assignmentList.add(new AssignmentExpression(parameters));
		if (!assignmentList.get(assignmentList.size() - 1).
				interpret(sqlExpression.substring(0, seperatorIndex).trim())) {
			return false;
		}
		parameters.setAssignmentList(this.assignmentList);
		if (this.nextExpression != null) {
			return this.nextExpression.interpret(sqlExpression.substring(seperatorIndex).trim());
		} else if (this.nextStatement != null) {
			return this.nextStatement.interpret(sqlExpression.substring(seperatorIndex).trim());
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
