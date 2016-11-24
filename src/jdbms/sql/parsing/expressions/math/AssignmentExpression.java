package jdbms.sql.parsing.expressions.math;

public class AssignmentExpression extends BinaryExpression {
	private static final String SYMBOL = "=";
	public AssignmentExpression() {
		super(SYMBOL);
	}

}
