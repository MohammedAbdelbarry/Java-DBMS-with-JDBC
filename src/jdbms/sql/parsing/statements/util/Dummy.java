package jdbms.sql.parsing.statements.util;

import java.util.ArrayList;
import java.util.Collection;

import jdbms.sql.parsing.parser.Parser;
import jdbms.sql.parsing.statements.CreateDatabaseStatement;
import jdbms.sql.parsing.statements.CreateTableStatement;
import jdbms.sql.parsing.statements.DeleteStatement;
import jdbms.sql.parsing.statements.DropDatabaseStatement;
import jdbms.sql.parsing.statements.DropTableStatement;
import jdbms.sql.parsing.statements.SelectStatement;
import jdbms.sql.parsing.statements.Statement;
import jdbms.sql.parsing.statements.UpdateStatement;

public class Dummy {
	Collection<Statement> statements;
	public Dummy() {
		statements = new ArrayList<>();
	}
	/*
   //Before iterating over registered statements, we should register the 8 main statements.
	public void parse(String sql) {
		for (String key : StatementFactory.getInstance().getRegisteredStatements()) {
			statements.add(StatementFactory.getInstance().createStatement(key));
		}
		for(Statement statement : statements) {
			if (statement.interpret(sql)) {
				//statement.act();
				System.out.println("Success");
			}
		}
	}
	*/
	public void parse(String sql) {
		SelectStatement x = new SelectStatement();
		if (x.interpret(sql)) {
			System.out.println("Success");
		} else {
			System.out.println("Fail");
		}
	}
	public static void main(String args[]) {
		Dummy dummy = new Dummy();
		String sql = "    DELETE FROM my_table where ;"; 
		Parser p = new Parser();
		dummy.parse(p.parse(sql));
	}
}
