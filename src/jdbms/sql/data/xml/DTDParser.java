package jdbms.sql.data.xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import jdbms.sql.data.ColumnIdentifier;
import jdbms.sql.data.TableIdentifier;

public class DTDParser {
	private static final String STRING_TYPE = "VARCHAR";

	public static void main(String[] args) {
//		DTDParser parser = new DTDParser(new File("dtd.txt"));
//		parser.parse();
	}

	public DTDParser() {

	}

	public ArrayList<String> parse(File file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			line = reader.readLine();
			String[] components = line.split(" ");
			String tableName = components[1];
			line = reader.readLine();
			String columnNames = line.substring(line.indexOf('(') + 1, line.indexOf(')'));
			String[] cols = columnNames.split(",");
			ArrayList<String> columns = new  ArrayList<>();
			for (String component : cols) {
				columns.add((component));
			}
			reader.close();
			return columns;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
