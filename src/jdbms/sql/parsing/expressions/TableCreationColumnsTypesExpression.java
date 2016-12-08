package jdbms.sql.parsing.expressions;

import java.util.ArrayList;

import jdbms.sql.data.ColumnIdentifier;
import jdbms.sql.datatypes.util.DataTypesValidator;
import jdbms.sql.parsing.expressions.util.ColumnExpression;
import jdbms.sql.parsing.properties.InputParametersContainer;

/**
 * The Class TableCreationColumnsTypesExpression.
 */
public class TableCreationColumnsTypesExpression
extends ColumnsDatatypesExpression {

	private ArrayList<ColumnIdentifier> columnsDataTypes = null;
	private final DataTypesValidator validator;

	/**
	 * Instantiates a new table creation columns types expression.
	 * @param parameters the parameters
	 */
	public TableCreationColumnsTypesExpression(
			final InputParametersContainer parameters) {
		super(new TerminalExpression(parameters), parameters);
		columnsDataTypes = new ArrayList<>();
		validator = new DataTypesValidator();
	}

	@Override
	public boolean interpret(final String sqlExpression) {
		final String[] parts = sqlExpression.
				trim().split("\\)");
		if (parts[0].startsWith("(")) {
			parts[0] = parts[0].replaceFirst("\\(", "");
			final String[] types = parts[0].split(",");
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
				} catch(final Exception e) {
					return false;
				}
				if (new ColumnExpression(colName).
						isValidColumnName()
						&& validator.
						isSupportedDataType(dataType)) {
					columnsDataTypes.
					add(new ColumnIdentifier(colName,
							dataType));
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
