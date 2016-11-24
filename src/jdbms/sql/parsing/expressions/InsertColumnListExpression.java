package jdbms.sql.parsing.expressions;

import java.util.List;

import jdbms.sql.parsing.expressions.util.ColumnExpression;
import jdbms.sql.parsing.statements.ValueStatement;

public class InsertColumnListExpression extends ColumnListExpression {

	List<ColumnExpression> columnsNames;
	public InsertColumnListExpression() {
		super(new ValueStatement());
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
			columnsNames.add(new ColumnExpression(columns[i]));
			if (!columnsNames.get(i).isValidColumnName()) {
				return false;
			}
		}
		return super.interpret(restOfExp);
	}
}
