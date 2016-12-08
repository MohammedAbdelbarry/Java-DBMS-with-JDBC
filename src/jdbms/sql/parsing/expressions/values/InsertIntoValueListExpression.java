package jdbms.sql.parsing.expressions.values;

import java.util.ArrayList;

import jdbms.sql.parsing.expressions.terminal.TerminalExpression;
import jdbms.sql.parsing.expressions.util.StringModifier;
import jdbms.sql.parsing.expressions.util.ValueExpression;
import jdbms.sql.parsing.properties.InputParametersContainer;

/**
 * The Class InsertIntoValueListExpression.
 */
public class InsertIntoValueListExpression extends ValueListExpression {

	private ArrayList< ArrayList<String> > rowsValues;
	private StringModifier modifier;
	
	/**
	 * Instantiates a new insert into value list expression.
	 * @param parameters the input parameters
	 */
	public InsertIntoValueListExpression(
			InputParametersContainer parameters) {
		super(new TerminalExpression(parameters), parameters);
		rowsValues = new ArrayList<>();
		this.modifier = new StringModifier();
	}

	@Override
	public boolean interpret(String sqlExpression) {
		sqlExpression = sqlExpression.trim();
		String modifiedExpression = modifier.
				modifyString(sqlExpression).trim();
		while (modifiedExpression.indexOf(")") != -1) {
			if (!sqlExpression.startsWith("(")) {
				return false;
			} else {
				sqlExpression = sqlExpression.
						replaceFirst("\\(", "").trim();
				modifiedExpression = modifiedExpression.
						replaceFirst("\\(", "").trim();
			}
			String currValues = sqlExpression.substring(0,
					modifiedExpression.indexOf(")")).trim();
			String modifiedValues = modifiedExpression.substring(0,
					modifiedExpression.indexOf(")")).trim();
			ArrayList<String> currValuesList = new ArrayList<>();
			while (modifiedValues.indexOf(",") != -1) {
				if (!new ValueExpression(currValues.substring(0,
						modifiedValues.indexOf(",")).
						trim()).
						isValidExpressionName()) {
					return false;
				}
				currValuesList.add(currValues.substring(0,
						modifiedValues.indexOf(",")).
						trim());
				currValues = currValues.
						substring(modifiedValues.
						indexOf(",") + 1).trim();
				modifiedValues = modifiedValues.
						substring(modifiedValues.
								indexOf(",") + 1).
						trim();
			}
			if (!new ValueExpression(currValues.
					trim()).isValidExpressionName()) {
				return false;
			}
			currValuesList.add(currValues.trim());
			rowsValues.add(currValuesList);
			sqlExpression = sqlExpression.
					substring(modifiedExpression.
						indexOf(")") + 1).trim();
			modifiedExpression = modifiedExpression.
					substring(modifiedExpression.
						indexOf(")") + 1).trim();
			if (!sqlExpression.startsWith(",")) {
				break;
			}
			sqlExpression = sqlExpression.
					replaceFirst(",", "").trim();
			modifiedExpression = modifiedExpression.
					replaceFirst(",", "").trim();
		}
		if (!sqlExpression.trim().startsWith(";")) {
			return false;
		}
		parameters.setValues(rowsValues);
		return super.interpret(sqlExpression.trim());
	}
}
