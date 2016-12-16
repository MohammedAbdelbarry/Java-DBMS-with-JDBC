package jdbms.sql.file.protobuff.testing;

import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import jdbms.sql.data.ColumnIdentifier;
import jdbms.sql.data.Table;
import jdbms.sql.file.protobuff.ProtocolBufferReader;
import jdbms.sql.file.protobuff.ProtocolBufferWriter;
import jdbms.sql.parsing.properties.InsertionParameters;
import jdbms.sql.parsing.properties.TableCreationParameters;
import jdbms.sql.util.ClassRegisteringHelper;

public class ProtocolBufferTesting {

	@Test
    public void testNull() {
		try {
			ClassRegisteringHelper.registerInitialStatements();
			final TableCreationParameters createTableParameters = new TableCreationParameters();
    	    final ProtocolBufferReader reader = new ProtocolBufferReader();
    	    final ProtocolBufferWriter writer = new ProtocolBufferWriter();
	   		createTableParameters.setTableName("horbIES");
	   		final ArrayList<ColumnIdentifier> identifiers = new ArrayList<>();
	   		identifiers.add(new ColumnIdentifier("f", "VARCHAR"));
	   		identifiers.add(new ColumnIdentifier("e", "REAL"));
	   		identifiers.add(new ColumnIdentifier("d", "INT"));
	   		identifiers.add(new ColumnIdentifier("c", "DATE"));
	   		createTableParameters.setColumnDefinitions(identifiers);
	   		final Table table = new Table(createTableParameters);
	   		final InsertionParameters insertParameters = new InsertionParameters();
	   		insertParameters.setTableName("horbIES");
	   		final ArrayList<String> rowValues = new ArrayList<>();
	   		rowValues.add("null");
	   		rowValues.add("3.5");
	   		rowValues.add("3");
	   		rowValues.add("'1996-12-12'");
	   		final ArrayList<ArrayList<String>> values = new ArrayList<>();
	   		values.add(rowValues);
	   		insertParameters.setValues(values);
	   		table.insertRows(insertParameters);
	   		writer.write(table, ".","");
	   		final Table loadedTable = reader.read(table.getName(), ".", "");
	   		Assert.assertEquals("horbIES", loadedTable.getName());
	   		Assert.assertEquals("", loadedTable.getColumns().get("F").get(0).toString());
	   		Assert.assertEquals(1, loadedTable.getNumberOfRows());
        } catch(final Exception e) {
            e.printStackTrace();
            fail("Failed.");
        }
    }

	@Test
	public void testBigInt() {
		try {
			ClassRegisteringHelper.registerInitialStatements();
			final TableCreationParameters createTableParameters = new TableCreationParameters();
    	    final ProtocolBufferReader reader = new ProtocolBufferReader();
    	    final ProtocolBufferWriter writer = new ProtocolBufferWriter();
    	    createTableParameters.setTableName("table1");
	   		final ArrayList<ColumnIdentifier> identifiers = new ArrayList<>();
	   		identifiers.add(new ColumnIdentifier("col_1", "BIGINT"));
	   		identifiers.add(new ColumnIdentifier("col_2", "TEXT"));
	   		identifiers.add(new ColumnIdentifier("col_3", "DOUBLE"));
	   		identifiers.add(new ColumnIdentifier("col_4", "DATE"));
	   		createTableParameters.setColumnDefinitions(identifiers);
	   		final Table table = new Table(createTableParameters);
	   		InsertionParameters insertParameters = new InsertionParameters();
	   		insertParameters.setTableName("table1");
	   		ArrayList<String> rowValues = new ArrayList<>();
	   		ArrayList<ArrayList<String>> values = new ArrayList<>();
	   		rowValues.add("10000000000");
	   		rowValues.add("'hi'");
	   		rowValues.add("3.5");
	   		rowValues.add("'1996-12-12'");
	   		values.add(rowValues);
	   		rowValues = new ArrayList<>();
	   		rowValues.add("10000000000");
	   		rowValues.add("'hi'");
	   		rowValues.add("3.5");
	   		rowValues.add("'1996-12-12'");
	   		values.add(rowValues);
	   		insertParameters.setValues(values);
	   		table.insertRows(insertParameters);
	   		writer.write(table, ".","");
	   		final Table loadedTable = reader.read(table.getName(), ".", "");
	   		Assert.assertEquals(2, loadedTable.getNumberOfRows());
	   		Assert.assertTrue(loadedTable.getColumns().get("COL_1").
	   				get(0).toString().equals("10000000000"));
	   		insertParameters = new InsertionParameters();
	   		rowValues = new ArrayList<>();
	   		values = new ArrayList<>();
	   		rowValues.add("1000000000000000000");
	   		rowValues.add("\"Hi, hello, hola, 'adios.\"");
	   		rowValues.add("-.3331");
	   		rowValues.add("'1996-03-03'");
	   		values.add(rowValues);
	   		insertParameters.setValues(values);
	   		loadedTable.insertRows(insertParameters);
	   		Assert.assertEquals(3, loadedTable.getNumberOfRows());
	   		Assert.assertTrue(loadedTable.getColumns().get("COL_1").
	   				get(2).toString().equals("1000000000000000000"));
        } catch(final Exception e) {
            e.printStackTrace();
            fail("Failed.");
        }
	}

