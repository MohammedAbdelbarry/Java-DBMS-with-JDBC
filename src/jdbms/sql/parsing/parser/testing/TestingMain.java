package jdbms.sql.parsing.parser.testing;

import java.io.IOException;
import java.util.Scanner;

import jdbms.sql.data.SQLData;
import jdbms.sql.parsing.parser.Parser;
import jdbms.sql.parsing.statements.InitialStatement;
import jdbms.sql.parsing.statements.util.InitialStatementFactory;

public class TestingMain {

	public TestingMain() {

	}

	public static void main(String[] args) throws IOException {
		try {
			Class.forName("jdbms.sql.parsing.statements.CreateDatabaseStatement");
			Class.forName("jdbms.sql.parsing.statements.CreateTableStatement");
			Class.forName("jdbms.sql.parsing.statements.DropDatabaseStatement");
			Class.forName("jdbms.sql.parsing.statements.DropTableStatement");
			Class.forName("jdbms.sql.parsing.statements.InsertIntoStatement");
			Class.forName("jdbms.sql.parsing.statements.DeleteStatement");
			Class.forName("jdbms.sql.parsing.statements.SelectStatement");
			Class.forName("jdbms.sql.parsing.statements.UpdateStatement");
			Class.forName("jdbms.sql.parsing.statements.UseStatement");
			Class.forName("jdbms.sql.parsing.expressions.math.EqualsExpression");
			Class.forName("jdbms.sql.parsing.expressions.math.LargerThanEqualsExpression");
			Class.forName("jdbms.sql.parsing.expressions.math.LessThanEqualsExpression");
			Class.forName("jdbms.sql.parsing.expressions.math.LargerThanExpression");
			Class.forName("jdbms.sql.parsing.expressions.math.LessThanExpression");
			Class.forName("jdbms.sql.parsing.expressions.math.NotEqualsExpression");
			Class.forName("jdbms.sql.datatypes.IntSQLType");
			Class.forName("jdbms.sql.datatypes.VarcharSQLType");
		} catch (ClassNotFoundException e) {
			System.err.println("Internal Error");
		}
		SQLData data = new SQLData();
		Parser parser = new Parser();
		Scanner in = new Scanner(System.in);
		while (true) {
			StringBuilder stringBuilder = new StringBuilder();
			String sql = null;
			while (in.hasNextLine()) {
				stringBuilder.append(in.nextLine());
				if (stringBuilder.indexOf(";") != -1) {
					sql = stringBuilder.substring(0, stringBuilder.indexOf(";") + 1);
					break;
				}
			}
			System.out.println(sql);
			String normalizedOutput = parser.normalizeCommand(sql);
			System.out.println(normalizedOutput);
			for (String key : InitialStatementFactory.getInstance().getRegisteredStatements()) {
				InitialStatement statement = InitialStatementFactory.getInstance().createStatement(key);
				if (statement.interpret(normalizedOutput)) {
					System.out.println(key);
					statement.act(data);
				}
			}
		}
	}
}
