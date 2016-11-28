package jdbms.sql.parsing.expressions;

import java.util.ArrayList;
import java.util.List;

import jdbms.sql.data.ColumnIdentifier;
import jdbms.sql.datatypes.util.SQLTypeFactory;
import jdbms.sql.errors.ErrorHandler;
import jdbms.sql.parsing.expressions.util.ColumnExpression;
import jdbms.sql.parsing.properties.InputParametersContainer;

public class DefineColumnExpression extends ColumnsDatatypesExpression {

	private List<String> dataTypes;
	private ArrayList<ColumnIdentifier> columnsDataTypes = null;
	public DefineColumnExpression(InputParametersContainer parameters) {
		super(new TerminalExpression(parameters), parameters);
		this.columnsDataTypes = new ArrayList<>();
		dataTypes = new ArrayList<>(
				SQLTypeFactory.
				getInstance().getRegisteredTypes());
	}

	@Override
	public boolean interpret(String sqlExpression) {
		sqlExpression = sqlExpression.trim();
		String col = sqlExpression.substring(0, sqlExpression.indexOf(" ")).trim();
		String rightPart = sqlExpression.substring(sqlExpression.indexOf(" ") + 1).trim();
		if (new ColumnExpression(col).isValidColumnName() &&
				dataTypes.contains(rightPart.substring(0, rightPart.indexOf(" ")).trim())) {
			columnsDataTypes.add(new ColumnIdentifier(col,
					rightPart.substring(0, rightPart.indexOf(" ")).trim()));
			parameters.setColumnDefinitions(columnsDataTypes);
			return super.interpret(rightPart.substring(rightPart.indexOf(" ") + 1).trim());
		}
		ErrorHandler.printSyntaxErrorNear("Column Datatype");
		return false;
	}
}
