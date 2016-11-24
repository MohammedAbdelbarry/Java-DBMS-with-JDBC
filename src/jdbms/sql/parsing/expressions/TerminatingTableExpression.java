package jdbms.sql.parsing.expressions;

public class TerminatingTableExpression extends TableNameExpression {

	public TerminatingTableExpression() {
		super(new TerminalExpression());
	}
}
