package jdbms.sql.parsing.expressions;

import jdbms.sql.parsing.statements.SetStatement;

public class TableUpdateTableNameExpression extends TableNameExpression {

	public TableUpdateTableNameExpression() {
		super(new SetStatement());
	}
}
