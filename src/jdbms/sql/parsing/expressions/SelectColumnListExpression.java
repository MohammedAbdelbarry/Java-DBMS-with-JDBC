package jdbms.sql.parsing.expressions;

import java.util.ArrayList;
import java.util.List;

import jdbms.sql.parsing.expressions.math.AssignmentExpression;
import jdbms.sql.parsing.expressions.util.ColumnExpression;
import jdbms.sql.parsing.properties.InputParametersContainer;
import jdbms.sql.parsing.statements.FromStatement;

public class SelectColumnListExpression extends ColumnListExpression {

	ArrayList<String> columnsNames;
	public SelectColumnListExpression(
			InputParametersContainer parameters) {
		super(new FromStatement(parameters), parameters);
		columnsNames = new ArrayList<>();
	}

	@Override
	public boolean interpret(String sqlExpression) {
		sqlExpression = sqlExpression.trim();
		String modifiedExpression = removeString(sqlExpression);
		while (modifiedExpression.indexOf(",") != -1) {
			String currCol = sqlExpression.substring(0,
					modifiedExpression.indexOf(","));
			String modifiedCol = modifiedExpression.substring(0,
					modifiedExpression.indexOf(","));
			sqlExpression = sqlExpression.replaceFirst(sqlExpression.substring(0,
					modifiedExpression.indexOf(",")) + ",", "").trim();
			modifiedExpression = modifiedExpression.replaceFirst(modifiedCol + ",", "").trim();
			columnsNames.add(currCol.trim());
			if (!new ColumnExpression(currCol.trim()).isValidColumnName()) {
				return false;
			}
		}
		columnsNames.add(sqlExpression.substring(0, sqlExpression.indexOf(" ")).trim());
		if (!new ColumnExpression(columnsNames.get(columnsNames.size() - 1)).isValidColumnName()) {
			return false;
		}
		parameters.setColumns(columnsNames);
		return super.interpret(sqlExpression.trim().substring(sqlExpression.indexOf(" ")).trim());
	}

	private String removeString(String expression) {
		String stringless = "";
		for (int i = 0; i < expression.length(); i++) {
			stringless += expression.charAt(i);
			if (expression.charAt(i) == '\'') {
				for (int j = i + 1; j < expression.length(); j++) {
					if (expression.charAt(j) == '\'') {
						stringless += expression.charAt(j);
						i = j;
						break;
					}
					stringless += "s";
				}
			} else if (stringless.charAt(i) == '"') {
				for (int j = i + 1; j < expression.length(); j++) {
					if (expression.charAt(j) == '"') {
						stringless += expression.charAt(j);
						i = j;
						break;
					}
					stringless += "s";
				}
			}
		}
		return stringless;
	}
}
