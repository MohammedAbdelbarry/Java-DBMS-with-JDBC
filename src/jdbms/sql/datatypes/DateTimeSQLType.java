package jdbms.sql.datatypes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jdbms.sql.datatypes.util.SQLTypeFactory;
import jdbms.sql.exceptions.InvalidDateFormatException;

public class DateTimeSQLType extends SQLType<Date> {
	private static final String DATE_TIME_FORMAT
	= "yyyy-MM-ddHH:mm:ss";
	static {
		SQLTypeFactory.getInstance().
		registerType("DATETIME", DateTimeSQLType.class);
	}
	public DateTimeSQLType(final String value)
			throws InvalidDateFormatException {
		super(new Date());
		if (value == null) {
			super.value = null;
		} else {
			final SimpleDateFormat dateFormat
			= new SimpleDateFormat(DATE_TIME_FORMAT);
			dateFormat.setLenient(false);
			try {
				super.value = dateFormat.parse(
						value.replaceAll("\\s", ""));
			} catch (final ParseException e) {
				throw new InvalidDateFormatException(value);
			}
		}
	}

	@Override
	public String getType() {
		return "DATETIME";
	}
	@Override
	public String getStringValue() {
		if (value == null) {
			return "";
		}
		final SimpleDateFormat formatter
		= new SimpleDateFormat(DATE_TIME_FORMAT);
		return formatter.format(super.value);
	}
}
