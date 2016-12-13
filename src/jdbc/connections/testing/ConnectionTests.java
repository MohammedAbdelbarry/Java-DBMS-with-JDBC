package jdbc.connections.testing;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import jdbc.connections.DBConnection;

public class ConnectionTests {

	DBConnection connection;
	@Before
	public void setUp() throws Exception {
		connection = new DBConnection("jdbc:altdb://localhost", "");
	}

	@Test
	public void testCreateStatement() {
		try {
			Assert.assertNotNull("Null Statement Object Returned",
					connection.createStatement());
		} catch (final SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testClose() {
		try {
			connection.close();
			assertTrue("Connection NOT closed yet!", connection.isClosed());
		} catch (final SQLException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testCreateStatementAfterClose() {
		try {
			connection.close();
			connection.createStatement();
			Assert.fail();
		} catch (final SQLException e) {

		} catch (final Exception e) {
			Assert.fail();
		}
	}

}
