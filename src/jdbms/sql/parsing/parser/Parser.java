package jdbms.sql.parsing.parser;

import java.util.ArrayList;
import java.util.Locale;
import jdbms.sql.parsing.util.Constants;
/**
 * Parses the user's input to the conventional SQL command.
 * */
public class Parser {
	/**Array of components of the user's input.*/
	private ArrayList<String> components;
	/**Array of the registered key words in the system.*/
	private ArrayList<String> registeredKeywords;

	public Parser() {
		components = new ArrayList<String>();
		registeredKeywords = new ArrayList<String>();
		fillRegisteredKeywords();
	}

	/**.*/
	public static void main(String[] args) {
		Parser parser = new Parser();
		String parsed = parser.parse("create TABLE Persons(PersonID int,"
				+ "LastName varchar(255),FirstName varchar(255),"
				+ "Address varchar(255),"
				+ "City varchar(255));");
		System.out.println(parsed);
	}

	/**
	 * Initializing the registerdKeywords Array.
	 * */
	private void fillRegisteredKeywords() {
		registeredKeywords.addAll(Constants.RESERVED_KEYWORDS);
	}

	/**
	 * fills array of components.
	 * @param command : user's input to be processed
	 * */
	private void splitCommand(String command) {
		StringBuilder current = new StringBuilder("");
		for (int i = 0; i < command.length(); i++) {
			char curChar = command.charAt(i);
			if (curChar != ' ') {
				current.append(curChar);
				if (i == command.length() - 1) {
					String temp = current.toString();
					if (temp.length() > 0) {
						components.add(temp);
					}
				}
			} else {
				String temp = current.toString();
				if (temp.length() > 0) {
					components.add(temp);
					current = new StringBuilder("");
				}
			}
		}

	}

	/**
	 * handles the presence of semicolon
	 * at the end of the last word in the command.
	 *  */
	private void checkSemiColon() {
		int lastIndex = components.size() - 1;
		String last = components.get(lastIndex);
		int lastStringIndex = last.length() - 1;
		if (last.charAt(last.length() - 1) == ';' && last.length() > 1) {
			components.remove(lastIndex);
			String modified = last.substring(0, lastStringIndex);
			components.add(modified);
			components.add(" ;");
		} else if (last.charAt(last.length() - 1) == ';') {
			components.remove(lastIndex);
			components.add(" ;");
		}
	}


	/**
	 * checks whether the given string is a registered key word.
	 * @return whether the given string is a registered key word
	 * */
	private boolean isKeyWord(String check) {
		return registeredKeywords.contains(check.toUpperCase());
	}

	/**
	 * convert key words' characters to upper case characters.
	 * */

	private void toUpperKeyWords() {
		for (int i = 0; i < components.size(); i++) {
			String current = components.get(i);
			String check;
			check = current.toUpperCase(Locale.ENGLISH);
			if (isKeyWord(check)) {
				components.remove(i);
				components.add(i, check);

			}
		}
	}

	/**returns the command after being parsed.
	 * @param command : command to be processed
	 * @return parsedString : the new parsed command
	 * */
	private String generateParsedCommand(String command) {
		boolean keyword = true;
		StringBuilder parsedString = new StringBuilder("");
		for (int i = 0; i < components.size(); i++) {
			String current = components.get(i);
			if (isKeyWord(current) && !keyword) {
				parsedString.append(" ");
			}
			parsedString.append(current.toUpperCase());
			if (isKeyWord(current) &&  i != components.size() - 1) {
				parsedString.append(" ");
				keyword = true;
			} else  {
				keyword = false;
			}
		}
		return parsedString.toString();
	}

	/**
	 * Derives the parsing process and returns the parsed command.
	 * @param command : the command to be processed
	 * @return the parsed command
	 * */
	public String parse(String command) {
		toUpperKeyWords();
		splitCommand(command);
		checkSemiColon();
		return generateParsedCommand(command);
	}
}
