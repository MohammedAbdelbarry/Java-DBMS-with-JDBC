package jdbms.sql.file;

import java.io.IOException;

import jdbms.sql.data.Table;
import jdbms.sql.exceptions.ColumnAlreadyExistsException;
import jdbms.sql.exceptions.ColumnListTooLargeException;
import jdbms.sql.exceptions.ColumnNotFoundException;
import jdbms.sql.exceptions.InvalidDateFormatException;
import jdbms.sql.exceptions.RepeatedColumnException;
import jdbms.sql.exceptions.TypeMismatchException;
import jdbms.sql.exceptions.ValueListTooLargeException;
import jdbms.sql.exceptions.ValueListTooSmallException;

/**
 * The contract that a back-end parser
 * must implement to be able to read a
 * table from the disk.
 * @author Mohammed Abdelbarry
 */
public interface TableReader {
    /**
     * Reads a table from a given directory.
     * @param tableName    the name of the table to be read
     * @param databaseName the database in which the table exists
     * @param path         the path of the directory
     * @return the table object
     * @throws ColumnNotFoundException      When a column does not exist in the
     *                                      table
     * @throws TypeMismatchException        When the user tries to compare a
     *                                      value to a value of the wrong type
     * @throws ColumnAlreadyExistsException If a column with the same name
     *                                      already exists in the created table
     * @throws InvalidDateFormatException   If a date value in the table is
     *                                      wrong
     * @throws IOException                  If the reader failed to read the
     *                                      table from the disk
     * @throws RepeatedColumnException      If a column was repeated when
     *                                      loading the table
     * @throws ColumnListTooLargeException  If the column list was too large
     *                                      when loading the table
     * @throws ValueListTooLargeException   If the value list was too large
     * when
     *                                      loading the table
     * @throws ValueListTooSmallException   If the value list was too small
     * when
     *                                      loading the table
     */
    public Table read(String tableName, final String databaseName,
                      final String path)
            throws ColumnAlreadyExistsException,
            RepeatedColumnException,
            ColumnListTooLargeException,
            ColumnNotFoundException,
            ValueListTooLargeException,
            ValueListTooSmallException,
            TypeMismatchException,
            InvalidDateFormatException,
            IOException;
}
