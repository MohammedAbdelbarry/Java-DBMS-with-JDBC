package jdbms.sql.parsing.expressions;

import jdbms.sql.parsing.statements.WhereStatement;

public class TableConditionalExpression extends TableNameExpression {

	public TableConditionalExpression() {
		super(new WhereStatement());
	}
}
