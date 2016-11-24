package jdbms.sql.parsing.properties;

import jdbms.sql.parsing.expressions.math.AssignmentExpression;
import jdbms.sql.parsing.expressions.math.BooleanExpression;

public class UpdatingParameters {
	private BooleanExpression condition;
	private AssignmentExpression assignment;
	private String tableName;
	public UpdatingParameters() {

	}
	public BooleanExpression getCondition() {
		return condition;
	}
	public void setCondition(BooleanExpression condition) {
		this.condition = condition;
	}
	public AssignmentExpression getAssignment() {
		return assignment;
	}
	public void setAssignment(AssignmentExpression assignment) {
		this.assignment = assignment;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String table) {
		this.tableName = table;
	}

}
