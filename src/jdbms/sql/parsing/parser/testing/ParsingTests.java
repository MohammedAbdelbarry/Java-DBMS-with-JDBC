package jdbms.sql.parsing.parser.testing;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import jdbms.sql.parsing.parser.Parser;

public class ParsingTests {

    @Test
    public void testMultipleSpaces() {
        Parser x = new Parser();
        String check = x.normalizeCommand("select    id from students ;");
        assertEquals(check, "SELECT id FROM students ; ");
    }

    @Test
    public void testGluedSemiColon() {
        Parser x = new Parser();
        String check = x.normalizeCommand("select id from students;");
        assertEquals(check, "SELECT id FROM students ; ");
    }

    @Test
    public void testMultipleColumns() {
        Parser x = new Parser();
        String check = x.normalizeCommand("insert into    ( id , name , grade ) from students where x % 2 == 0 > 5;");
        assertEquals(check, "INSERT INTO ( id , name , grade ) FROM students WHERE x % 2 == 0 > 5 ; ");
    }

    @Test
    public void testMultipleInsertingColumns() {
        Parser x = new Parser();
        String check = x.normalizeCommand("");
        assertEquals(check, "");
    }

}