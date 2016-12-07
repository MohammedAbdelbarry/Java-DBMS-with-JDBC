package jdbms.sql.datatypes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jdbms.sql.datatypes.util.SQLTypeFactory;
import jdbms.sql.exceptions.InvalidDateFormatException;

public class DateSQLType extends SQLType<Date> {
	static {
		SQLTypeFactory.getInstance().
		registerType("DATE", DateSQLType.class);
	}
	public DateSQLType(final String value)
			throws InvalidDateFormatException {
		super(new Date());
		final SimpleDateFormat dateFormat
		= new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		try {
			super.value = dateFormat.parse(
					value.replaceAll("\\s", ""));
		} catch (final ParseException e) {
			throw new InvalidDateFormatException(value);
		}
	}

	@Override
	public String getType() {
		return "DATE";
	}

}
