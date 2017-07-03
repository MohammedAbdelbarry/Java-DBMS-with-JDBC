package jdbms.sql.parsing.properties;

import java.util.ArrayList;

import jdbms.sql.parsing.expressions.math.AssignmentExpression;
import jdbms.sql.parsing.expressions.math.BooleanExpression;

/**
 * The Class UpdatingParameters.
 */
public class UpdatingParameters {

    /** The condition. */
    private BooleanExpression condition;

    /** The assignment list. */
    private ArrayList<AssignmentExpression> assignmentList;

    /** The table name. */
    private String tableName;

    /**
     * Instantiates a new updating parameters.
     */
    public UpdatingParameters() {

    }

    /**
     * Gets the condition.
     * @return the condition
     */
    public BooleanExpression getCondition() {
        return condition;
    }

    /**
     * Sets the condition.
     * @param condition the new condition
     */
    public void setCondition(final BooleanExpression condition) {
        this.condition = condition;
    }

    /**
     * Gets the assignment list.
     * @return the assignment list
     */
    public ArrayList<AssignmentExpression> getAssignmentList() {
        return assignmentList;
    }

    /**
     * Sets the assignment list.
     * @param assignmentList the new assignment list
     */
    public void setAssignmentList(final ArrayList<AssignmentExpression>
                                          assignmentList) {
        this.assignmentList = assignmentList;
    }

    /**
     * Gets the table name.
     * @return the table name
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * Sets the table name.
     * @param table the new table name
     */
    public void setTableName(final String table) {
        this.tableName = table;
    }

}
