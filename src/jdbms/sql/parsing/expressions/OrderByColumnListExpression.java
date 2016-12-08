package jdbms.sql.parsing.expressions;

import java.util.ArrayList;

import jdbms.sql.parsing.expressions.util.ColumnOrder;
import jdbms.sql.parsing.properties.InputParametersContainer;

public class OrderByColumnListExpression extends ColumnListExpression {

	private ArrayList<ColumnOrder> columnsOrder;

	public OrderByColumnListExpression(InputParametersContainer parameters) {
		super(new TerminalExpression(parameters), parameters);
		this.columnsOrder = new ArrayList<>();
	}

	@Override
	public boolean interpret(String sqlExpression) {
		sqlExpression = sqlExpression.trim();
		String[] parts = sqlExpression.split(",");
		for (int i = 0 ; i < parts.length - 1; i++) {
			parts[i] = parts[i].trim();
			if (parts[i].indexOf(" ") == -1) {
				ColumnOrder currColOrder = new ColumnOrder(parts[i], "ASC");
				if (!currColOrder.isValidColumnOrder()) {
					return false;
				}
				columnsOrder.add(currColOrder);
			} else {
				ColumnOrder currColOrder = new ColumnOrder(parts[i].
						substring(0, parts[i].indexOf(" ")),
						parts[i].substring(parts[i].indexOf(" ")).trim()); 
				if (!currColOrder.isValidColumnOrder()) {
					return false;
				}
				columnsOrder.add(currColOrder);
			}
		}
		String lastColOrder = parts[parts.length - 1].substring(0,
				parts[parts.length - 1].indexOf(";")).trim();
		if (lastColOrder.indexOf(" ") == -1) {
			ColumnOrder last = new ColumnOrder(lastColOrder, "ASC");
			if (!last.isValidColumnOrder()) {
				return false;
			}
			columnsOrder.add(last);
		} else {
			ColumnOrder last = new ColumnOrder(lastColOrder.
					substring(0, lastColOrder.indexOf(" ")).trim(),
					lastColOrder.substring(lastColOrder.indexOf(" ")).trim()); 
			if (!last.isValidColumnOrder()) {
				return false;
			}
			columnsOrder.add(last);
		}
		parameters.setColumnsOrder(columnsOrder);
		return super.interpret(parts[parts.length - 1].
				substring(parts[parts.length - 1].indexOf(";")).trim());
	}
}
