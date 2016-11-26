package jdbms.sql.parsing.properties;

import java.util.ArrayList;

import jdbms.sql.parsing.expressions.math.AssignmentExpression;
import jdbms.sql.parsing.expressions.math.BooleanExpression;

public class UpdatingParameters {
	private BooleanExpression condition;
	private ArrayList<AssignmentExpression> assignmentList;
	private String tableName;
	public UpdatingParameters() {

	}
	public BooleanExpression getCondition() {
		return condition;
	}
	public void setCondition(BooleanExpression condition) {
		this.condition = condition;
	}
	public ArrayList<AssignmentExpression> getAssignmentList() {
		return assignmentList;
	}
	public void setAssignmentList(ArrayList<AssignmentExpression> assignmentList) {
		this.assignmentList = assignmentList;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String table) {
		this.tableName = table;
	}

}
