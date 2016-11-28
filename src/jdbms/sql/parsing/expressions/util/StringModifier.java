package jdbms.sql.parsing.expressions.util;

public class StringModifier {

	public StringModifier() {

	}
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
