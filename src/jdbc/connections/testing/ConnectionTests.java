package jdbc.connections.testing;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import jdbc.connections.DBConnection;
import jdbms.sql.parsing.parser.ParserMain;

public class ConnectionTests {

	private DBConnection connection;

	@Before
	public void setUp() throws Exception {
		try {
			final CodeSource codeSource = ParserMain.class.
					getProtectionDomain().getCodeSource();
			final File jarFile = new File(
					codeSource.getLocation().toURI().getPath());
			final String path = jarFile.getParentFile().getPath()
					+ File.separator + "Data";
			connection = new DBConnection("jdbc:altdb://localhost", path);
		} catch (final URISyntaxException e) {
			throw new RuntimeException();
		}
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
