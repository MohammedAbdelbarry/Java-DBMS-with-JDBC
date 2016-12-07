package jdbms.sql.datatypes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jdbms.sql.datatypes.util.SQLTypeFactory;
import jdbms.sql.exceptions.InvalidDateFormatException;

public class DateTimeSQLType extends SQLType<Date> {
	static {
		SQLTypeFactory.getInstance().
		registerType("DATETIME", DateTimeSQLType.class);
	}
	public DateTimeSQLType(final String value)
			throws InvalidDateFormatException {
		super(new Date());
		final SimpleDateFormat dateFormat
		= new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
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
		return "DATETIME";
	}
}
