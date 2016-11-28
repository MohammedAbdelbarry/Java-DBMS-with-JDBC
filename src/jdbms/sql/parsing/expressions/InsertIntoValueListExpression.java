package jdbms.sql.parsing.expressions;

import java.util.ArrayList;

import jdbms.sql.parsing.expressions.math.AssignmentExpression;
import jdbms.sql.parsing.expressions.util.StringModifier;
import jdbms.sql.parsing.expressions.util.ValueExpression;
import jdbms.sql.parsing.properties.InputParametersContainer;

public class InsertIntoValueListExpression extends ValueListExpression {

	private ArrayList<String[]> rowsValues;
	private StringModifier modifier;
	public InsertIntoValueListExpression(
			InputParametersContainer parameters) {
		super(new TerminalExpression(parameters), parameters);
		rowsValues = new ArrayList<>();
		this.modifier = new StringModifier();
	}

	@Override
	public boolean interpret(String sqlExpression) {
		sqlExpression = sqlExpression.trim();
		String modifiedExpression = modifier.modifyString(sqlExpression);
		/*while (modifiedExpression.indexOf(")") != -1) {
			String currValues = sqlExpression.substring(0,
					modifiedExpression.indexOf(")")).trim();
			String modifiedAssignment = modifiedExpression.substring(0,
					modifiedExpression.indexOf(")")).trim();
			sqlExpression = sqlExpression.replaceFirst(sqlExpression.substring(0,
					modifiedExpression.indexOf(",")) + ",", "").trim();
			modifiedExpression = modifiedExpression.replaceFirst(modifiedAssignment + ",", "").trim();
			//assignmentList.add(new AssignmentExpression(parameters));
			//if (!assignmentList.get(assignmentList.size() - 1).interpret(currAssignmentExp.trim())) {
				//return false;
			//}
		}*/
		String[] parts = sqlExpression.split("\\)");
		if (parts[0].trim().startsWith("(")) {
			parts[0] = parts[0].trim().replaceFirst("\\(", "");
			String[] values = parts[0].trim().split(",");
			for (int i = 0; i < values.length; i++) {
				values[i] = values[i].trim();
				if (!new ValueExpression(values[i]).isValidExpressionName()) {
					return false;
				}
			}
			rowsValues.add(values);
			for (int i = 1; i < parts.length - 1; i++) {
				parts[i] = parts[i].trim();
				if (parts[i].startsWith(",(") || parts[i].startsWith(", (")) {
					parts[i] = parts[i].trim().replace(",(", "").trim();
					parts[i] = parts[i].trim().replace(", (", "").trim();
					String[] restOfValues = parts[i].trim().split(",");
					for (int j = 0; j < restOfValues.length; j++) {
						restOfValues[j] = restOfValues[j].trim();
						if (!new ValueExpression(restOfValues[j].trim()).isValidExpressionName()) {
							return false;
						}
					}
					rowsValues.add(restOfValues);
				} else {
					return false;
				}
			}
			parameters.setValues(rowsValues);
			return super.interpret(parts[parts.length - 1].trim());
		}
		return false;
	}
}
