package jdbms.sql.parsing.parser.testing;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import jdbms.sql.parsing.parser.StringNormalizer;

public class NormalizingTesting {

    @Test
    public void testMultipleSpaces() {
        StringNormalizer x = new StringNormalizer();
        String check = x.normalizeCommand("select    id from students ;");
        assertEquals(check, "SELECT id FROM students ; ");
    }

    @Test
    public void testGluedSemiColon() {
        StringNormalizer x = new StringNormalizer();
        String check = x.normalizeCommand("select id from students;");
        assertEquals(check, "SELECT id FROM students ; ");
    }

    @Test
    public void testMultipleColumns() {
        StringNormalizer x = new StringNormalizer();
        String check = x.normalizeCommand("insert into    ( id , name , grade ) from students where x % 2 == 0 > 5;");
        assertEquals(check, "INSERT INTO ( id , name , grade ) FROM students WHERE x % 2 == 0 > 5 ; ");
    }

    @Test
    public void testMultipleInsertingColumns() {
        StringNormalizer x = new StringNormalizer();
        String check = x.normalizeCommand("");
        assertEquals(check, "");
    }

}