package jdbms.sql.parsing.expressions;

import java.util.HashMap;
import java.util.List;

import jdbms.sql.parsing.expressions.util.ColumnExpression;
import jdbms.sql.parsing.expressions.util.DataTypesConstants;
import jdbms.sql.parsing.properties.InputParametersContainer;

public class TableCreationColumnsTypesExpression extends TableCreationTableInfo {
	private List<String> dataTypes;
	private HashMap<String, String> columnsDataTypes = null;

	public TableCreationColumnsTypesExpression(
			InputParametersContainer parameters) {
		super(new TerminalExpression(parameters), parameters);
		columnsDataTypes = new HashMap<>();
		dataTypes = DataTypesConstants.DATA_TPYES;
	}

	@Override
	public boolean interpret(String sqlExpression) {
		String[] parts = sqlExpression.split("\\)");
		if (parts[0].startsWith("(")) {
			parts[0] = parts[0].replace("(", "");
			String[] types = parts[0].split(",");
			for (String colType : types) {
				String colName = colType.trim().substring(0, colType.indexOf(" "));
				String dataType = colType.trim().substring(colType.indexOf(" ") + 1);
				if (new ColumnExpression(colName).isValidColumnName()
						&& dataTypes.contains(dataType)) {
					columnsDataTypes.put(colName, dataType);
				} else {
					return false;
				}
			}
			parameters.setColumnDefinitions(columnsDataTypes);
			return super.interpret(parts[parts.length - 1].trim());
		}
		return false;
	}
}
