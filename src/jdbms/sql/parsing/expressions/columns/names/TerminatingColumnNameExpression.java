package jdbms.sql.parsing.expressions.columns.names;

import java.util.ArrayList;

import jdbms.sql.parsing.expressions.terminal.TerminalExpression;
import jdbms.sql.parsing.expressions.util.ColumnExpression;
import jdbms.sql.parsing.properties.InputParametersContainer;

public class TerminatingColumnNameExpression extends ColumnListExpression {

	private ArrayList<String> columnsNames;

	public TerminatingColumnNameExpression(
			InputParametersContainer parameters) {
		super(new TerminalExpression(parameters), parameters);
		this.columnsNames = new ArrayList<>();
	}

	@Override
	public boolean interpret(String sqlExpression) {
		String columnName = sqlExpression.substring(0,
				sqlExpression.indexOf(" ")).trim();
		if (!new ColumnExpression(columnName).isValidColumnName()) {
			return false;
		}
		columnsNames.add(columnName);
		parameters.setColumns(columnsNames);
		return super.interpret(sqlExpression.substring(sqlExpression.indexOf(" ")).trim());
	}
}
