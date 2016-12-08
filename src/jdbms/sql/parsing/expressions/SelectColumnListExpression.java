package jdbms.sql.parsing.expressions;

import java.util.ArrayList;

import jdbms.sql.parsing.expressions.util.ColumnExpression;
import jdbms.sql.parsing.expressions.util.StringModifier;
import jdbms.sql.parsing.properties.InputParametersContainer;
import jdbms.sql.parsing.statements.FromStatement;

/**
 * The Class SelectColumnListExpression.
 */
public class SelectColumnListExpression extends ColumnListExpression {

	private static final String FROMSTATEMENT = "FROM";
	private final ArrayList<String> columnsNames;
	private final StringModifier modifier;

	/**
	 * Instantiates a new select column list expression.
	 * @param parameters the parameters
	 */
	public SelectColumnListExpression(
			final InputParametersContainer parameters) {
		super(new FromStatement(parameters), parameters);
		this.columnsNames = new ArrayList<>();
		this.modifier = new StringModifier();
	}

	@Override
	public boolean interpret(String sqlExpression) {
		sqlExpression = sqlExpression.trim();
		final String modifiedExpression
		= modifier.modifyString(sqlExpression);
		if (!modifiedExpression.contains(FROMSTATEMENT)) {
			return false;
		}
		final String colList = sqlExpression.substring(0,
				modifiedExpression.indexOf(FROMSTATEMENT)).trim();
		final String restOfExpression = sqlExpression.
				substring(modifiedExpression.indexOf(FROMSTATEMENT)).trim();
		final String[] parts = colList.split(",");
		for (String col : parts) {
			col = col.trim();
			if (!new ColumnExpression(col).isValidColumnName()) {
				return false;
			}
			columnsNames.add(col);
		}
		parameters.setColumns(columnsNames);
		return super.interpret(restOfExpression);
	}
}
