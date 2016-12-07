package jdbms.sql.file;

import jdbms.sql.data.Table;
import jdbms.sql.exceptions.ColumnAlreadyExistsException;
import jdbms.sql.exceptions.ColumnListTooLargeException;
import jdbms.sql.exceptions.ColumnNotFoundException;
import jdbms.sql.exceptions.InvalidDateFormatException;
import jdbms.sql.exceptions.RepeatedColumnException;
import jdbms.sql.exceptions.TypeMismatchException;
import jdbms.sql.exceptions.ValueListTooLargeException;
import jdbms.sql.exceptions.ValueListTooSmallException;

public interface FileReader {
	/**
	 * Reads a table from a given directory.
	 * @param tableName the name of the
	 * table to be read
	 * @param databaseName the database
	 * in which the table exists
	 * @param path the path of the
	 * directory
	 * @return the table object
	 * @throws TypeMismatchException
	 * @throws ValueListTooSmallException
	 * @throws ValueListTooLargeException
	 * @throws ColumnNotFoundException
	 * @throws ColumnListTooLargeException
	 * @throws RepeatedColumnException
	 * @throws ColumnAlreadyExistsException
	 * @throws InvalidDateFormatException
	 */
	public Table parse(String tableName, final String databaseName,
			final String path)
					throws ColumnAlreadyExistsException,
					RepeatedColumnException,
					ColumnListTooLargeException,
					ColumnNotFoundException,
					ValueListTooLargeException,
					ValueListTooSmallException,
					TypeMismatchException,
					InvalidDateFormatException;
}
