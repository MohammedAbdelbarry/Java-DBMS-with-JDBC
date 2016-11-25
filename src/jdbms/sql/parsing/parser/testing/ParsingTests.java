package jdbms.sql.parsing.parser.testing;

import static org.junit.Assert.*;
import org.junit.Test;
import jdbms.sql.parsing.parser.Parser;

public class ParsingTests {

    @Test
    public void testMultipleSpaces() {
        Parser x = new Parser();
        String check = x.normalizeCommand("select    id from students ;");
        assertEquals(check, "SELECT ID FROM STUDENTS ;");      
    }

    @Test
    public void testGluedSemiColon() {
        Parser x = new Parser();
        String check = x.normalizeCommand("select id from students;");
        assertEquals(check, "SELECT ID FROM STUDENTS ;");      
    }

    @Test
    public void testMultipleColumns() {
        Parser x = new Parser();
        String check = x.normalizeCommand("insert into    ( id , name , grade ) from students where x % 2 == 0 > 5;");
        assertEquals(check, "INSERT INTO ( ID , NAME , GRADE ) FROM STUDENTS WHERE X % 2 == 0 > 5 ;");     
    }

    @Test
    public void testMultipleInsertingColumns() {
        Parser x = new Parser();
        String check = x.normalizeCommand("");
        assertEquals(check, "");       
    }

}