package jdbms.sql.parsing.expressions;

import jdbms.sql.parsing.statements.ValueStatement;

public class TableNameValueListExpression extends TableNameExpression {

	public TableNameValueListExpression() {
		super(new ValueStatement());
	}
}
