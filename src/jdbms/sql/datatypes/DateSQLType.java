package jdbms.sql.datatypes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jdbms.sql.datatypes.util.SQLTypeFactory;
import jdbms.sql.exceptions.InvalidDateFormatException;
import jdbms.sql.parsing.util.Constants;

/**
 * A java object representing the
 * SQL Date type.
 * @author Mohammed Abdelbarry
 */
public class DateSQLType extends SQLType<Date> {
    private static final String DATE_FORMAT
            = "yyyy-MM-dd";

    static {
        SQLTypeFactory.getInstance().
                registerType("DATE", DateSQLType.class);
    }

    public DateSQLType(final String value)
            throws InvalidDateFormatException {
        super(new Date());
        if (value == null
                || value.equals(Constants.NULL_INDICATOR)) {
            super.value = null;
        } else {
            final SimpleDateFormat dateFormat
                    = new SimpleDateFormat(DATE_FORMAT);
            dateFormat.setLenient(false);
            try {
                super.value = dateFormat.parse(
                        value.replaceAll("\\s", "").replaceAll("['\"]", ""));
            } catch (final ParseException e) {
                throw new InvalidDateFormatException(value);
            }
        }
    }

    @Override
    public String getType() {
        return "DATE";
    }

    @Override
    public String toString() {
        if (value == null) {
            return "";
        }
        final SimpleDateFormat formatter
                = new SimpleDateFormat(DATE_FORMAT);
        return "'" + formatter.format(super.value) + "'";
    }
}
