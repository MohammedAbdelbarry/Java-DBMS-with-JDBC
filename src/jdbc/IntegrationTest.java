package jdbc;
import java.sql.Driver;

import org.junit.Assert;
import org.junit.Test;

public class IntegrationTest {
	static {
		try {
			Class.forName("jdbc.drivers.DBDriver");
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static Class<?> getSpecifications() {
		return Driver.class;
	}

	@Test
	public void test() {
		Assert.assertNotNull("Failed to create Driver implemenation",  TestRunner.getImplementationInstance());
	}

}
