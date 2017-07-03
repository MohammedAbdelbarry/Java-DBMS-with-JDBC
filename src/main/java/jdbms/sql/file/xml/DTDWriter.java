package jdbms.sql.file.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import jdbms.sql.errors.ErrorHandler;

public class DTDWriter {
    /**
     * DTD Identifier to be inserted in the DTD file Name.
     */
    private static final String DTD_IDENTIFIER = "DTD";
    /**
     * DTD extension to be inserted in the DTD file.
     */
    private static final String DTD_EXTENSION = ".dtd";

    public DTDWriter() {
    }

    /**
     * Creates the DTD file according to the given table identifiers.
     * @param database    name of the database
     * @param columnNames the names of the columns
     * @param tableName   the name of the table
     * @param path        path to the file to be created
     * @return the DTD file as a string
     */
    public String create(final String database, final ArrayList<String>
            columnNames,
                         final String tableName, final String path) {
        final StringBuilder dtdString = new StringBuilder("");
        dtdString.append("<!ELEMENT ");
        dtdString.append(tableName).append(" (row)*>");
        dtdString.append("<!ATTLIST ").append(tableName).append(" ").append
				("xmlns CDATA #FIXED '' ");
        for (final String column : columnNames) {
            dtdString.append(column).append(" NMTOKEN ").append("#REQUIRED ");
        }
        dtdString.append('>');
        dtdString.append('\n');
        dtdString.append("<!ELEMENT ");
        dtdString.append("row (");
        for (int i = 0; i < columnNames.size(); i++) {
            final String column = columnNames.get(i);
            dtdString.append(column);
            if (i != columnNames.size() - 1) {
                dtdString.append(",");
            }
        }
        dtdString.append(")>");
        dtdString.append('\n');
        dtdString.append("<!ATTLIST row xmlns CDATA #FIXED ''>");
        dtdString.append('\n');
        for (final String column : columnNames) {
            dtdString.append("<!ELEMENT ").append(column).append(" (#PCDATA)>");
            dtdString.append("<!ATTLIST ").append(column).append(" xmlns "
					+ "CDATA #FIXED ''>");
            dtdString.append('\n');
        }
        dtdString.append('\n');
        final String dtd = dtdString.toString();
        createFile(database, tableName, dtd, path);
        return dtd;
    }

    /**
     * Creates the file using the previously generated DTD string.
     * @param database  name of the database
     * @param tableName the name of the table
     * @param dtd       the generated DTD string to be stored
     * @param path      the path where the file would be saved
     */
    public void createFile(final String database, final String tableName
            , final String dtd, final String path) {
        final File dtdFile = new File(path + database
                + "/" + tableName.toLowerCase() + DTD_IDENTIFIER + DTD_EXTENSION);
        try {
            final FileWriter writer = new FileWriter(dtdFile);
            writer.write(dtd);
            writer.close();
        } catch (final IOException e) {
            ErrorHandler.printInternalError();
        }

    }
}
