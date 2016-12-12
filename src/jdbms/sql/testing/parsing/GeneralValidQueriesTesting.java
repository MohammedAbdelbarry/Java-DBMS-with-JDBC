package jdbms.sql.testing.parsing;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import jdbms.sql.data.ColumnIdentifier;
import jdbms.sql.parsing.parser.StringNormalizer;
import jdbms.sql.parsing.statements.InitialStatement;
import jdbms.sql.parsing.statements.util.InitialStatementFactory;
import jdbms.sql.util.HelperClass;

public class GeneralValidQueriesTesting {

	private StringNormalizer normalizer;
	private Collection<InitialStatement> statements;

	@Before
	public void executedBeforeEach() {
		normalizer = new StringNormalizer();
		statements = new ArrayList<>();
		HelperClass.registerInitialStatements();
		for (final String key : InitialStatementFactory.
				getInstance().getRegisteredStatements()) {
			statements.add(InitialStatementFactory.
					getInstance().createStatement(key));
		}
	}

	@Test
	public void testCreateTable() {
		String sqlCommand = "crEate Table _t_ (_a_ int,"
				+ " _b_ float, _c_ real, _d_ double,"
				+ " _e_ bIgiNt, _f_ date, _g_ dateTime,"
				+ " _h_ varchar, _i_ real, _j_ text, _k_ InTeGer );";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		final String tableName = "_t_";
		final ArrayList <ColumnIdentifier> list = new ArrayList<>();
		list.add(new ColumnIdentifier("_a_", "INT"));
		list.add(new ColumnIdentifier("_b_", "FLOAT"));
		list.add(new ColumnIdentifier("_c_", "REAL"));
		list.add(new ColumnIdentifier("_d_", "DOUBLE"));
		list.add(new ColumnIdentifier("_e_", "BIGINT"));
		list.add(new ColumnIdentifier("_f_", "DATE"));
		list.add(new ColumnIdentifier("_g_", "DATETIME"));
		list.add(new ColumnIdentifier("_h_", "VARCHAR"));
		list.add(new ColumnIdentifier("_i_", "REAL"));
		list.add(new ColumnIdentifier("_j_", "TEXT"));
		list.add(new ColumnIdentifier("_k_", "INTEGER"));
		int interpretedCount = 0;
		for (final InitialStatement statement : statements) {
			if (statement.interpret(sqlCommand)) {
				interpretedCount++;
				assertEquals(tableName,
						statement.getParameters().
						getTableName());
				assertEquals(list,
						statement.getParameters().
						getColumnDefinitions());
			}
		}
		assertEquals(interpretedCount, 1);
	}

	@Test
	public void testDelete() {
		String sqlCommand = "Delete from myT where false;";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		final String tableName = "myT";
		int interpretedCount = 0;
		for (final InitialStatement statement : statements) {
			if (statement.interpret(sqlCommand)) {
				interpretedCount++;
				assertEquals(tableName,
						statement.getParameters().getTableName());
				assertEquals(statement.getParameters().
						getCondition().getLeftOperand(), "1");
				assertEquals(statement.getParameters().
						getCondition().getRightOperand(), "1");
			}
		}
		assertEquals(interpretedCount, 1);
	}

	@Test
	public void testDeleteComplexBooleanExpression() {
		String sqlCommand = "Delete from myT where _col"
				+ " != \"DELETE; FR'OM MYT WH'RE ;_COL != !@;#"
				+ "$;%^;^^;&*(;)__+=/*-+ ORDER BY OR WHERE\";";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		final String tableName = "myT";
		int interpretedCount = 0;
		for (final InitialStatement statement : statements) {
			if (statement.interpret(sqlCommand)) {
				interpretedCount++;
				assertEquals(tableName,
						statement.getParameters().getTableName());
				assertEquals(statement.getParameters().
						getCondition().getLeftOperand(), "_col");
				assertEquals(statement.
						getParameters().getCondition().
						getRightOperand(), "\"DELETE; FR'OM MYT"
								+ " WH'RE ;_COL != !@;#$;%^;^^;"
								+ "&*(;)__+=/*-+ ORDER BY OR WHERE\"");
			}
		}
		assertEquals(interpretedCount, 1);
	}

