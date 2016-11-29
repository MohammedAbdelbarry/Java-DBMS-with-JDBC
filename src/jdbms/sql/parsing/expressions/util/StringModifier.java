package jdbms.sql.parsing.expressions.util;

/**
 * The string modifier helper class.
 */
public class StringModifier {

	/**
	 * Instantiates a new string modifier.
	 */
	public StringModifier() {

	}
	
	/**
	 * Modify string, replaces the given expression with a modified one.
	 * @param expression the input expression
	 * @return the modified string
	 */
	public String modifyString(String expression) {
		String stringless = "";
		for (int i = 0; i < expression.length(); i++) {
			stringless += expression.charAt(i);
			if (expression.charAt(i) == '\'') {
				for (int j = i + 1; j < expression.length(); j++) {
					if (expression.charAt(j) == '\'') {
						stringless += expression.charAt(j);
						i = j;
						break;
					}
					stringless += "s";
				}
			} else if (stringless.charAt(i) == '"') {
				for (int j = i + 1; j < expression.length(); j++) {
					if (expression.charAt(j) == '"') {
						stringless += expression.charAt(j);
						i = j;
						break;
					}
					stringless += "s";
				}
			}
		}
		return stringless;
	}
}
