package jdbms.sql.parsing.expressions;

import java.util.ArrayList;
import java.util.List;

import jdbms.sql.parsing.expressions.util.ValueExpression;

public class InsertIntoValueListExpression extends ValueListExpression {

	private List<ArrayList<String>> rows;
	
	public InsertIntoValueListExpression() {
		super(new TerminalExpression());
		rows = new ArrayList<ArrayList<String>>();
	}

	@Override
	public boolean interpret(String sqlExpression) {
		String[] parts = sqlExpression.split(")");
		if (parts[0].trim().startsWith("(")) {
			parts[0].trim().replace("(", "");
			String[] values = parts[0].trim().split(",");
			for (int i = 0; i < values.length; i++) {
				rows.get(0).add(values[i].trim());
				if (!new ValueExpression(values[i].trim()).isValidExpressionName()) {
					return false;
				}
			}
			for (int i = 1; i < parts.length - 1; i++) {
				parts[i] = parts[i].trim();
				if (parts[i].startsWith(",(")) {
					parts[i].trim().replace(",(", "");
					String[] restOfValues = parts[i].trim().split(",");
					for (int j = 0; j < restOfValues.length; j++) {
						rows.get(i).add(restOfValues[j].trim());
						if (!new ValueExpression(restOfValues[j].trim()).isValidExpressionName()) {
							return false;
						}
					}
				}
			}
			return super.interpret(parts[parts.length - 1].trim());
		} else {
			return false;
		}
	}
}
