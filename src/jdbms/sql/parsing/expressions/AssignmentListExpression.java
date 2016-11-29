package jdbms.sql.parsing.expressions;

import java.util.ArrayList;

import jdbms.sql.errors.ErrorHandler;
import jdbms.sql.parsing.expressions.math.AssignmentExpression;
import jdbms.sql.parsing.expressions.util.StringModifier;
import jdbms.sql.parsing.properties.InputParametersContainer;
import jdbms.sql.parsing.statements.Statement;

/**
 * The Class AssignmentListExpression.
 */
public abstract class AssignmentListExpression implements Expression {

	private Expression nextExpression;
	private Statement nextStatement;
	private StringModifier modifier;
	private ArrayList<AssignmentExpression> assignmentList;
	protected InputParametersContainer parameters;

	/**
	 * Instantiates a new assignment list expression.
	 * @param nextExpression the next expression
	 * @param parameters the parameters
	 */
	public AssignmentListExpression(Expression nextExpression,
			InputParametersContainer parameters) {
		this.nextExpression = nextExpression;
		this.parameters = parameters;
		this.assignmentList = new ArrayList<>();
		this.modifier = new StringModifier();
	}

	/**
	 * Instantiates a new assignment list expression.
	 * @param nextStatement the next statement
	 * @param parameters the parameters
	 */
	public AssignmentListExpression(Statement nextStatement,
			InputParametersContainer parameters) {
		this.nextStatement = nextStatement;
		this.parameters = parameters;
		this.assignmentList = new ArrayList<>();
		this.modifier = new StringModifier();
	}

	@Override
	public boolean interpret(String sqlExpression) {
		sqlExpression = sqlExpression.trim();
		String modifiedExpression = modifier.
				modifyString(sqlExpression).trim();
		while (modifiedExpression.indexOf(",") != -1) {
			assignmentList.
			add(new AssignmentExpression(parameters));
			if (!assignmentList.
					get(assignmentList.size() - 1).
					interpret(sqlExpression.substring(0,
							modifiedExpression.
							indexOf(",")).trim())) {
				ErrorHandler.printSyntaxErrorNear("SET");
				return false;
			}
			sqlExpression = sqlExpression.
					substring(modifiedExpression.
						indexOf(",") + 1).trim();
			modifiedExpression = modifiedExpression.
					substring(modifiedExpression.
						indexOf(",") + 1).trim();
		}
		int seperatorIndex
		= modifiedExpression.indexOf("WHERE");
		if (seperatorIndex == -1) {
			seperatorIndex = modifiedExpression.indexOf(";");
		}
		assignmentList.add(new AssignmentExpression(parameters));
		if (!assignmentList.get(assignmentList.size() - 1).
				interpret(sqlExpression.
						substring(0, seperatorIndex).
						trim())) {
			ErrorHandler.printSyntaxErrorNear("SET");
			return false;
		}
		parameters.setAssignmentList(this.assignmentList);
		if (this.nextExpression != null) {
			return this.nextExpression.
					interpret(sqlExpression.
							substring(seperatorIndex)
							.trim());
		} else if (this.nextStatement != null) {
			return this.nextStatement.
					interpret(sqlExpression.
							substring(seperatorIndex)
							.trim());
		}
		return false;
	}
}
