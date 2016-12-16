package jdbms.sql.file.protobuff;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import jdbms.sql.data.ColumnIdentifier;
import jdbms.sql.data.Table;
import jdbms.sql.exceptions.ColumnAlreadyExistsException;
import jdbms.sql.exceptions.ColumnListTooLargeException;
import jdbms.sql.exceptions.ColumnNotFoundException;
import jdbms.sql.exceptions.InvalidDateFormatException;
import jdbms.sql.exceptions.RepeatedColumnException;
import jdbms.sql.exceptions.TypeMismatchException;
import jdbms.sql.exceptions.ValueListTooLargeException;
import jdbms.sql.exceptions.ValueListTooSmallException;
import jdbms.sql.file.TableReader;
import jdbms.sql.file.protobuff.util.TableProtos.DBTable;
import jdbms.sql.file.protobuff.util.TableProtos.DBTable.TableColumn;
import jdbms.sql.parsing.properties.InsertionParameters;
import jdbms.sql.parsing.properties.TableCreationParameters;

/**
 * The class that reads a table object
 * from a file and returns this object.
 * @author Ahmed Walid
 */
public class ProtocolBufferReader implements TableReader {

	/** The protocol buffer extension. */
	private static final String PROTOCOL_BUFFER_EXTENSION
	= ".protobuff";

	/**
	 * Instantiates a new protocol buffer reader.
	 */
	public ProtocolBufferReader() {

	}

	@Override
	public Table read(final String tableName,
			final String databaseName, final String path)
					throws ColumnAlreadyExistsException,
			RepeatedColumnException, ColumnListTooLargeException,
			ColumnNotFoundException, ValueListTooLargeException,
			ValueListTooSmallException, TypeMismatchException,
			InvalidDateFormatException, IOException {
		final File protoBuffFile = new File(path
				+ databaseName.toLowerCase() + File.separator
				+ tableName.toLowerCase() + PROTOCOL_BUFFER_EXTENSION);
		final DBTable protoTable = DBTable.
				parseFrom(new FileInputStream(protoBuffFile));
		return createTableObject(protoTable);
	}

	/**
	 * Creates a table object using the
	 * data retrieved from reading the file.
	 * @param protoTable the proto table object
	 * @return the table object
	 * @throws ColumnAlreadyExistsException if a
	 * column already exists in the table
	 * @throws InvalidDateFormatException the date
	 * format was invalid
	 * @throws RepeatedColumnException the column
	 * was repeated
	 * @throws ColumnListTooLargeException the column list
	 * was larger than the value list
	 * @throws ColumnNotFoundException the column
	 * wasn't found in the table
	 * @throws ValueListTooLargeException the value list
	 * was larger than the column list
	 * @throws ValueListTooSmallException the value list
	 * was smaller than the column list
	 * @throws TypeMismatchException the type of the
	 * value being inserted doesn't match the
	 * type of the column
	 */
	private Table createTableObject(final DBTable protoTable)
			throws ColumnAlreadyExistsException, InvalidDateFormatException,
			RepeatedColumnException, ColumnListTooLargeException,
			ColumnNotFoundException, ValueListTooLargeException,
			ValueListTooSmallException, TypeMismatchException {
		final TableCreationParameters creationParams
		= new TableCreationParameters();
		final InsertionParameters insertionParams
		= new InsertionParameters();
		insertionParams.setTableName(protoTable.getTableName());
		creationParams.setTableName(protoTable.getTableName());
		final ArrayList<ColumnIdentifier> columnsDefinitions = new ArrayList<>();
		final ArrayList<String> columnNames = new ArrayList<>();
		final ArrayList<ArrayList<String>> values = new ArrayList<>();
		for (final TableColumn currentProtoColumn : protoTable.
				getTableColumnsList()) {
			columnsDefinitions.add(new ColumnIdentifier(currentProtoColumn.
					getColumnName(),
					currentProtoColumn.getColumnType()));
			columnNames.add(currentProtoColumn.getColumnName());
		}
		insertionParams.setColumns(columnNames);
		creationParams.setColumnDefinitions(columnsDefinitions);
		final Table table = new Table(creationParams);
		for (int i = 0; i < protoTable.getNumberOfRows(); i++) {
			final ArrayList<String> rowValues = new ArrayList<>();
			for (final TableColumn currentProtoColumn : protoTable.
					getTableColumnsList()) {
				rowValues.add(currentProtoColumn.getValues(i));
			}
			values.add(rowValues);
		}
		insertionParams.setValues(values);
		table.insertRows(insertionParams);
		return table;
	}
}
