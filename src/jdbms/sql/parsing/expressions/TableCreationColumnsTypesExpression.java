package jdbms.sql.parsing.expressions;

import java.util.ArrayList;
import java.util.List;

import jdbms.sql.data.ColumnIdentifier;
import jdbms.sql.datatypes.util.SQLTypeFactory;
import jdbms.sql.errors.ErrorHandler;
import jdbms.sql.parsing.expressions.util.ColumnExpression;
import jdbms.sql.parsing.properties.InputParametersContainer;

/**
 * The Class TableCreationColumnsTypesExpression.
 */
public class TableCreationColumnsTypesExpression
extends ColumnsDatatypesExpression {

	private List<String> dataTypes;
	private ArrayList<ColumnIdentifier> columnsDataTypes = null;
	
	/**
	 * Instantiates a new table creation columns types expression.
	 * @param parameters the parameters
	 */
	public TableCreationColumnsTypesExpression(
			InputParametersContainer parameters) {
		super(new TerminalExpression(parameters), parameters);
		columnsDataTypes = new ArrayList<>();
		dataTypes = new ArrayList<>(
				SQLTypeFactory.
				getInstance().getRegisteredTypes());
	}

	@Override
	public boolean interpret(String sqlExpression) {
		String[] parts = sqlExpression.
				trim().split("\\)");
		if (parts[0].startsWith("(")) {
			parts[0] = parts[0].replaceFirst("\\(", "");
			String[] types = parts[0].split(",");
			for (String colType : types) {
				colType = colType.trim();
				String colName = "", dataType = "";
				try {
					colName = colType.trim().substring(0,
							colType.indexOf(" ")).
							trim();
					dataType = colType.trim().
							substring(colType.
									indexOf(" ") + 1).
							trim();
				} catch(Exception e) {
					ErrorHandler.
					printSyntaxErrorNear("Columns Datatypes");
				}
				if (new ColumnExpression(colName).
						isValidColumnName()
						&& dataTypes.
						contains(dataType)) {
					columnsDataTypes.
					add(new ColumnIdentifier(colName,
							dataType));
				} else {
					ErrorHandler.
					printSyntaxErrorNear("Columns Datatypes");
					return false;
				}
			}
			parameters.setColumnDefinitions(columnsDataTypes);
			return super.interpret(parts[parts.length - 1].trim());
		}
		ErrorHandler.printSyntaxErrorNear("Opening Parenthesis");
		return false;
	}
}
