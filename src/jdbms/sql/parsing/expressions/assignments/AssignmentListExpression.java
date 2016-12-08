package jdbms.sql.parsing.expressions.assignments;

import java.util.ArrayList;

import jdbms.sql.parsing.expressions.Expression;
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
	private final StringModifier modifier;
	private final ArrayList<AssignmentExpression> assignmentList;
	protected InputParametersContainer parameters;

	/**
	 * Instantiates a new assignment list expression.
	 * @param nextExpression the next expression
	 * @param parameters the parameters
	 */
	public AssignmentListExpression(final Expression nextExpression,
			final InputParametersContainer parameters) {
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
	public AssignmentListExpression(final Statement nextStatement,
			final InputParametersContainer parameters) {
		this.nextStatement = nextStatement;
		this.parameters = parameters;
		this.assignmentList = new ArrayList<>();
		this.modifier = new StringModifier();
	}

	@Override
	public boolean interpret(String sqlExpression) {
		sqlExpression = sqlExpression.trim();
		final String modifiedExpression = modifier.
				modifyString(sqlExpression).trim();
		int seperatorIndex
		= modifiedExpression.indexOf("WHERE");
		if (seperatorIndex == -1) {
			seperatorIndex = modifiedExpression.indexOf(";");
		}
		if (seperatorIndex == -1) {
			return false;
		}
		final String assignList = sqlExpression.substring(0, seperatorIndex).trim();
		final String restOfExpression = sqlExpression.substring(seperatorIndex).trim();
		final String[] parts = assignList.split(",");
		for (String assignment : parts) {
			assignment = assignment.trim();
			assignmentList.add(new AssignmentExpression(parameters));
			if (!assignmentList.get(assignmentList.size() - 1).
					interpret(assignment)) {
				return false;
			}

		}
		parameters.setAssignmentList(this.assignmentList);
		if (this.nextExpression != null) {
			return this.nextExpression.
					interpret(restOfExpression);
		} else if (this.nextStatement != null) {
			return this.nextStatement.
					interpret(restOfExpression);
		}
		return false;
	}
}