	@Test
	public void testInsertInto() {
		String sqlCommand = "Insert into myT (_a, _d_, _e_) "
				+ "values (0001.01, ',values; insert into myt (table)',"
				+ " '0001-01-01 00:00:00'),"
				+ " (-9.200, \"\",\"0001-01-10 00:00:00\");";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		final String tableName = "myT";
		final ArrayList<String> columnNames = new ArrayList<>();
		columnNames.add("_a");
		columnNames.add("_d_");
		columnNames.add("_e_");
		final ArrayList<ArrayList<String>> newRows = new ArrayList<>();
		newRows.add(new ArrayList<>());
		newRows.get(0).add("0001.01");
		newRows.get(0).add("',values; insert into myt (table)'");
		newRows.get(0).add("'0001-01-01 00:00:00'");
		newRows.add(new ArrayList<>());
		newRows.get(1).add("-9.200");
		newRows.get(1).add("\"\"");
		newRows.get(1).add("\"0001-01-10 00:00:00\"");
		int interpretedCount = 0;
		for (final InitialStatement statement : statements) {
			if (statement.interpret(sqlCommand)) {
				interpretedCount++;
				assertEquals(tableName,
						statement.getParameters().getTableName());
				assertEquals(columnNames, statement.
						getParameters().getColumns());
				assertEquals(newRows, statement.
						getParameters().getValues());
			}
		}
		assertEquals(interpretedCount, 1);
	}

	@Test
	public void testSelect() {
		String sqlCommand = "select distinct col1, col2, col3 from t where "
				+ "col1 <= -.0000000000000000000000000000001;";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		final String tableName = "t";
		final ArrayList<String> columnNames = new ArrayList<>();
		columnNames.add("col1");
		columnNames.add("col2");
		columnNames.add("col3");
		int interpretedCount = 0;
		for (final InitialStatement statement : statements) {
			if (statement.interpret(sqlCommand)) {
				interpretedCount++;
				assertEquals(tableName,
						statement.getParameters().getTableName());
				assertEquals(columnNames,
						statement.getParameters().getColumns());
				assertEquals(statement.getParameters().
						getCondition().getLeftOperand(), "col1");
				assertEquals(statement.getParameters().
						getCondition().getRightOperand(),
						"-.0000000000000000000000000000001");
			}
		}
		assertEquals(interpretedCount, 1);
	}

	@Test
	public void testUpdate() {
		String sqlCommand = "update hamada set "
				+ "col1 = \"set where; 'set where' hello\" ,"
				+ " col2 = '3425-12-22 14:01:22', col3 = 0.0000000000000000001 "
				+ " where col1 != ';; ;;; ;; ;; ' ;";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		final String tableName = "hamada";
		int interpretedCount = 0;
		for (final InitialStatement statement : statements) {
			if (statement.interpret(sqlCommand)) {
				interpretedCount++;
				assertEquals(tableName,
						statement.getParameters().getTableName());
				assertEquals(statement.getParameters().
						getAssignmentList().get(0).getLeftOperand(), "col1");
				assertEquals(statement.getParameters().
						getAssignmentList().get(0).getRightOperand(),
						"\"set where; 'set where' hello\"");
				assertEquals(statement.getParameters().
						getAssignmentList().get(1).getLeftOperand(), "col2");
				assertEquals(statement.getParameters().
						getAssignmentList().get(1).getRightOperand(),
						"'3425-12-22 14:01:22'");
				assertEquals(statement.getParameters().
						getAssignmentList().get(2).getLeftOperand(),
						"col3");
				assertEquals(statement.getParameters().
						getAssignmentList().get(2).getRightOperand(),
						"0.0000000000000000001");
				assertEquals(statement.getParameters().
						getCondition().getLeftOperand(), "col1");
				assertEquals(statement.getParameters().
						getCondition().getRightOperand(), "';; ;;; ;; ;; '");
			}
		}
		assertEquals(interpretedCount, 1);
	}

