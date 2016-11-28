package jdbms.sql.parsing.expressions;

import java.util.ArrayList;

import jdbms.sql.errors.ErrorHandler;
import jdbms.sql.parsing.properties.InputParametersContainer;
import jdbms.sql.parsing.statements.FromStatement;

public class ColumnWildcardExpression extends ColumnListExpression {

	public ColumnWildcardExpression(InputParametersContainer parameters) {
		super(new FromStatement(parameters), parameters);
	}

	@Override
	public boolean interpret(String sqlExpression) {
		if (sqlExpression.startsWith("*")) {
			ArrayList<String> list = new ArrayList<>();
			list.add("*");
			parameters.setColumns(list);
			return super.interpret(sqlExpression.substring(sqlExpression.indexOf("*") + 1).trim());
		}
		ErrorHandler.printSyntaxErrorNear("*");
		return false;
	}
}
