package jdbms.sql.parsing.expressions.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class DataTypesConstants {
	public static final List<String> DATA_TPYES = 
		    Collections.unmodifiableList(Arrays.asList("INT",
		    		"VARCHAR", "TEXT"));
}
