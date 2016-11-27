package jdbms.sql.datatypes.util;

import jdbms.sql.parsing.util.Constants;

public class DataTypesValidator {

	public DataTypesValidator() {

	}
	public boolean match(String dataType, String value) {
		if (Constants.STRING_TYPES.contains(dataType)) {
			return value.matches(Constants.STRING_REGEX)
					|| value.matches(Constants.DOUBLE_STRING_REGEX);
		} else if (Constants.INTEGER_TYPES.contains(dataType)) {
			return value.matches(Constants.INT_REGEX);
		} else {
			return false;
		}
	}
	public boolean checkDataTypes(String first, String second) {
		if (Constants.STRING_TYPES.contains(first)
				&& Constants.STRING_TYPES.contains(second)) {
			return true;
		}
		if (Constants.INTEGER_TYPES.contains(first)
				&& Constants.INTEGER_TYPES.contains(second)) {
			return true;
		}
		return false;
	}
	public String getDataType(String value) {
		if (value.matches(Constants.STRING_REGEX)
					|| value.matches(Constants.DOUBLE_STRING_REGEX))  {
			return "VARCHAR";
		} else if (value.matches(Constants.INT_REGEX)) {
			return "INTEGER";
		} else {
			return null;
		}
	}
}
