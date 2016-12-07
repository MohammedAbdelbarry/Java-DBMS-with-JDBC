package jdbms.sql.parsing.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class Constants {
	public static final List<String> RESERVED_KEYWORDS =
			Collections.unmodifiableList(Arrays.asList("SELECT",
					"TABLE", "DELETE", "UPDATE", "INSERT", "INTO", "VALUES",
					"SET", "WHERE", "FROM", "CREATE", "DROP", "DATABASE",
					"INTEGER", "VARCHAR", "TEXT", "INT", "USE", "DISTINCT",
					"TRUE", "FALSE", "ALTER", "ADD", "ORDER", "BY",
					"ASC", "DESC", "FLOAT", "REAL"));

	public static final String COLUMN_REGEX = "^[a-zA-Z_][a-zA-Z0-9_\\$]*$";
	public static final String STRING_REGEX = "^'.*?'$";
	public static final String DOUBLE_STRING_REGEX = "^\".*?\"$";
	public static final String INT_REGEX = "^[+-]?\\d+$";
	public static final String FLOAT_REGEX = "^[+-]?\\d*[.]\\d+$";
	public static final String DATE_REGEX
	= "^[0-9]{4}\\s*-\\s*(?:[0][1-9]|[1][0-2])"//yyyy-MM
	+ "\\s*-\\s*(?:[0][1-9]|[1-2][0-9]|[3][0-1])$";//dd
	public static final String DATE_TIME_REGEX
	= "^[0-9]{4}\\s*-\\s*(?:[0][1-9]|[1][0-2])"//yyyy-MM
	+ "\\s*-\\s*(?:[0][1-9]|[1-2][0-9]|[3][0-1])"//dd
	+ "\\s+(?:[0-1][0-9]|[2][0-3])"//hh
	+ "\\s*:\\s*[0-5][0-9]\\s*:\\s*[0-5][0-9]$";//mm:ss
	public static final List<String> STRING_TYPES =
			Collections.unmodifiableList(Arrays.asList("TEXT",
					"VARCHAR"));
	public static final List<String> INTEGER_TYPES =
			Collections.unmodifiableList(Arrays.asList("INTEGER",
					"INT"));
	public static final List<String> FLOAT_TYPES =
			Collections.unmodifiableList(Arrays.asList("FLOAT",
					"REAL"));
	public static final List<String> DATE_TYPES =
			Collections.unmodifiableList(Arrays.asList(
					"DATE"));
	public static final List<String> DATE_TIME_TYPES =
			Collections.unmodifiableList(Arrays.asList(
					"DATETIME"));
}

