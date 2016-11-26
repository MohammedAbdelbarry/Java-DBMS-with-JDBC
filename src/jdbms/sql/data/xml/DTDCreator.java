package jdbms.sql.data.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.print.DocFlavor.STRING;

import jdbms.sql.data.ColumnIdentifier;
import jdbms.sql.data.Database;
import jdbms.sql.data.TableIdentifier;
import jdbms.sql.parsing.statements.CreateDatabaseStatement;

public class DTDCreator {

	private TableIdentifier identifier;
	private Database database;
	
	public static void main(String[] args) {
		ArrayList<ColumnIdentifier> columns = new ArrayList<>();
		columns.add(new ColumnIdentifier("Grade", "INTEGER"));
		columns.add(new ColumnIdentifier("Name", "VARCHAR"));
		Database db = new Database("MyData");
		TableIdentifier identifier = new TableIdentifier("Students", columns);
		DTDCreator creator = new DTDCreator(db, identifier);
		System.out.println(creator.create());
	}

	public DTDCreator(Database database,TableIdentifier identifier) {
		this.identifier = identifier;
		this.database = database;
	}

	public String create() {
		StringBuilder dtdString = new StringBuilder("");
		dtdString.append("<!ELEMENT ");
		dtdString.append(identifier.getTableName() + " (row)>");
		dtdString.append('\n');
		ArrayList<String> columns = identifier.getColumnNames();
		dtdString.append("<!ELEMENT ");
		dtdString.append("row (");
		for (int i = 0; i < columns.size(); i++) {
			String column = columns.get(i);
			dtdString.append(column);
			if (i != columns.size() - 1) {
				dtdString.append(",");
			}
		}
		dtdString.append(")>");
		dtdString.append('\n');
		for (String column : columns) {
			dtdString.append("<!ELEMENT " + column + " (#PCDATA)>");
			dtdString.append('\n');
		}
		String dtd = dtdString.toString();
		createFile(dtd);
		return dtd;
	}

	public void createFile(String dtd) {		
		File dtdFile = new File(System.getProperty("user.dir") + "/" +database.getDatabaseName()
		+ "/" + identifier.getTableName() + ".dtd");
		try {
			FileWriter writer = new FileWriter(dtdFile);
			writer.write(dtd);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
