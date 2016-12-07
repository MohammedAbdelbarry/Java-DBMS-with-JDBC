package jdbms.sql.file;

import jdbms.sql.data.Table;

public interface FileWriter {
	/**
	 * Saves the given table in a file.
	 * @param table the table to be saved
	 * @param databaseName the database
	 * in which the table exists
	 * @param path the path of the
	 * directory
	 */
	public void create(final Table table,
			final String databaseName,
			final String path);
}
