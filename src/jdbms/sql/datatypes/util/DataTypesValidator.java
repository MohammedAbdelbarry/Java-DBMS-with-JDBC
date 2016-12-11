package jdbms.sql.datatypes.util;

import jdbms.sql.parsing.util.Constants;

public class DataTypesValidator {

	public DataTypesValidator() {

	}
	public boolean match(final String dataType, final String value) {
		if (Constants.STRING_TYPES.contains(dataType)) {
			return isString(value);
		} else if (Constants.INTEGER_TYPES.contains(dataType)) {
			return isInteger(value);
		} else if (Constants.FLOAT_TYPES.contains(dataType)) {
			return isFloat(value);
		} else if (Constants.DATE_TYPES.contains(dataType)) {
			return value.matches(Constants.DATE_REGEX);
		} else if (Constants.DATE_TIME_TYPES.contains(dataType)) {
			return value.matches(Constants.DATE_TIME_REGEX);
		} else if (Constants.BIG_INTEGER_TYPES.contains(dataType)) {
			return isLong(value);
		} else if (Constants.DOUBLE_TYPES.contains(dataType)) {
			return isDouble(value);
		} else {
			return false;
		}
	}
	public boolean checkDataTypes(final String first, final String second) {
		if (Constants.STRING_TYPES.contains(first)
				&& Constants.STRING_TYPES.contains(second)) {
			return true;
		}
		if (Constants.INTEGER_TYPES.contains(first)
				&& Constants.INTEGER_TYPES.contains(second)) {
			return true;
		}
		if (Constants.FLOAT_TYPES.contains(first)
				&& Constants.FLOAT_TYPES.contains(second)) {
			return true;
		}
		if (Constants.DATE_TYPES.contains(first)
				&& Constants.DATE_TYPES.contains(second)) {
			return true;
		}
		if (Constants.DATE_TIME_TYPES.contains(first)
				&& Constants.DATE_TIME_TYPES.contains(second)) {
			return true;
		}
		if (Constants.BIG_INTEGER_TYPES.contains(first)
				&& Constants.BIG_INTEGER_TYPES.contains(second)) {
			return true;
		}
		if (Constants.DOUBLE_TYPES.contains(first)
				&& Constants.DOUBLE_TYPES.contains(second)) {
			return true;
		}
		return false;
	}
	public String getDataType(final String value) {
		if (isString(value))  {
			return "VARCHAR";
		} else if (isLong(value)) {
			return "BIGINT";
		} else if (isDouble(value)) {
			return "DOUBLE";
		} else if (isInteger(value)) {
			return "INTEGER";
		} else if (isFloat(value)){
			return "FLOAT";
		} else if (value.matches(Constants.DATE_REGEX)) {
			return "DATE";
		} else if (value.matches(Constants.DATE_TIME_REGEX)) {
			return "DATETIME";
		} else {
			return null;
		}
	}
	public boolean assertDataTypesEquals(final String firstValue,
			final String secondValue) {
		return checkDataTypes(getDataType(firstValue),
				getDataType(secondValue));
	}
	public boolean isConstant(final String value) {
		return getDataType(value) != null;
	}

	public boolean isSupportedDataType(final String dataType) {
		return Constants.SUPPORTED_DATA_TYPES.
				contains(dataType.trim().toUpperCase());
	}
	private boolean isFloat(final String value) {
		try {
			Float.parseFloat(value);
			return true;
		} catch (final NumberFormatException ex) {
			return false;
		}
	}
	private boolean isDouble(final String value) {
		try {
			Double.parseDouble(value);
			return true;
		} catch (final NumberFormatException ex) {
			return false;
		}
	}
	private boolean isInteger(final String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (final NumberFormatException ex) {
			return false;
		}
	}
	private boolean isLong(final String value) {
		try {
			Long.parseLong(value);
			return true;
		} catch (final NumberFormatException ex) {
			return false;
		}
	}
	private boolean isString(final String value) {
		return value.matches(Constants.STRING_REGEX)
				|| value.matches(Constants.DOUBLE_STRING_REGEX);
	}
}
