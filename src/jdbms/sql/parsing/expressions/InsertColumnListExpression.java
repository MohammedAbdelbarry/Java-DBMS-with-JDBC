package jdbms.sql.parsing.expressions;

import java.util.ArrayList;
import java.util.List;

import jdbms.sql.parsing.expressions.util.ColumnExpression;
import jdbms.sql.parsing.statements.ValueStatement;

public class InsertColumnListExpression extends ColumnListExpression {

	List<String> columnsNames;
	public InsertColumnListExpression() {
		super(new ValueStatement());
		this.columnsNames = new ArrayList<String>();
	}

	@Override
	public boolean interpret(String sqlExpression) {
		String allColumns = sqlExpression.substring(0, sqlExpression.indexOf(")")).trim();
		String restOfExpression = sqlExpression.substring(sqlExpression.indexOf(")") + 1);
		if (allColumns.startsWith("(")) {
			allColumns = allColumns.replace("(", "").trim();
			String[] columns = allColumns.split(",");
			for (String col : columns) {
				if (new ColumnExpression(col.trim()).isValidColumnName()) {
					columnsNames.add(col.trim());
				} else {
					return false;
				}
			}
			return super.interpret(restOfExpression.trim());
		}
		return false;
	}
}
