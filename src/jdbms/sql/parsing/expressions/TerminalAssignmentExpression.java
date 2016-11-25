package jdbms.sql.parsing.expressions;

public class TerminalAssignmentExpression extends AssignmentListExpression {

	public TerminalAssignmentExpression() {
		super(new TerminalExpression());
	}
}
