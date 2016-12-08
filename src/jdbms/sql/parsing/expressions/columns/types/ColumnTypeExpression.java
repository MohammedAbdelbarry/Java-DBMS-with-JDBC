package jdbms.sql.parsing.expressions.columns.types;

import java.util.ArrayList;

import jdbms.sql.data.ColumnIdentifier;
import jdbms.sql.datatypes.util.DataTypesValidator;
import jdbms.sql.parsing.expressions.terminal.TerminalExpression;
import jdbms.sql.parsing.expressions.util.ColumnExpression;
import jdbms.sql.parsing.properties.InputParametersContainer;

/**
 * The Class DefineColumnExpression.
 */
public class ColumnTypeExpression extends ColumnsDatatypesExpression {

	private ArrayList<ColumnIdentifier> columnsDataTypes = null;
	private final DataTypesValidator validator;

	/**
	 * Instantiates a new define column expression.
	 * @param parameters the input parameters
	 */
	public ColumnTypeExpression(final InputParametersContainer parameters) {
		super(new TerminalExpression(parameters), parameters);
		this.columnsDataTypes = new ArrayList<>();
		this.validator = new DataTypesValidator();
	}

	@Override
	public boolean interpret(String sqlExpression) {
		sqlExpression = sqlExpression.trim();
		final String col = sqlExpression.substring(0,
				sqlExpression.indexOf(" ")).trim();
		final String rightPart = sqlExpression.
				substring(sqlExpression.
						indexOf(" ") + 1).trim();
		if (new ColumnExpression(col).isValidColumnName()
				&& this.validator.
				isSupportedDataType(rightPart.
						substring(0,
								rightPart.
								indexOf(" ")).trim())) {
			columnsDataTypes.add(new ColumnIdentifier(col,
					rightPart.substring(0,
							rightPart.
							indexOf(" ")).trim()));
			parameters.setColumnDefinitions(columnsDataTypes);
			return super.interpret(rightPart.
					substring(rightPart.indexOf(" ") + 1).
					trim());
		}
		return false;
	}
}