package jdbms.sql.parsing.expressions;

import java.util.ArrayList;

import jdbms.sql.parsing.expressions.util.ValueExpression;
import jdbms.sql.parsing.properties.InputParametersContainer;

public class InsertIntoValueListExpression extends ValueListExpression {

	private ArrayList<String[]> rowsValues;

	public InsertIntoValueListExpression(
			InputParametersContainer parameters) {
		super(new TerminalExpression(parameters), parameters);
		rowsValues = new ArrayList<>();
	}

	@Override
	public boolean interpret(String sqlExpression) {
		String[] parts = sqlExpression.split("\\)");
		if (parts[0].trim().startsWith("(")) {
			parts[0] = parts[0].trim().replace("(", "");
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
				if (parts[i].startsWith(",(")) { // The bug is here.
					parts[i].trim().replace(",(", "");
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
