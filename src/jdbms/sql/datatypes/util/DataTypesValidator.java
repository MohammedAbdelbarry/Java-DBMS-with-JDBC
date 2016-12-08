package jdbms.sql.datatypes.util;

import jdbms.sql.parsing.util.Constants;

public class DataTypesValidator {

	public DataTypesValidator() {

	}
	public boolean match(final String dataType, final String value) {
		if (Constants.STRING_TYPES.contains(dataType)) {
			return value.matches(Constants.STRING_REGEX)
					|| value.matches(Constants.DOUBLE_STRING_REGEX);
		} else if (Constants.INTEGER_TYPES.contains(dataType)) {
			return value.matches(Constants.INT_REGEX);
		} else if (Constants.FLOAT_TYPES.contains(dataType)) {
			return value.matches(Constants.FLOAT_REGEX);
		} else if (Constants.DATE_TYPES.contains(dataType)) {
			return value.matches(Constants.DATE_REGEX);
		} else if (Constants.DATE_TIME_TYPES.contains(dataType)) {
			return value.matches(Constants.DATE_TIME_REGEX);
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
		return false;
	}
	public String getDataType(final String value) {
		if (value.matches(Constants.STRING_REGEX)
				|| value.matches(Constants.DOUBLE_STRING_REGEX))  {
			return "VARCHAR";
		} else if (value.matches(Constants.INT_REGEX)) {
			return "INTEGER";
		} else if (value.matches(Constants.FLOAT_REGEX)){
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
}
