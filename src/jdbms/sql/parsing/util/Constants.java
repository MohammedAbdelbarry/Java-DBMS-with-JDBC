package jdbms.sql.parsing.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

abstract class Constants {
	public static final List<String> RESERVED_KEYWORDS = 
		    Collections.unmodifiableList(Arrays.asList("SELECT", "CREATE TABLE",
		    		"CREATE DATABASE", "DROP DATABASE", "DELETE", "UPDATE", "INSERT INTO",
		    		"SET", "WHERE", "FROM", "CREATE", "DROP", "INSERT", "DATABASE"));
}
