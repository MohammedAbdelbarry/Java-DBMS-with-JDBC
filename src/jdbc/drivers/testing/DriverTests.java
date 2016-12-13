package jdbc.drivers.testing;

import java.sql.SQLException;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import jdbc.drivers.DBDriver;

public class DriverTests {

	DBDriver driver;
	@Before
	public void setUp() throws Exception {
		driver = new DBDriver();
	}

	@Test
	public void testValidURL() {
		try {
			Assert.assertTrue("Valid URL rejected",
					driver.acceptsURL("jdbc:xmldb://localhost"));
		} catch (final SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testInvalidURL() {
		try {
			Assert.assertFalse("Invalid URL accpeted",
					driver.acceptsURL("jdbc:pbdb://localhos"));
		} catch (final SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testConnectWithoutPath() {
		try {
			driver.connect("jdbc:xmldb://localhost",
					new Properties());
			Assert.fail();
		} catch (final SQLException e) {

		} catch (final Exception e) {
			Assert.fail();
		}
	}
}