	@Test
	public void testDateTime() {
		try {
			ClassRegisteringHelper.registerInitialStatements();
			final TableCreationParameters createTableParameters = new TableCreationParameters();
    	    final ProtocolBufferReader reader = new ProtocolBufferReader();
    	    final ProtocolBufferWriter writer = new ProtocolBufferWriter();
    	    createTableParameters.setTableName("table3");
	   		final ArrayList<ColumnIdentifier> identifiers = new ArrayList<>();
	   		identifiers.add(new ColumnIdentifier("a_1", "DATETIME"));
	   		identifiers.add(new ColumnIdentifier("a_2", "FLOAT"));
	   		identifiers.add(new ColumnIdentifier("a_3", "DOUBLE"));
	   		identifiers.add(new ColumnIdentifier("a_4", "REAL"));
	   		createTableParameters.setColumnDefinitions(identifiers);
	   		final Table table = new Table(createTableParameters);
	   		final InsertionParameters insertParameters = new InsertionParameters();
	   		insertParameters.setTableName("table3");
	   		ArrayList<String> rowValues = new ArrayList<>();
	   		final ArrayList<ArrayList<String>> values = new ArrayList<>();
	   		rowValues.add("'1996-03-03 03:04:05'");
	   		rowValues.add("9.9999999999");
	   		rowValues.add("-3.5");
	   		rowValues.add("-.99999991");
	   		values.add(rowValues);
	   		rowValues = new ArrayList<>();
	   		rowValues.add("'2016-12-16 05:05:59'");
	   		rowValues.add(".36");
	   		rowValues.add("2804095476.304520233618"
	   				+ "144679411623406549573264414"
	   				+ "497615806032371166825427914"
	   				+ "976287202675703372420668");
	   		rowValues.add("-2.5");
	   		values.add(rowValues);
	   		insertParameters.setValues(values);
	   		table.insertRows(insertParameters);
	   		writer.write(table, ".","");
	   		final Table loadedTable = reader.read(table.getName(), ".", "");
	   		Assert.assertEquals(2, loadedTable.getNumberOfRows());
	   		Assert.assertTrue(loadedTable.getColumns().get("A_1").
	   				get(0).toString().equals("'1996-03-03 03:04:05'"));
	   		Assert.assertTrue(loadedTable.getColumns().
	   				get("A_3").get(1).
	   				toString().equals("2.80409547630452E9"));
        } catch(final Exception e) {
            e.printStackTrace();
            fail("Failed.");
        }
	}

	@Test
	public void test() {
		try {
			ClassRegisteringHelper.registerInitialStatements();
			final TableCreationParameters createTableParameters = new TableCreationParameters();
    	    final ProtocolBufferReader reader = new ProtocolBufferReader();
    	    final ProtocolBufferWriter writer = new ProtocolBufferWriter();
    	    createTableParameters.setTableName("tb");
	   		final ArrayList<ColumnIdentifier> identifiers = new ArrayList<>();
	   		identifiers.add(new ColumnIdentifier("harby", "TEXT"));
	   		identifiers.add(new ColumnIdentifier("barry", "VARCHAR"));
	   		identifiers.add(new ColumnIdentifier("khaled", "INT"));
	   		identifiers.add(new ColumnIdentifier("naggar", "BIGINT"));
	   		identifiers.add(new ColumnIdentifier("etsh", "REAL"));
	   		identifiers.add(new ColumnIdentifier("tolbz", "FLOAT"));
	   		identifiers.add(new ColumnIdentifier("yakkout", "DOUBLE"));
	   		identifiers.add(new ColumnIdentifier("walls", "DATE"));
	   		identifiers.add(new ColumnIdentifier("omar", "DATETIME"));
	   		createTableParameters.setColumnDefinitions(identifiers);
	   		final Table table = new Table(createTableParameters);
	   		final InsertionParameters insertParameters = new InsertionParameters();
	   		insertParameters.setTableName("tb");
	   		ArrayList<String> rowValues = new ArrayList<>();
	   		final ArrayList<ArrayList<String>> values = new ArrayList<>();
	   		rowValues.add("\"harby's herbies\"");
	   		rowValues.add("'barsy ballsy'");
	   		rowValues.add("2805476");
	   		rowValues.add("999992221991");
	   		rowValues.add("-3.5");
	   		rowValues.add("-.99999991");
	   		rowValues.add("9.9999999999");
	   		rowValues.add("'1396-03-06'");
	   		rowValues.add("'1996-03-03 03:04:05'");
	   		values.add(rowValues);
	   		rowValues = new ArrayList<>();
	   		rowValues.add("'harby's'");
	   		rowValues.add("'barsy'");
	   		rowValues.add("-76");
	   		rowValues.add("-199992221991");
	   		rowValues.add("-.22455");
	   		rowValues.add("-.94999991");
	   		rowValues.add("9.9699999999");
	   		rowValues.add("'1356-03-06'");
	   		rowValues.add("'1391-03-03 03:04:05'");
	   		values.add(rowValues);
	   		insertParameters.setValues(values);
	   		table.insertRows(insertParameters);
	   		writer.write(table, ".","");
	   		final Table loadedTable = reader.
	   				read(table.getName(), ".", "");
	   		Assert.assertEquals(2, loadedTable.
	   				getNumberOfRows());
	   		Assert.assertTrue(loadedTable.
	   				getColumns().get("HARBY").
	   				get(0).toString().
	   				equals("\"harby's herbies\""));
	   		Assert.assertTrue(loadedTable.
	   				getColumns().
	   				get("ETSH").get(1).
	   				toString().
	   				equals("-0.22455"));
	   		Assert.assertTrue(loadedTable.
	   				getColumns().get("BARRY").
	   				get(1).toString().
	   				equals("'barsy'"));
	   		Assert.assertTrue(loadedTable.
	   				getColumns().
	   				get("OMAR").get(1).
	   				toString().
	   				equals("'1391-03-03"
	   						+ " 03:04:05'"));
	   		Assert.assertTrue(loadedTable.
	   				getColumns().
	   				get("WALLS").get(0).
	   				toString().
	   				equals("'1396-03-06'"));

        } catch(final Exception e) {
            e.printStackTrace();
            fail("Failed.");
        }
	}

