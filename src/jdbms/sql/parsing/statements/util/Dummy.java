package jdbms.sql.parsing.statements.util;

import java.util.ArrayList;
import java.util.Collection;

import jdbms.sql.data.ColumnIdentifier;
import jdbms.sql.parsing.parser.Parser;
import jdbms.sql.parsing.statements.CreateTableStatement;
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
			Class.forName("jdbms.sql.datatypes.IntSQLType");
			Class.forName("jdbms.sql.datatypes.VarcharSQLType");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		for (String key : InitialStatementFactory.getInstance().getRegisteredStatements()) {
			statements.add(InitialStatementFactory.getInstance().createStatement(key));
		}
	}
	public void parse(String sql) {
		CreateTableStatement x = new CreateTableStatement();
		if (x.interpret(sql)) {
			System.out.println("Accepted");
		}
		for (ColumnIdentifier col : x.getParameters().getColumnDefinitions()) {
			System.out.println(col.getName());
		}
	}
	public static void main(String args[]) {
		Dummy dummy = new Dummy();
		//String insertInto = "INSERT INTO STUDENTS VALUES (12,'HEHE',-20),(13,'HOHO' ,\"70\"),(-13,'HOHO' ,'701');";
		//String insertInto = "insert into MYTABLE values (\"(c1,c2);\", '(c3,c4);'), ('()', '),;();');";
		//String createDB = "cREATe             dAtAbASE     Mn$DdtOL;";
		//String dropDB = "      DROP DATABASE MY$DBLOL ;";
		//String dropTable = "DROP TABLE MY$DBLOL ;";
		//String createTable = "create table mytable (col1 int, col2 text, col3 int, col4 text);";
		//String delete = "delete from mytable where col1 >= 550;";
		//String update = "update _$table_name set column1=-8    ,    column2=9 where 1 = 'n,o';";
		//String update = "update mytable set col2 = ', ',col3 = ' WHERE ',col4 = ' ; ',col5='WHERE,; ; ,' where col1 = 'set where from \"select\" create ';";
		//String terminalUpdate = "update _$table_name set   column1='value1'   ,    column2='value2';";
		//String select = "seLeCt col1,col2,col3 FrOM table$_name whEre col1=',hi,babe,do,';";
		//String select = "select * from a where b = ' select * from a where b = e ';";
		//String selectAll = "    selecT  * fRom   fan$$555$tbaleshNammmmm         where _hamada=-5  ;";
		//String delete = "delete from a where b = ' delete ; )( /*SET * from a where b = e ';";
//		String delete = "delete from a where col>=-7 ;";
		//String alter = "alter table mytable add col1 int ;";
		//String insert = "insert into t values (4, 'hello\"myFag\"friend can , make you sad', \"where 'you' want tot fuck;().,!@#$%/*-+\", -555);";
		String create = "create table t (c1 int, c2 varchar, c3 text, c4 integer);";
		Parser p = new Parser();
		//dummy.parse(p.normalizeCommand(insertInto));
		//dummy.parse(p.normalizeCommand(createDB));
		//dummy.parse(p.normalizeCommand(dropDB));
		//dummy.parse(p.normalizeCommand(dropTable));
		//dummy.parse(p.normalizeCommand(createTable));
		//System.out.println(p.normalizeCommand(update));
		dummy.parse(p.normalizeCommand(create));

		//dummy.parse(p.normalizeCommand(update));
		//dummy.parse(p.normalizeCommand(terminalUpdate));
		//dummy.parse(p.normalizeCommand(select));
		//dummy.parse(p.normalizeCommand(alter));
	}

	/*public static void main(String args[]) {
		String right;
        String test = "name = '777 555' and id = 7 ;";
        String[] operands = test.split("=");
        operands[1] = operands[1].trim();
        System.out.println("The left operand is:" + operands[1]);

        char check = operands[1].charAt(0);
        System.out.println(check);
        if (check == '\'' || check == '"') {
            System.out.println("I'm a String");
            String trimy = operands[1].substring(1);
            System.out.println(trimy);
            right = check + trimy.trim().substring(0, trimy.trim().indexOf(check)).trim() + check;
            System.out.println(right);

            // Getting the rest of the expression

        } else {
            System.out.println("I'm a number");
            right = operands[1].trim().substring(0, operands[1].trim().indexOf(" ")).trim();
            System.out.println(right);
        }
	}*/
}
