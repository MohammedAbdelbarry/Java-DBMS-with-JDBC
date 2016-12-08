//package jdbms.sql.data.xml.testing;
//
//import static org.junit.Assert.*;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.ArrayList;
//
//
//import jdbms.sql.parsing.parser.Parser;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import jdbms.sql.data.ColumnIdentifier;
//import jdbms.sql.data.SQLData;
//import jdbms.sql.data.Table;
//import jdbms.sql.data.xml.XMLCreator;
//import jdbms.sql.parsing.properties.InsertionParameters;
//import jdbms.sql.parsing.properties.TableCreationParameters;
//import jdbms.sql.parsing.statements.InitialStatement;
//import jdbms.sql.parsing.statements.util.InitialStatementFactory;
//
//public class XMLCreationTesting {
//
//	private XMLCreator creator;
//	private Parser parser;
//	private SQLData data;
//	@Before
//	public void setUp() throws Exception {
//		try {
//			Class.forName("jdbms.sql.parsing.statements.CreateDatabaseStatement");
//			Class.forName("jdbms.sql.parsing.statements.CreateTableStatement");
//			Class.forName("jdbms.sql.parsing.statements.DropDatabaseStatement");
//			Class.forName("jdbms.sql.parsing.statements.DropTableStatement");
//			Class.forName("jdbms.sql.parsing.statements.InsertIntoStatement");
//			Class.forName("jdbms.sql.parsing.statements.DeleteStatement");
//			Class.forName("jdbms.sql.parsing.statements.SelectStatement");
//			Class.forName("jdbms.sql.parsing.statements.UpdateStatement");
//			Class.forName("jdbms.sql.parsing.statements.UseStatement");
//			Class.forName("jdbms.sql.parsing.expressions.math.EqualsExpression");
//			Class.forName("jdbms.sql.parsing.expressions.math.LargerThanEqualsExpression");
//			Class.forName("jdbms.sql.parsing.expressions.math.LessThanEqualsExpression");
//			Class.forName("jdbms.sql.parsing.expressions.math.LargerThanExpression");
//			Class.forName("jdbms.sql.parsing.expressions.math.LessThanExpression");
//			Class.forName("jdbms.sql.parsing.expressions.math.NotEqualsExpression");
//			Class.forName("jdbms.sql.datatypes.IntSQLType");
//			Class.forName("jdbms.sql.datatypes.VarcharSQLType");
//		} catch (ClassNotFoundException e) {
//			System.err.println("Internal Error");
//		}
//		creator = new XMLCreator();
//		TableCreationParameters createTableParameters = new TableCreationParameters();
//		ArrayList<ColumnIdentifier> cols = new ArrayList<>();
//		cols.add(new ColumnIdentifier("ID","INT"));
//		cols.add(new ColumnIdentifier("Address", "VARCHAR"));
//		createTableParameters.setColumnDefinitions(cols);
//		createTableParameters.setTableName("Person");
//		Table table = new Table(createTableParameters);
//		InsertionParameters insertionParameters = new InsertionParameters();
//		ArrayList<String> columns = new ArrayList<>();
//		for (int i = 0; i < cols.size(); i++) {
//			columns.add(cols.get(i).getName());
//		}
//		ArrayList<ArrayList<String>> values = new ArrayList<>();
//		ArrayList<String> coList = new ArrayList<>();
//		coList.add("21");
//		ArrayList<String> coList2 = new ArrayList<>();
//		coList.add("'5 Abo Qeer St.'");
//		values.add(coList);
//		insertionParameters.setColumns(columns);
//		insertionParameters.setValues(values);
//		table.insertRows(insertionParameters);
//		parser = new Parser();
//		data = new SQLData();
//		String sql = new String("create Database DB;");
//		String normalizedOutput = parser.normalizeCommand(sql);
//		//System.out.println(normalizedOutput);
//		for (String key : InitialStatementFactory.getInstance().getRegisteredStatements()) {
//			InitialStatement statement = InitialStatementFactory.getInstance().createStatement(key);
//			if (statement.interpret(normalizedOutput)) {
//				//System.out.println(key);
//				statement.act(data);
//			}
//		}
//		creator.create(table, "DB", "Data");
//		sql = new String("create Database MyData;");
//		normalizedOutput = parser.normalizeCommand(sql);
//		//System.out.println(normalizedOutput);
//		for (String key : InitialStatementFactory.getInstance().getRegisteredStatements()) {
//			InitialStatement statement = InitialStatementFactory.getInstance().createStatement(key);
//			if (statement.interpret(normalizedOutput)) {
//				//System.out.println(key);
//				statement.act(data);
//			}
//		}
//		sql = new String("create table Person(ID int, Address varchar);");
//		normalizedOutput = parser.normalizeCommand(sql);
//		//System.out.println(normalizedOutput);
//		for (String key : InitialStatementFactory.getInstance().getRegisteredStatements()) {
//			InitialStatement statement = InitialStatementFactory.getInstance().createStatement(key);
//			if (statement.interpret(normalizedOutput)) {
//				//System.out.println(key);
//				statement.act(data);
//			}
//		}
//		sql = new String("insert into Person (ID, Address) values (21, '5 Abo Qeer St.');");
//		normalizedOutput = parser.normalizeCommand(sql);
//		//System.out.println(normalizedOutput);
//		for (String key : InitialStatementFactory.getInstance().getRegisteredStatements()) {
//			InitialStatement statement = InitialStatementFactory.getInstance().createStatement(key);
//			if (statement.interpret(normalizedOutput)) {
//				//System.out.println(key);
//				statement.act(data);
//			}
//		}
//		
//	}
//
//	@Test
//	public void test() {
//		String pathname = "Data"+ File.separator + "MyData" + File.separatorChar + "Person.xml";
//		File file = new File(pathname);
//		try {
//			BufferedReader reader = new BufferedReader(new FileReader(file));
//			String line = "";
//			String xml = "";
//			while ((line = reader.readLine()) != null) {
//				xml += line;
//			}
//			String first = new String(xml);
//			pathname = "Data"+ File.separator  +"DB" + File.separator+"Person.xml";
//			file = new File(pathname);
//			reader = new BufferedReader(new FileReader(file));
//			line = "";
//			xml = "";
//			while ((line = reader.readLine()) != null) {
//				xml += line;
//			}
//			
//			assertEquals(first, xml);
//			reader.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//}