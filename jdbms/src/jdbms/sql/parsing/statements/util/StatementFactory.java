package jdbms.sql.parsing.statements.util;

import jdbms.sql.parsing.statements.*;

public class StatementFactory {

	private StatementFactory() {
		// TODO Auto-generated constructor stub
	}
	public Statement getStatement(String statementType) {
		if (statementType.equals("SELECT")) {
			return new SelectStatement();
		} else if (statementType.equals("UPDATE")) {
			return new SelectStatement();
		} else if (statementType.equals("INSERT")) {
			return new SelectStatement();
		} else {
			return null;			
		}
	}
}
