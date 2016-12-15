package jdbc.results.testing;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import jdbc.results.DataResultSet;
import jdbc.statement.DBStatement;

public class ResultSetTesting {

	private static final DBStatement DBStatement = null;

	@Test
	public void testAbsolute() throws SQLException {

		final DataResultSet resultSet = new DataResultSet(DBStatement);

		constructThreeRowedTable(resultSet);

		boolean check;

		// Check Before First
		check = resultSet.absolute(0);
		assertEquals(check, false);

		check = resultSet.absolute(-5);
		assertEquals(check, false);

		// Check inside the table
		check = resultSet.absolute(1);
		assertEquals(check, true);

		check = resultSet.absolute(-2);
		assertEquals(check, true);

		// Check After Last
		check = resultSet.absolute(4);
		assertEquals(check, false);

		check = resultSet.absolute(5);
		assertEquals(check, false);

	}

	@Test
	public void testNumberOfRows() throws SQLException {

		final DataResultSet resultSet = new DataResultSet(DBStatement);

		constructThreeRowedTable(resultSet);

		assertEquals(resultSet.isBeforeFirst(), true);

		int rows = 0;
		while (resultSet.next()) {
			rows++;
		}

		assertEquals(rows, 3);

		assertEquals(resultSet.isAfterLast(), true);
	}

	@Test
	public void testCursor() throws SQLException {

		final DataResultSet resultSet = new DataResultSet(DBStatement);

		constructThreeRowedTable(resultSet);

		assertEquals(resultSet.isBeforeFirst(), true);
		resultSet.next();
		assertEquals(resultSet.isFirst(), true);
		resultSet.next();
		resultSet.next();
		assertEquals(resultSet.isLast(), true);
		resultSet.next();
		assertEquals(resultSet.isAfterLast(), true);

		resultSet.absolute(-1);
		int rows = 0;
		while (resultSet.previous()) {
			rows++;
		}

		assertEquals(rows, 2);

		assertEquals(resultSet.isBeforeFirst(), true);

	}

	@Test
	public void testGettingDataByIndex() throws SQLException, ParseException {

		final DataResultSet resultSet = new DataResultSet(DBStatement);

		constructFourRowedTable(resultSet);

		resultSet.next();

		final int id = resultSet.getInt(1);
		assertEquals(id, 1);

		final String customer = resultSet.getString(2);
		assertEquals(customer, "Ahmed Mohamed");

		// Date accountDate = resultSet.getDate(3);
		// Convert to actual date
		// Date actualDate = new Date();
		// assertEquals(accountDate, actualDate);

		final double balance = resultSet.getDouble(4);
		final double actualBalance = 9999.99999;
		assertEquals(balance, actualBalance, 0);
	}

	@Test
	public void testGettingDataByName() throws SQLException, ParseException {

		final DataResultSet resultSet = new DataResultSet(DBStatement);

		constructFourRowedTable(resultSet);

		resultSet.next();

		final int id = resultSet.getInt("ID");
		assertEquals(id, 1);

		final String customer = resultSet.getString("Customer");
		assertEquals(customer, "Ahmed Mohamed");

		// Date accountDate = resultSet.getDate("AccountDate");
		// Convert to actual date
		// Date actualDate = new Date();
		// assertEquals(accountDate, actualDate);

		final double balance = resultSet.getDouble("Balance");
		final double actualBalance = 9999.99999;
		assertEquals(balance, actualBalance, 0);
	}

	private void constructFourRowedTable(final DataResultSet resultSet) {

		final String tableName = "Bank";

		final ArrayList<String> columns = new ArrayList<>();
		columns.add("ID");
		columns.add("Customer");
		columns.add("AccountDate");
		columns.add("Balance");

		final ArrayList<String> row1 = new ArrayList<>();
		row1.add("1");
		row1.add("Ahmed Mohamed");
		row1.add("2009-09-09");
		row1.add("9999.99999");
		final ArrayList<String> row2 = new ArrayList<>();
		row1.add("2");
		row1.add("Mohamed Ahmed");
		row1.add("2010-10-10");
		row1.add("12345.99999");
		final ArrayList<String> row3 = new ArrayList<>();
		row1.add("3");
		row1.add("Ahmed Adam");
		row1.add("2011-11-11");
		row1.add("100.0");
		final ArrayList<String> row4 = new ArrayList<>();
		row1.add("1");
		row1.add("Mohamed Adam");
		row1.add("2012-12-12");
		row1.add("0.0");

		final ArrayList<ArrayList<String>> columnRows = new ArrayList<>();
		columnRows.add(row1);
		columnRows.add(row2);
		columnRows.add(row3);
		columnRows.add(row4);

		final Map<String, Integer> columnTypes = new HashMap<>();
		columnTypes.put(columns.get(0), Types.INTEGER);
		columnTypes.put(columns.get(1), Types.VARCHAR);
		columnTypes.put(columns.get(2), Types.DATE);
		columnTypes.put(columns.get(3), Types.DOUBLE);

		resultSet.setTableName(tableName);
		resultSet.setColumns(columns);
		resultSet.setOutputRows(columnRows);
		resultSet.setColumnTypes(columnTypes);
	}

	private void constructThreeRowedTable(final DataResultSet resultSet) {

		final String tableName = "Students";

		final ArrayList<String> columns = new ArrayList<>();
		columns.add("ID");
		columns.add("Names");
		columns.add("Grades");

		final ArrayList<String> row1 = new ArrayList<>();
		row1.add("1");
		row1.add("Mohamed");
		row1.add("A");
		final ArrayList<String> row2 = new ArrayList<>();
		row1.add("2");
		row1.add("Ahmed");
		row1.add("A+");
		final ArrayList<String> row3 = new ArrayList<>();
		row1.add("3");
		row1.add("Gamal");
		row1.add("A+");

		final ArrayList<ArrayList<String>> columnRows = new ArrayList<>();
		columnRows.add(row1);
		columnRows.add(row2);
		columnRows.add(row3);

		final Map<String, Integer> columnTypes = new HashMap<>();
		columnTypes.put(columns.get(0), Types.INTEGER);
		columnTypes.put(columns.get(1), Types.VARCHAR);
		columnTypes.put(columns.get(2), Types.VARCHAR);

		resultSet.setTableName(tableName);
		resultSet.setColumns(columns);
		resultSet.setOutputRows(columnRows);
		resultSet.setColumnTypes(columnTypes);
	}
}