package jdbms.sql.file;

import java.io.IOException;

import jdbms.sql.data.Table;

/**
 * The contract that a back-end parser
 * must implement to be able to write a
 * table to the disk.
 * @author Mohammed Abdelbarry
 */
public interface TableWriter {
    /**
     * Saves the given table in a file.
     * @param table        the table to be saved
     * @param databaseName the database in which the table exists
     * @param path         the path of the directory
     * @throws IOException If the writer couldn't write the table to the disk
     */
    void write(final Table table,
               final String databaseName,
               final String path) throws IOException;
}
