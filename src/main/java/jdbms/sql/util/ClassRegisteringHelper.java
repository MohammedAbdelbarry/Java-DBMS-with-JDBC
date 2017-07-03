package jdbms.sql.util;

/**
 * The class resposible for registering classes
 * in the factory.
 */
public class ClassRegisteringHelper {

    /**
     * Register initial statements in the
     * statements factory.
     */
    public static void registerInitialStatements() {
        try {
            Class.forName("jdbms.sql.parsing.statements" +
                    ".CreateDatabaseStatement");
            Class.forName("jdbms.sql.parsing.statements.CreateTableStatement");
            Class.forName("jdbms.sql.parsing.statements.DropDatabaseStatement");
            Class.forName("jdbms.sql.parsing.statements.DropTableStatement");
            Class.forName("jdbms.sql.parsing.statements.InsertIntoStatement");
            Class.forName("jdbms.sql.parsing.statements.DeleteStatement");
            Class.forName("jdbms.sql.parsing.statements.SelectStatement");
            Class.forName("jdbms.sql.parsing.statements.UpdateStatement");
            Class.forName("jdbms.sql.parsing.statements.UseStatement");
            Class.forName("jdbms.sql.parsing.statements"
                    + ".AlterTableAddStatement");
            Class.forName("jdbms.sql.parsing.statements"
                    + ".AlterTableDropStatement");
            Class.forName("jdbms.sql.parsing.expressions.math"
                    + ".EqualsExpression");
            Class.forName("jdbms.sql.parsing.expressions.math"
                    + ".LargerThanEqualsExpression");
            Class.forName("jdbms.sql.parsing.expressions.math"
                    + ".LessThanEqualsExpression");
            Class.forName("jdbms.sql.parsing.expressions.math"
                    + ".LargerThanExpression");
            Class.forName("jdbms.sql.parsing.expressions.math"
                    + ".LessThanExpression");
            Class.forName("jdbms.sql.parsing.expressions.math"
                    + ".NotEqualsExpression");
            Class.forName("jdbms.sql.datatypes.IntSQLType");
            Class.forName("jdbms.sql.datatypes.VarcharSQLType");
            Class.forName("jdbms.sql.datatypes.FloatSQLType");
            Class.forName("jdbms.sql.datatypes.DateSQLType");
            Class.forName("jdbms.sql.datatypes.DateTimeSQLType");
            Class.forName("jdbms.sql.datatypes.DoubleSQLType");
            Class.forName("jdbms.sql.datatypes.BigIntSQLType");
        } catch (final ClassNotFoundException e) {

        }
    }
}
