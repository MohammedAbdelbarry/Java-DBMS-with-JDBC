package jdbms.sql.parsing.parser;

import java.io.IOException;
import java.util.Scanner;

import jdbms.sql.data.SQLData;
import jdbms.sql.errors.ErrorHandler;
import jdbms.sql.parsing.expressions.util.StringModifier;
import jdbms.sql.util.ClassRegisteringHelper;

public class ParserMain {
    private static final String QUIT = "QUIT;";

    public ParserMain() {

    }

    public static void main(final String[] args) throws IOException {
        ClassRegisteringHelper.registerInitialStatements();
        final SQLData data = new SQLData();
        final StringNormalizer normalizer = new StringNormalizer();
        final Scanner in = new Scanner(System.in);
        final Parser parser = new Parser();
        while (true) {
            final StringBuilder stringBuilder = new StringBuilder();
            String sql = null;
            boolean invalid = false;
            final StringModifier modifier = new StringModifier();
            while (in.hasNextLine()) {
                stringBuilder.append(in.nextLine());
                String modifiedExpression = null;
                try {
                    modifiedExpression = modifier.
                            modifyString(stringBuilder.toString()).trim();
                    if (modifiedExpression.contains(";")) {
                        sql = stringBuilder.substring(0,
                                modifiedExpression.indexOf(";") + 1);
                        break;
                    }
                } catch (final IndexOutOfBoundsException e) {
                    invalid = true;
                    break;
                }
            }
            if (invalid) {
                ErrorHandler.printSyntaxError();
                continue;
            }
            if (sql.trim().equalsIgnoreCase(QUIT)) {
                in.close();
                break;
            }
            String normalizedOutput;
            try {
                normalizedOutput = normalizer.normalizeCommand(sql);
            } catch (final IndexOutOfBoundsException e) {
                ErrorHandler.printSyntaxError();
                continue;
            }
            parser.parse(normalizedOutput, data);
        }
    }
}
