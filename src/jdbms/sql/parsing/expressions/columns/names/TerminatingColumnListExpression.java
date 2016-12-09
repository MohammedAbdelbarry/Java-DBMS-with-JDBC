package jdbms.sql.parsing.expressions.columns.names;

import java.util.ArrayList;

import jdbms.sql.parsing.expressions.terminal.TerminalExpression;
import jdbms.sql.parsing.expressions.util.ColumnExpression;
import jdbms.sql.parsing.properties.InputParametersContainer;

public class TerminatingColumnListExpression extends ColumnListExpression {

	private final ArrayList<String> columnsNames;

	public TerminatingColumnListExpression(
			final InputParametersContainer parameters) {
		super(new TerminalExpression(parameters), parameters);
		this.columnsNames = new ArrayList<>();
	}

	@Override
	public boolean interpret(String sqlExpression) {
		sqlExpression = sqlExpression.trim();
		final String colList = sqlExpression.substring(0,
				sqlExpression.indexOf(";"));
		final String restOfExpression = sqlExpression.
				substring(sqlExpression.indexOf(";"));
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