	@Test
	public void testMultipleNulls() {
		try {
			ClassRegisteringHelper.registerInitialStatements();
			final TableCreationParameters createTableParameters = new TableCreationParameters();
    	    final ProtocolBufferReader reader = new ProtocolBufferReader();
    	    final ProtocolBufferWriter writer = new ProtocolBufferWriter();
    	    createTableParameters.setTableName("null");
	   		final ArrayList<ColumnIdentifier> identifiers = new ArrayList<>();
	   		identifiers.add(new ColumnIdentifier("col_1", "BIGINT"));
	   		identifiers.add(new ColumnIdentifier("col_2", "TEXT"));
	   		identifiers.add(new ColumnIdentifier("col_3", "DOUBLE"));
	   		identifiers.add(new ColumnIdentifier("col_4", "DATE"));
	   		createTableParameters.setColumnDefinitions(identifiers);
	   		final Table table = new Table(createTableParameters);
	   		InsertionParameters insertParameters = new InsertionParameters();
	   		insertParameters.setTableName("null");
	   		ArrayList<String> rowValues = new ArrayList<>();
	   		ArrayList<ArrayList<String>> values = new ArrayList<>();
	   		rowValues.add("null");
	   		rowValues.add("'hi'");
	   		rowValues.add("3.5");
	   		rowValues.add("null");
	   		values.add(rowValues);
	   		rowValues = new ArrayList<>();
	   		rowValues.add("10000000000");
	   		rowValues.add("null");
	   		rowValues.add("null");
	   		rowValues.add("null");
	   		values.add(rowValues);
	   		insertParameters.setValues(values);
	   		table.insertRows(insertParameters);
	   		writer.write(table, ".","");
	   		final Table loadedTable = reader.read(table.getName(), ".", "");
	   		Assert.assertEquals(2, loadedTable.getNumberOfRows());
	   		Assert.assertTrue(loadedTable.getColumns().get("COL_1").
	   				get(0).toString().equals(""));
	   		Assert.assertTrue(loadedTable.getColumns().get("COL_4").
	   				get(0).toString().equals(""));
	   		Assert.assertFalse(loadedTable.getColumns().get("COL_2").
	   				get(0).toString().equals(""));
	   		Assert.assertTrue(loadedTable.getColumns().get("COL_3").
	   				get(0).toString().equals("3.5"));
	   		Assert.assertTrue(loadedTable.getColumns().get("COL_3").
	   				get(1).toString().equals(""));
	   		insertParameters = new InsertionParameters();
	   		rowValues = new ArrayList<>();
	   		values = new ArrayList<>();
	   		rowValues.add("null");
	   		rowValues.add("\"Hi, hello, hola, 'adios.\"");
	   		rowValues.add("null");
	   		rowValues.add("'1996-03-03'");
	   		values.add(rowValues);
	   		insertParameters.setValues(values);
	   		loadedTable.insertRows(insertParameters);
	   		Assert.assertEquals(3, loadedTable.getNumberOfRows());
	   		Assert.assertTrue(loadedTable.getColumns().get("COL_1").
	   				get(2).toString().equals(""));
	   		Assert.assertFalse(loadedTable.getColumns().get("COL_4").
	   				get(2).toString().equals(""));
	   		Assert.assertTrue(loadedTable.getColumns().get("COL_2").
	   				get(2).toString().equals("\"Hi, hello, hola, 'adios.\""));
        } catch(final Exception e) {
            e.printStackTrace();
            fail("Failed.");
        }
	}
}
