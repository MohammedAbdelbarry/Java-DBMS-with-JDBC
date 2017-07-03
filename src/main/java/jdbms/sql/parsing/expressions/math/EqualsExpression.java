package jdbms.sql.parsing.expressions.math;

import jdbms.sql.datatypes.BigIntSQLType;
import jdbms.sql.datatypes.DateSQLType;
import jdbms.sql.datatypes.DateTimeSQLType;
import jdbms.sql.datatypes.DoubleSQLType;
import jdbms.sql.datatypes.VarcharSQLType;
import jdbms.sql.parsing.expressions.math.util.BooleanExpressionFactory;
import jdbms.sql.parsing.properties.InputParametersContainer;

/**
 * The equals boolean expression class.
 */
public class EqualsExpression extends BooleanExpression {

    private static final String SYMBOL = "=";

    static {
        BooleanExpressionFactory.getInstance().
                registerBoolExpression(SYMBOL, EqualsExpression.class);
    }

    /**
     * Instantiates a new equals expression.
     * @param parameters the input parameters
     */
    public EqualsExpression(
            final InputParametersContainer parameters) {
        super(SYMBOL, parameters);
    }

    @Override
    public boolean evaluate(final VarcharSQLType left,
                            final VarcharSQLType right) {
        return left.compareTo(right) == 0;
    }

    @Override
    public boolean evaluate(final BigIntSQLType left,
                            final BigIntSQLType right) {
        return left.compareTo(right) == 0;
    }

    @Override
    public boolean evaluate(final DoubleSQLType left,
                            final DoubleSQLType right) {
        return left.compareTo(right) == 0;
    }

    @Override
    public boolean evaluate(final DateSQLType left,
                            final DateSQLType right) {
        return left.compareTo(right) == 0;
    }

    @Override
    public boolean evaluate(final DateTimeSQLType left,
                            final DateTimeSQLType right) {
        return left.compareTo(right) == 0;
    }
}