	@Test
	public void testAlterTableAddColumn() {
		String sqlCommand = "ALTeR	 TAbLE	t_tt_t_ttTT_tTT"
				+ "   AdD		 ColumnName datetime;";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		final String tableName = "t_tt_t_ttTT_tTT";
		final ArrayList <ColumnIdentifier> list = new ArrayList<>();
		list.add(new ColumnIdentifier("ColumnName", "DATETIME"));
		int interpretedCount = 0;
		for (final InitialStatement statement : statements) {
			if (statement.interpret(sqlCommand)) {
				interpretedCount++;
				assertEquals(tableName,
						statement.getParameters().getTableName());
				assertEquals(list,
						statement.getParameters().getColumnDefinitions());
			}
		}
		assertEquals(interpretedCount, 1);
	}

	@Test
	public void testAlterTableDropColumn() {
		String sqlCommand = "alter table __t_ "
				+ "drop ColumN c, w, _a, ____b, ___d, c, w;";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		final String tableName = "__t_";
		final ArrayList <String> list = new ArrayList<>();
		list.add("c");
		list.add("w");
		list.add("_a");
		list.add("____b");
		list.add("___d");
		list.add("c");
		list.add("w");
		int interpretedCount = 0;
		for (final InitialStatement statement : statements) {
			if (statement.interpret(sqlCommand)) {
				interpretedCount++;
				assertEquals(tableName,
						statement.getParameters().getTableName());
				assertEquals(statement.interpret(sqlCommand), true);
				assertEquals(list,
						statement.getParameters().getColumns());
			}
		}
		assertEquals(interpretedCount, 1);
	}

	@Test
	public void testCreateDatabase() {
		String sqlCommand = "create daTabase ddDD_aaAAaaA_tTTt_a_b_asse ;";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		final String databaseName = "ddDD_aaAAaaA_tTTt_a_b_asse";
		int interpretedCount = 0;
		for (final InitialStatement statement : statements) {
			if (statement.interpret(sqlCommand)) {
				interpretedCount++;
				assertEquals(databaseName,
						statement.getParameters().getDatabaseName());
			}
		}
		assertEquals(interpretedCount, 1);
	}

	@Test
	public void testDropDatabase() {
		String sqlCommand = "Drop daTaBase ___DataBase;";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		final String databaseName = "___DataBase";
		int interpretedCount = 0;
		for (final InitialStatement statement : statements) {
			if (statement.interpret(sqlCommand)) {
				interpretedCount++;
				assertEquals(databaseName,
						statement.getParameters().getDatabaseName());
			}
		}
		assertEquals(interpretedCount, 1);
	}

	@Test
	public void testUse() {
		String sqlCommand = "uSE _myDatabase__				;";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		final String databaseName = "_myDatabase__";
		int interpretedCount = 0;
		for (final InitialStatement statement : statements) {
			if (statement.interpret(sqlCommand)) {
				interpretedCount++;
				assertEquals(databaseName,
						statement.getParameters().getDatabaseName());
			}
		}
		assertEquals(interpretedCount, 1);
	}

	@Test
	public void testDropTable() {
		String sqlCommand = "dRop TablE my___________table;";
		sqlCommand = normalizer.normalizeCommand(sqlCommand);
		final String tableName = "my___________table";
		int interpretedCount = 0;
		for (final InitialStatement statement : statements) {
			if (statement.interpret(sqlCommand)) {
				interpretedCount++;
				assertEquals(tableName,
						statement.getParameters().getTableName());
			}
		}
		assertEquals(interpretedCount, 1);
	}
}
