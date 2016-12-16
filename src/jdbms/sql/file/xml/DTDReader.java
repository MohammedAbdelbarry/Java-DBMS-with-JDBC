package jdbms.sql.file.xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import jdbms.sql.errors.ErrorHandler;

public class DTDReader {

    public DTDReader() {

    }

    /**
     * Parses the DTD file given and returns array of column names.
     * @param file the DTD file to be parsed*
     * @return array of column names as extracted from the DTD
     */
    public ArrayList<String> parse(final File file) {
        try {
            final BufferedReader reader
                    = new BufferedReader(new FileReader(file));
            String line;
            line = reader.readLine();
            line = reader.readLine();
            final String columnNames = line.
                    substring(line.
                            indexOf('(') + 1, line.indexOf(')'));
            final String[] cols = columnNames.split(",");
            final ArrayList<String> columns
                    = new ArrayList<>();
            Collections.addAll(columns,
                    cols);
            reader.close();
            return columns;
        } catch (final IOException e) {
            ErrorHandler.printInternalError();
            return null;
        }
    }
}
