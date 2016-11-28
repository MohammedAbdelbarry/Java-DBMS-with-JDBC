package jdbms.sql.parsing.expressions;

import java.util.ArrayList;

import jdbms.sql.parsing.expressions.util.ColumnExpression;
import jdbms.sql.parsing.expressions.util.StringModifier;
import jdbms.sql.parsing.properties.InputParametersContainer;
import jdbms.sql.parsing.statements.FromStatement;

public class SelectColumnListExpression extends ColumnListExpression {

	private ArrayList<String> columnsNames;
	private StringModifier modifier;
	public SelectColumnListExpression(
			InputParametersContainer parameters) {
		super(new FromStatement(parameters), parameters);
		columnsNames = new ArrayList<>();
		this.modifier = new StringModifier();
	}

	@Override
	public boolean interpret(String sqlExpression) {
		sqlExpression = sqlExpression.trim();
		String modifiedExpression = modifier.modifyString(sqlExpression);
		while (modifiedExpression.indexOf(",") != -1) {
			String currCol = sqlExpression.substring(0,
					modifiedExpression.indexOf(","));
			String modifiedCol = modifiedExpression.substring(0,
					modifiedExpression.indexOf(","));
			sqlExpression = sqlExpression.replace(sqlExpression.substring(0,
					modifiedExpression.indexOf(",")) + ",", "").trim();
			modifiedExpression = modifiedExpression.replace(modifiedCol + ",", "").trim();
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
}
