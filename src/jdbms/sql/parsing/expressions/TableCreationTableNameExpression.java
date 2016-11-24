package jdbms.sql.parsing.expressions;

public class TableCreationTableNameExpression extends TableNameExpression {

	public TableCreationTableNameExpression() {
		super(new TableCreationColumnsTypesExpression());
	}
}
