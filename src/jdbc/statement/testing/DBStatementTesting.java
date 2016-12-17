package jdbc.statement.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import jdbc.drivers.DBDriver;

public class DBStatementTesting {
	private final String protocol = "pbdb";
	private final String tmp
    = System.getProperty("java.io.tmpdir");
	private Statement statement;

	@Before
	public void executedBeforeEach() throws SQLException {
		try {
			final Driver driver = new DBDriver();
			final Properties info = new Properties();
			final File dbDir = new File(tmp + "/jdbc/"
		            + Math.round((((float) Math.
		            random()) * 100000)));
		    info.put("path", dbDir.getAbsoluteFile());
		    final Connection connection = driver.
		            connect("jdbc:"
		                    + protocol
		                    + "://localhost", info);
		    statement = connection.createStatement();
			statement.execute("Create database mypbdb");
			statement.execute("use mypbdb");
			statement.execute("create table tb (a int)");
		} catch (final Exception e) {
			e.printStackTrace();
			fail("Failed to create table.");
		}

	}

	@Test
	public void testExecute() {
		try {
			assertFalse(statement.execute("select * from tb"));
			assertFalse(statement.execute("insert into"
					+ " tb values (5)"));
			assertEquals(statement.getUpdateCount(), 1);
			assertTrue(statement.execute("select * from tb"));
		} catch (final Exception e) {
			e.printStackTrace();
			fail("Execute method threw a sql exception.");
		}
	}

	@Test
	public void testExecuteQuery() {
		try {
			assertFalse(statement.execute("insert"
					+ " into tb values (5)"));
			final ResultSet res = statement.
					executeQuery("select * from tb");
			assertTrue(res.next());
			assertEquals(res.getInt(1), 5);
		} catch (final Exception e) {
			e.printStackTrace();
			fail("failed.");
		}
		try {
			statement.executeQuery("Invalid"
					+ " query.");
		} catch (final SQLException e) {

		}
	}

	@Test
	public void testExecuteUpdate() {
		try {
			int updateCount = statement.
					executeUpdate("insert into tb (a)"
							+ " values (2), (3), (-1)");
			assertEquals(updateCount, 3);
			updateCount = statement.executeUpdate("delete"
					+ " from tb where a >= 2");
			assertEquals(updateCount, 2);
			updateCount = statement.executeUpdate("delete"
					+ " from tb where true");
			assertEquals(updateCount, 1);
		} catch (final Exception e) {
			e.printStackTrace();
			fail("Failed to update rows in the table.");
		}
	}

	@Test
	public void testUpdateCount() {
		try {
			statement.executeUpdate("insert"
					+ " into tb (a) values (2),"
					+ " (3), (-1)");
			assertEquals(statement.getUpdateCount(), 3);
			statement.executeUpdate("delete from tb"
					+ " where a >= 2");
			assertEquals(statement.getUpdateCount(), 2);
			statement.executeUpdate("delete from tb"
					+ " where true");
			assertEquals(statement.getUpdateCount(), 1);
		} catch (final Exception e) {
			e.printStackTrace();
			fail("Failed to update rows in the table.");
		}
	}

	@Test
	public void testExecuteBatch() {
		try {
			statement.addBatch("insert into tb values (5)");
			statement.addBatch("insert into tb values (5), (4)");
			statement.addBatch("insert into tb values (5), (4), (1)");
			statement.addBatch("select * from tb");
			final int[] updateCount = statement.executeBatch();
			assertEquals(updateCount[0], 1);
			assertEquals(updateCount[1], 2);
			assertEquals(updateCount[2], 3);
			assertEquals(updateCount[3], Statement.SUCCESS_NO_INFO);
		} catch (final Exception e) {
			e.printStackTrace();
			fail("Fail");
		}
	}
}
