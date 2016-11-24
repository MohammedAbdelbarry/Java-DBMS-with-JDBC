package jdbms.sql.parsing.parser.testing;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import jdbms.sql.parsing.parser.Parser;

public class ParsingTests {
	
	private Parser parser;
	@Before
	public void setUp() {
		parser = new Parser();
	}
	@Test
	public void testMultipleSpaces() {
		String check = parser.parse("select    id from students ;");
		assertEquals(check, "SELECT ID FROM STUDENTS ;");
	}
	
	@Test
	public void testGluedSemiColon() {
		String check = parser.parse("select id from students;");
		assertEquals(check, "SELECT ID FROM STUDENTS ;");
	}
	
	@Test
	public void testMultipleColumns() {
		String check = parser.parse("insert into    ( id , name , grade ) from students where x % 2 == 0 > 5;");
		assertEquals(check, "INSERT INTO (ID,NAME,GRADE) FROM STUDENTS WHERE X%2==0>5 ;");
	}
	
	@Test
	public void testMultipleColumns2() {
		String check = parser.parse("CREATE TABLE Persons(PersonID int,"
				+ "LastName varchar(255),FirstName varchar(255),"
				+ "Address varchar(255),"
				+ "City varchar(255));");
		assertEquals(check, "");
	}
}
