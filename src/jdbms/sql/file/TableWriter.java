package jdbms.sql.file;

import java.io.IOException;

import jdbms.sql.data.Table;

public interface TableWriter {
	/**
	 * Saves the given table in a file.
	 * @param table the table to be saved
	 * @param databaseName the database
	 * in which the table exists
	 * @param path the path of the
	 * directory
	 * @throws IOException
	 */
    void write(final Table table,
               final String databaseName,
               final String path) throws IOException;
}
