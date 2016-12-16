package jdbms.sql.parsing.expressions.table.name;

import jdbms.sql.parsing.expressions.terminal.TerminalExpression;
import jdbms.sql.parsing.properties.InputParametersContainer;

/**
 * The Class Terminating Table Expression.
 */
public class TerminatingTableExpression extends TableNameExpression {

    /**
     * Instantiates a new terminating table expression.
     * @param parameters the input parameters
     */
    public TerminatingTableExpression(
            InputParametersContainer parameters) {
        super(new TerminalExpression(parameters), parameters);
    }
}
