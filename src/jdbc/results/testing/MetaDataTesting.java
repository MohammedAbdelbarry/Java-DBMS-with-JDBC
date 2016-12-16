package jdbc.results.testing;

import static org.junit.Assert.assertEquals;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import jdbc.results.MetaData;

public class MetaDataTesting {

	@Test
	public void testColumnCount() {
		final MetaData metaData = new MetaData();

		constructTable(metaData);

		assertEquals(metaData.getColumnCount(), 4);
	}

	@Test
	public void testColumnLabels() {
		final MetaData metaData = new MetaData();
		constructTable(metaData);
		assertEquals(metaData.
				getColumnLabel(1), "ID");
		assertEquals(metaData.
				getColumnLabel(2), "Names");
		assertEquals(metaData.
				getColumnLabel(3), "Grades");
		assertEquals(metaData.
				getColumnLabel(4), "ExamDate");
	}

	@Test
	public void testColumnNames() {
		final MetaData metaData = new MetaData();
		constructTable(metaData);
		assertEquals(metaData.
				getColumnName(1), "ID");
		assertEquals(metaData.
				getColumnName(2), "Names");
		assertEquals(metaData.
				getColumnName(3), "Grades");
		assertEquals(metaData.
				getColumnName(4), "ExamDate");
	}

	@Test
	public void testTableName() {
		final MetaData metaData = new MetaData();
		constructTable(metaData);
		assertEquals(metaData.
				getTableName(1), "Students");
		assertEquals(metaData.
				getTableName(2), "Students");
		assertEquals(metaData.
				getTableName(3), "Students");
		assertEquals(metaData.
				getTableName(4), "Students");
		assertEquals(metaData.
				getTableName(5), "Students");

	}

	private void constructTable(final MetaData metaData) {

		final String tableName = "Students";

		final ArrayList<String> columns = new ArrayList<>();
		columns.add("ID");
		columns.add("Names");
		columns.add("Grades");
		columns.add("ExamDate");

		final Map<String, Integer> columnTypes = new HashMap<>();
		columnTypes.put(columns.get(0), Types.INTEGER);
		columnTypes.put(columns.get(1), Types.VARCHAR);
		columnTypes.put(columns.get(2), Types.VARCHAR);
		columnTypes.put(columns.get(3), Types.DATE);

		metaData.setTableName(tableName);
		metaData.setColumnNames(columns);
		metaData.setColumnTypes(columnTypes);
	}

}