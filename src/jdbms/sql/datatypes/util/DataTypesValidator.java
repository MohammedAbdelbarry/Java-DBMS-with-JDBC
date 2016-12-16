package jdbms.sql.datatypes.util;

import jdbms.sql.parsing.util.Constants;
/**
 * A class that validates SQLTypes
 * and Values.
 * @author Moham
 */
public class DataTypesValidator {
	/**
	 * Constructs a new
	 * Data Types Validator.
	 */
	public DataTypesValidator() {

	}
	/**
	 * Checks if an SQL value matches
	 * The given SQL Data Type or not.
	 * @param dataType The name of the
	 * SQL Data Type.
	 * @param value The value to be
	 * checked.
	 * @return True if the Value is a
	 * valid SQL value of the given
	 * data type, false otherwise.
	 */
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
	/**
	 * Checks whether two given values
	 * are of the same Data types or
	 * not.
	 * @param first The first value
	 * to be checked.
	 * @param second The second value
	 * to be checked.
	 * @return True if the two given
	 * SQL values are of the same
	 * SQL Data Type and false
	 * otherwise.
	 */
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
		return Constants.DOUBLE_TYPES.contains(first)
				&& Constants.DOUBLE_TYPES.contains(second);
	}
	/**
	 * Gets the data type of the given
	 * SQL Value.
	 * @param value The given SQL Value.
	 * @return The data type of the
	 * given SQL value.
	 */
	public String getDataType(final String value) {
		if (value.matches(Constants.DATE_TIME_REGEX))  {
			return "DATETIME";
		} else if (value.matches(Constants.DATE_REGEX)) {
			return "DATE";
		} else if (isDouble(value)) {
			return "DOUBLE";
		} else if (isLong(value)) {
			return "BIGINT";
		} else if (isInteger(value)) {
			return "INTEGER";
		} else if (isFloat(value)){
			return "FLOAT";
		} else if (isString(value)) {
			return "VARCHAR";
		} else {
			return null;
		}
	}
	public boolean assertDataTypesEquals(final String firstValue,
			final String secondValue) {
		return checkDataTypes(getDataType(firstValue),
				getDataType(secondValue));
	}
	/**
	 * Checks if a value is a constant
	 * value, or an invalid SQL value.
	 * @param value The SQL value to
	 * be checked.
	 * @return true if the value is a valid
	 * SQL value, and false otherwise
	 */
	public boolean isConstant(final String value) {
		return getDataType(value) != null;
	}
	/**
	 * Checks whether a data type is supported
	 * or not.
	 * @param dataType The data type to be
	 * checked
	 * @return true if this data type is
	 * supported and false otherwise
	 */
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
