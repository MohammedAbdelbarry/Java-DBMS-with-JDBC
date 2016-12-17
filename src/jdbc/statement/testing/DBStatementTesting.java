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
	}

	@Test
	public void testExecute() {
		try {
			assertFalse(statement.execute("select * from tb"));
			assertFalse(statement.execute("insert into tb values (5)"));
			assertEquals(statement.getUpdateCount(), 1);
			assertTrue(statement.execute("select * from tb"));
		} catch (final Exception e) {
			fail("Execute method threw a sql exception.");
			e.printStackTrace();
		}
	}

	@Test
	public void testExecuteQuery() {
		try {
			assertFalse(statement.execute("insert into tb values (5)"));
			final ResultSet res = statement.executeQuery("");
		} catch (final Exception e) {
			fail("failed.");
		}
	}

	@Test
	public void testExecuteUpdate() {

	}

	@Test
	public void testExecuteBatch() {

	}

	@Test
	public void testConnection() {

	}

	@Test
	public void testUpdateCount() {
		// getupdate account after execute, executeupdate, executebatch.
	}
}
