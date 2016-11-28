package jdbms.sql.data.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import jdbms.sql.errors.ErrorHandler;

public class DTDCreator {
	/**DTD Identifier to be inserted in the DTD file Name.*/
	private static final String DTD_IDENTIFIER = "DTD";
	/**DTD extension to be inserted in the DTD file.*/
	private static final String DTD_EXTENSION = ".dtd";

	public DTDCreator() {
	}

	/**
	 * Creates the DTD file according to the given table identifiers.
	 * @param database name of the database
	 * @param identifier table identifier
	 * @param path  path to the file to be created
	 * @return the DTD file as a string
	 * */
	public String create(String database, ArrayList<String> columnNames,
			String tableName, String path) {
		StringBuilder dtdString = new StringBuilder("");
		dtdString.append("<!ELEMENT ");
		dtdString.append(tableName + " (row)*>");
		dtdString.append("<!ATTLIST " + tableName +
				" " +"xmlns CDATA #FIXED '' ");
		for (String column : columnNames) {
			dtdString.append(column +  " NMTOKEN " + "#REQUIRED ");
		}
		dtdString.append('>');
		dtdString.append('\n');
		dtdString.append("<!ELEMENT ");
		dtdString.append("row (");
		for (int i = 0; i < columnNames.size(); i++) {
			String column = columnNames.get(i);
			dtdString.append(column);
			if (i != columnNames.size() - 1) {
				dtdString.append(",");
			}
		}
		dtdString.append(")>");
		dtdString.append('\n');
		dtdString.append("<!ATTLIST row xmlns CDATA #FIXED ''>");
		dtdString.append('\n');
		for (String column : columnNames) {
			dtdString.append("<!ELEMENT " + column +
					" (#PCDATA)>");
			dtdString.append("<!ATTLIST " + column +
					" xmlns CDATA #FIXED ''>");
			dtdString.append('\n');
		}
		dtdString.append('\n');
		String dtd = dtdString.toString();
		createFile(database, tableName, dtd, path);
		return dtd;
	}

	/**
	 * Creates the file using the previously generated DTD string.
	 * @param database name of the database
	 * @param identifier table identifiers
	 * @param dtd the generated DTD string to be stored
	 * @param path the path where the file would be saved
	 */	
	public void createFile(String database, String tableName
			, String dtd, String path) {
		File dtdFile = new File(path + database
		+ "/" + tableName + DTD_IDENTIFIER + DTD_EXTENSION);
		try {
			FileWriter writer = new FileWriter(dtdFile);
			writer.write(dtd);
			writer.close();
		} catch (IOException e) {
			ErrorHandler.printInternalError();
		}

	}
}
