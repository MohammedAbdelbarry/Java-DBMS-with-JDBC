package jdbms.sql.parsing.expressions;

import java.util.ArrayList;
import java.util.List;

import jdbms.sql.parsing.expressions.util.ColumnExpression;
import jdbms.sql.parsing.statements.FromStatement;

public class SelectColumnListExpression extends ColumnListExpression {

	List<String> columnsNames;
	public SelectColumnListExpression() {
		super(new FromStatement());
		columnsNames = new ArrayList<>();
	}

	@Override
	public boolean interpret(String sqlExpression) {
		String[] columns = sqlExpression.split(",");
		columns[columns.length - 1] = columns[columns.length - 1].trim();
		String restOfExp = columns[columns.length - 1]
				.substring(columns[columns.length - 1].indexOf(" ") + 1);
		columns[columns.length - 1] = columns[columns.length - 1]
				.substring(0, columns[columns.length - 1].indexOf(" "));
		for (int i = 0; i < columns.length; i++) {
			columnsNames.add(columns[i]);
			if (!new ColumnExpression(columns[i]).isValidColumnName()) {
				return false;
			}
		}
		return super.interpret(restOfExp);
	}
}
