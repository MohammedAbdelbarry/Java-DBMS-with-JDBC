package jdbc.testing;

import java.io.File;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import jdbc.connections.DBConnection;
import jdbc.drivers.DBDriver;
import jdbc.results.DataResultSet;
import jdbc.statement.DBStatement;

public class JDBCTests {

	private DBDriver driver;
	private String url;
	private String protocol;
	private Properties info;
	@Before
	public void setUp() throws Exception {
		driver = new DBDriver();
		protocol = "xmldb";
		url = "jdbc:" + protocol + "://localhost";
		info = new Properties();
		final String tmp = System.getProperty("java.io.tmpdir");
		info.put("path", new File(tmp + "/jdbc/" + Math.random()));
	}

	@Test
	public void testSelectAll() {
		try {
			final DBConnection connection = (DBConnection) driver.connect(url, info);
			final DBStatement statement = (DBStatement) connection.createStatement();
			statement.execute("Create Database School");
			statement.execute("Create table Student (ID int, Name varchar, Grade float)");
			int count = statement.executeUpdate("INSERT INTO Student (ID, Name, Grade)"
					+ " VALUES (1 ,'Ahmed Khaled', 90.5)");
			Assert.assertEquals("Table Insertion did not return 1", 1, count);
			count  = statement.executeUpdate("INSERT INTO Student (ID, Name, Grade)"
					+ " VALUES (2 ,'Ahmed El Naggar', 90.2)");
			Assert.assertEquals("Table Insertion did not return 1", 1, count);
			final DataResultSet resultSet = (DataResultSet)
					statement.executeQuery("SELECT * FROM Student");
			resultSet.next();
			Assert.assertEquals("Failed to get Correct Float Value",
					90.5, resultSet.getFloat("Grade"), 0.0001);
			connection.close();
		} catch (final SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSelectWhere() {
		try {
			final DBConnection connection = (DBConnection) driver.connect(url, info);
			final DBStatement statement = (DBStatement) connection.createStatement();
			statement.execute("Create Database School");
			statement.execute("Create table Student (ID int, Name varchar, Grade float)");
			int count = statement.executeUpdate("INSERT INTO Student (ID, Name, Grade)"
					+ " VALUES (1 ,'Ahmed Khaled', 90.5)");
			Assert.assertEquals("Table Insertion did not return 1", 1, count);
			count  = statement.executeUpdate("INSERT INTO Student (ID, Name, Grade)"
					+ " VALUES (2 ,'Ahmed El Naggar', 90.2)");
			Assert.assertEquals("Table Insertion did not return 1", 1, count);
			count  = statement.executeUpdate("INSERT INTO Student (ID, Name, Grade)"
					+ " VALUES (3 ,'Ahmed Walid', 90.5)");
			count  = statement.executeUpdate("INSERT INTO Student (ID, Name, Grade)"
					+ " VALUES (4 ,'Anas Harby', 90.5)");
			final DataResultSet resultSet = (DataResultSet)
					statement.executeQuery("SELECT * FROM Student WHERE ID > 1");
			int numberOfMatches = 0;
			while (resultSet.next()) {
				numberOfMatches++;
			}
			Assert.assertEquals("Invalid Result Set Size",3, numberOfMatches);
			connection.close();
		} catch (final SQLException e) {
			e.printStackTrace();
		}
	}
}
