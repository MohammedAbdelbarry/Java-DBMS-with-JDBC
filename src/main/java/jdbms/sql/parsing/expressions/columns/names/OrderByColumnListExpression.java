package jdbms.sql.parsing.expressions.columns.names;

import java.util.ArrayList;

import jdbms.sql.parsing.expressions.terminal.TerminalExpression;
import jdbms.sql.parsing.expressions.util.ColumnOrder;
import jdbms.sql.parsing.properties.InputParametersContainer;

public class OrderByColumnListExpression extends ColumnListExpression {

    private final ArrayList<ColumnOrder> columnsOrder;

    public OrderByColumnListExpression(final InputParametersContainer
                                               parameters) {
        super(new TerminalExpression(parameters), parameters);
        this.columnsOrder = new ArrayList<>();
    }

    @Override
    public boolean interpret(String sqlExpression) {
        sqlExpression = sqlExpression.trim();
        final String[] parts = sqlExpression.split(",");
        for (int i = 0; i < parts.length - 1; i++) {
            parts[i] = parts[i].trim();
            final ColumnOrder currColOrder = new ColumnOrder();
            if (!parts[i].contains(" ")) {
                currColOrder.setColumn(parts[i]);
                currColOrder.setOrder("ASC");
            } else {
                currColOrder.setColumn(parts[i].
                        substring(0, parts[i].indexOf(" ")));
                currColOrder.setOrder(parts[i].substring(parts[i].
                        indexOf(" ")).trim());
            }
            if (!currColOrder.isValidColumnOrder()) {
                return false;
            }
            columnsOrder.add(currColOrder);
        }
        parts[parts.length - 1] = parts[parts.length - 1].trim();
        final String lastColOrder = parts[parts.length - 1].substring(0,
                parts[parts.length - 1].indexOf(";")).trim();
        if (!lastColOrder.contains(" ")) {
            final ColumnOrder last = new ColumnOrder(lastColOrder, "ASC");
            if (!last.isValidColumnOrder()) {
                return false;
            }
            columnsOrder.add(last);
        } else {
            final ColumnOrder last = new ColumnOrder(lastColOrder.
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
