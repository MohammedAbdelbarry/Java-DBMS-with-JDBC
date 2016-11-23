package jdbms.sql.parsing.expressions;

import java.util.ArrayList;
import java.util.List;

import jdbms.sql.parsing.expressions.util.ColumnExpression;
import jdbms.sql.parsing.statements.FromStatement;

public class SelectColumnListExpression extends ColumnListExpression {

	List<ColumnExpression> columnsNames;
	public SelectColumnListExpression() {
		super(null, new FromStatement());
		columnsNames = new ArrayList<ColumnExpression>();
	}

	@Override
	public boolean interpret(String sqlExpression) {
		String[] columns = sqlExpression.split(",");
		columns[columns.length] = columns[columns.length].trim();
		String restOfExp = columns[columns.length]
				.substring(columns[columns.length].indexOf(" ") + 1);
		columns[columns.length] = columns[columns.length]
				.substring(0, columns[columns.length].indexOf(" "));
		for (int i = 0; i < columns.length; i++) {
			columns[i] = columns[i].trim();
			columnsNames.add(new ColumnExpression(columns[i]));
			if (!columnsNames.get(i).isValidColumnName()) {
				return false;
			}
		}
		return super.interpret(restOfExp);
	}
}
