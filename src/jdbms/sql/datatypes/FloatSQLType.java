package jdbms.sql.datatypes;

import jdbms.sql.datatypes.util.SQLTypeFactory;

/**
 * A java object representing the
 * SQL Float type.
 * @author Mohammed Abdelbarry
 */
public class FloatSQLType extends SQLType<Float> {
    static {
        SQLTypeFactory.getInstance().
                registerType("FLOAT", FloatSQLType.class);
    }

    public FloatSQLType(final String value) {
        super(value == null ? null
                : Float.parseFloat(value));
    }

    @Override
    public String getType() {
        return "FLOAT";
    }
}
