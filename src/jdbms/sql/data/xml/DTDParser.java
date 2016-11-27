package jdbms.sql.data.xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import jdbms.sql.data.ColumnIdentifier;
import jdbms.sql.data.TableIdentifier;

public class DTDParser {

	private String tableName;
	private ArrayList<ColumnIdentifier> columns;
	private File file;
	public static void main(String[] args) {
		DTDParser parser = new DTDParser(new File("dtd.txt"));
		parser.parse();
	}

	public DTDParser(File file) {
		 this.file = file;
		 columns = new ArrayList<>();
	}

	public TableIdentifier parse() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			line = reader.readLine();
			String[] components = line.split(" ");
			tableName = components[1];
			line = reader.readLine();
			String columnNames = line.substring(line.indexOf('(') + 1, line.indexOf(')'));
			String[] cols = columnNames.split(",");
			for (String component : cols) {
				columns.add(new ColumnIdentifier(component, "VARCHAR"));
			}
			TableIdentifier identifier = new TableIdentifier(tableName, columns);
			return identifier;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
