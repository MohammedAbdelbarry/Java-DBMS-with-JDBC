package jdbms.sql.parsing.parser;
 
/**
 * Parses the user's input to a normalized SQL command.
 */
public class Parser {

    public Parser() {

    }

    /**
     * Processing the given command to a normalized form.
     * @return output : string containing the normalized form of the user's
     *         input
     */
    public String normalizeCommand(String SQLCommand) {
        String output = SQLCommand;
        output = output.replaceAll("[(;]", " $0");
        output = output.replaceAll("\\s+", " ");
        output = output.trim();
        output = output.toUpperCase();
        return output;
    }

    /*public static void main(String[] args) {
        Parser parser = new Parser("select * from myTable");
        System.out.println(parser.normalizeCommand());
    }*/
}
