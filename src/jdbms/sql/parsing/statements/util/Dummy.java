package jdbms.sql.parsing.statements.util;

import java.util.ArrayList;
import java.util.Collection;

import jdbms.sql.parsing.parser.Parser;
import jdbms.sql.parsing.statements.Statement;

public class Dummy {
	Collection<Statement> statements;
	public Dummy() {
		statements = new ArrayList<>();
		try {
			Class.forName("jdbms.sql.parsing.statements.CreateDatabaseStatement");
			Class.forName("jdbms.sql.parsing.statements.CreateTableStatement");
			Class.forName("jdbms.sql.parsing.statements.DropDatabaseStatement");
			Class.forName("jdbms.sql.parsing.statements.DropTableStatement");
			Class.forName("jdbms.sql.parsing.statements.InsertIntoStatement");
			Class.forName("jdbms.sql.parsing.statements.DeleteStatement");
			Class.forName("jdbms.sql.parsing.statements.SelectStatement");
			Class.forName("jdbms.sql.parsing.statements.UpdateStatement");
			Class.forName("jdbms.sql.parsing.expressions.math.EqualsExpression");
			Class.forName("jdbms.sql.parsing.expressions.math.LargerThanEqualsExpression");
			Class.forName("jdbms.sql.parsing.expressions.math.LessThanEqualsExpression");
			Class.forName("jdbms.sql.parsing.expressions.math.LargerThanExpression");
			Class.forName("jdbms.sql.parsing.expressions.math.LessThanExpression");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		for (String key : StatementFactory.getInstance().getRegisteredStatements()) {
			statements.add(StatementFactory.getInstance().createStatement(key));
		}
	}
	public void parse(String sql) {	
		for(Statement statement : statements) {
			if (statement.interpret(sql)) {
				//statement.act();
				System.out.println("Success");
			}
		}
	}
	public static void main(String args[]) {
		Dummy dummy = new Dummy();
		String insertInto = "insert INtO    SOmEFANcY$TABLE(A_B,C_D)     VALUES (\"hamada\", 'hamada');";
		String createDB = "cREATe                                                       dAtAbASE     Mn$DdtOL;";
		String dropDB = "      DROP DATABASE MY$DBLOL ;";
		String dropTable = "DROP TABLE MY$DBLOL ;";
		String createTable = "CREATE TABLE TT$LOLNAME (col1 INT), (Col2, VARCHAR), (col3, TEXT) ;";
		String delete = "DELETE FROM Customers WHERE name='H O';";
		String update = "update _$table_name set   column1='value1',column2='value2'  where life   =   'false';";
		String terminalUpdate = "update _$table_name set   column1='value1'   ,    column2='value2';";
		String select = "    seLeCt colum$$n_name,co$$$LLLmn_name   FrOM table$_name whEre col1='val'     ;";
		String selectAll = "    selecT  * fRom   fan$$555$tbaleshNammmmm         where _hamada=5  ;";
		Parser p = new Parser();
		dummy.parse(p.normalizeCommand(insertInto));
		//dummy.parse(p.normalizeCommand(createDB));
		//dummy.parse(p.normalizeCommand(dropDB));
		//dummy.parse(p.normalizeCommand(dropTable));
		//dummy.parse(p.normalizeCommand(createTable));
		//dummy.parse(p.normalizeCommand(delete));
		//dummy.parse(p.normalizeCommand(terminalUpdate));
		//dummy.parse(p.normalizeCommand(selectAll));
	}
}
