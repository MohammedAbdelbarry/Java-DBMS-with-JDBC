package jdbms.sql.parsing.parser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jdbms.sql.parsing.util.Constants;

/**
 * Parses the user's input to a normalized SQL command.
 */
public class Parser {

	/**
	 * Required components of the SQL command.
	 */
	private ArrayList<String> components;
	/**
	 * Regex for reducing multiple spaces except for strings of double quotes.
	 */
	private String spaceRegexD;
	/**
	 * Regex for reducing multiple spaces except for strings of single quotes.
	 */
	private String spaceRegexS;
	/**
	 * The final regex for reducing multiple spaces except for strings of single
	 * or double quotes.
	 */
	private String spaceRegex;

	public Parser() {
		components = new ArrayList<>();
		spaceRegexD = "\\s+(?=(([\"]|[^\"])*\"([\"]|[^\"])*\")*([\"]|[^\"])*$)";
		spaceRegexS = "\\s+(?=(([\']|[^\'])*\'([\']|[^\'])*\')*([\']|[^\'])*$)";
		spaceRegex = spaceRegexD + spaceRegexS;
	}

	/**
	 * Processing the given command to a normalized form.
	 * @return output : string containing the normalized form of the user's
	 *         input
	 */
	public String normalizeCommand(String SQLCommand) {
		String output = SQLCommand;

		// Replace multiple space with only one space EXCEPT for quoted strings
		output = output.replaceAll(spaceRegex, " ");
		output = output.trim();

		// Preparing the output
		output = initialSpacing(output);
		split(output);
		capitalize();
		output = merge();
		components.clear();
		return output;
	}

	/**
	 * Inserting white spaces in special places in the user's command in order
	 * to recognize all the required data.
	 * @param SQLCommand
	 *            : user's command
	 * @return output : The string after inserting the required spaces
	 */
	public String initialSpacing(String SQLCommand) {
		String command = SQLCommand;
		ArrayList<String> components = new ArrayList<>();
		StringBuilder component = new StringBuilder();

		for (int i = 0; i < command.length(); i++) {
			while ((i < command.length()) && !(command.charAt(i)
					== '"') && !(command.charAt(i) == '\'')) {
				component.append(command.charAt(i));
				i++;
			}
			// component is ready
			String temp = component.toString();

			temp = temp.replaceAll("[(;,)]", " $0");
			temp = temp.replaceAll("- ", "-");
			components.add(temp);
			component.setLength(0);

			if (i < command.length()) {
				component.append(command.charAt(i));
				if (command.charAt(i) == '"') {
					i++;
					while (i < command.length() && !(
							command.charAt(i) == '"')) {
						component.append(command.charAt(i));
						i++;
					}
					if (i < command.length())
					component.append(command.charAt(i));
				} else if (command.charAt(i) == '\'') {
					i++;
					while (i < command.length() && !(
							command.charAt(i) == '\'')) {
						component.append(command.charAt(i));
						i++;
					}
					if (i < command.length())
					component.append(command.charAt(i));
				}
				// Another component is ready
				components.add(component.toString());
				component.setLength(0);
			}

		}

		StringBuilder output = new StringBuilder();
		for (String x : components)
			output.append(x);

		return output.toString();
	}

	// HELPER METHODS

	/**
	 * Splitting the user's command into several components to be processed.
	 * @param SQLCommand user's command
	 */
	public void split(String SQLCommand) {
		// Splitting by spaces outside the quotes
		Pattern regex = Pattern.compile("[^\\s\"']+|\"[^\"]*\"|'[^']*'");
		Matcher regexMatcher = regex.matcher(SQLCommand);
		while (regexMatcher.find()) {
			components.add(regexMatcher.group().trim());
		}
	}

	/**
	 * Capitalizing the reserved key words in the command
	 */
	public void capitalize() {
		for (int i = 0; i < components.size(); i++) {
			String current = components.get(i).toUpperCase();
			if (Constants.RESERVED_KEYWORDS.contains(current)) {
				components.set(i, current.toUpperCase());
			}
		}
	}

	/**
	 * Merging the components of the command to the final form.
	 * @return normalizedString : string of the normalized form
	 */
	public String merge() {
		StringBuilder output = new StringBuilder();
		for (String component : components) {
			output.append(component);
			output.append(" ");
		}
		String normalizedString = output.toString();
		return normalizedString;
	}

}