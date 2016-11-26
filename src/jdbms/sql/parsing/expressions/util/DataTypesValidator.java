package jdbms.sql.parsing.expressions.util;

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
}
