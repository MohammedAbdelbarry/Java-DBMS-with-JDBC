package jdbms.sql.parsing.expressions;

public class TableNameColumnListExpression extends TableNameExpression {

	public TableNameColumnListExpression() {
		super(new InsertColumnListExpression());
	}
}
