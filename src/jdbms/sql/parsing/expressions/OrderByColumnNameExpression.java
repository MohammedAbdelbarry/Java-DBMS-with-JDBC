package jdbms.sql.parsing.expressions;

import jdbms.sql.parsing.expressions.util.ColumnExpression;
import jdbms.sql.parsing.properties.InputParametersContainer;
import jdbms.sql.parsing.statements.SortingStatement;

public class OrderByColumnNameExpression extends ColumnListExpression {

	public OrderByColumnNameExpression(InputParametersContainer parameters) {
		super(new SortingStatement(parameters), parameters);
	}

	@Override
	public boolean interpret(String sqlExpression) {
		String columnName = sqlExpression.substring(0,
				sqlExpression.indexOf(" ")).trim();
		if (!new ColumnExpression(columnName).isValidColumnName()) {
			return false;
		}
		parameters.setSortingColumnName(columnName);
		return super.interpret(sqlExpression.substring(sqlExpression.indexOf(" ")).trim());
	}
}
