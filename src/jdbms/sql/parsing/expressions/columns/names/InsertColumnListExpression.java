package jdbms.sql.parsing.expressions.columns.names;

import java.util.ArrayList;

import jdbms.sql.parsing.expressions.util.ColumnExpression;
import jdbms.sql.parsing.properties.InputParametersContainer;
import jdbms.sql.parsing.statements.ValueStatement;

/**
 * The Class InsertColumnListExpression.
 */
public class InsertColumnListExpression extends ColumnListExpression {

	private ArrayList<String> columnsNames;
	
	/**
	 * Instantiates a new insert column list expression.
	 * @param parameters the input parameters
	 */
	public InsertColumnListExpression(
			InputParametersContainer parameters) {
		super(new ValueStatement(parameters), parameters);
		this.columnsNames = new ArrayList<>();
	}

	@Override
	public boolean interpret(String sqlExpression) {
		sqlExpression = sqlExpression.trim();
		String allColumns
		= sqlExpression.substring(0,
				sqlExpression.indexOf(")")).trim();
		String restOfExpression
		= sqlExpression.substring(sqlExpression.indexOf(")") + 1);
		if (allColumns.startsWith("(")) {
			allColumns = allColumns.replaceFirst("\\(", "").trim();
			String[] columns = allColumns.split(",");
			for (String col : columns) {
				if (new ColumnExpression(col.
						trim()).isValidColumnName()) {
					columnsNames.add(col.trim());
				} else {
					return false;
				}
			}
			parameters.setColumns(columnsNames);
			return super.interpret(restOfExpression.trim());
		}
		return false;
	}
}
