package jdbms.sql.file.protobuff;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import jdbms.sql.data.ColumnIdentifier;
import jdbms.sql.data.Table;
import jdbms.sql.data.TableColumn;
import jdbms.sql.exceptions.ColumnAlreadyExistsException;
import jdbms.sql.exceptions.ColumnListTooLargeException;
import jdbms.sql.exceptions.ColumnNotFoundException;
import jdbms.sql.exceptions.InvalidDateFormatException;
import jdbms.sql.exceptions.RepeatedColumnException;
import jdbms.sql.exceptions.TypeMismatchException;
import jdbms.sql.exceptions.ValueListTooLargeException;
import jdbms.sql.exceptions.ValueListTooSmallException;
import jdbms.sql.file.TableWriter;
import jdbms.sql.file.protobuff.util.TableProtos.DBTable;
import jdbms.sql.parsing.properties.InsertionParameters;
import jdbms.sql.parsing.properties.TableCreationParameters;
import jdbms.sql.parsing.util.Constants;
import jdbms.sql.util.ClassRegisteringHelper;

public class ProtocolBufferWriter implements TableWriter {
	private static final String PROTOCOL_BUFFER_EXTENSION
	= ".protobuff";

	public ProtocolBufferWriter() {

	}

	@Override
	public void write(final Table table,
			final String databaseName, final String path)
					throws IOException {
		final DBTable protoTable = createProtoTable(table);
		final File protoBuffFile = new File(path
				+ databaseName + File.separator
				+ table.getName().toLowerCase()
				+ PROTOCOL_BUFFER_EXTENSION);
		if (!protoBuffFile.exists()) {
			protoBuffFile.createNewFile();
		}
		final FileOutputStream outputStream
		= new FileOutputStream(protoBuffFile);
		protoTable.writeTo(outputStream);
		outputStream.close();
	}

	private DBTable createProtoTable(final Table table) {
		final DBTable.Builder protoTableBuilder = DBTable.newBuilder();
		protoTableBuilder.setNumberOfRows(table.getNumberOfRows());
		protoTableBuilder.setTableName(table.getName());
		for (final String columnName : table.getColumnNames()) {
			final DBTable.TableColumn.
			Builder protoColumn = DBTable.TableColumn.newBuilder();
			final TableColumn currColumn = table.
					getColumns().get(columnName.toUpperCase());
			protoColumn.setColumnName(currColumn.getColumnName());
			protoColumn.setColumnType(currColumn.getColumnDataType());
			for (String currValue : currColumn.getValues()) {
				if (currValue.equals("")) {
					currValue = Constants.NULL_INDICATOR;
				}
				protoColumn.addValues(currValue);
			}
			protoTableBuilder.addTableColumns(protoColumn);
		}
		return protoTableBuilder.build();
	}

	public static void main(final String[] args) throws IOException, ColumnAlreadyExistsException, InvalidDateFormatException, RepeatedColumnException, ColumnListTooLargeException, ColumnNotFoundException, ValueListTooLargeException, ValueListTooSmallException, TypeMismatchException {
		final TableCreationParameters createTableParameters = new TableCreationParameters();
		ClassRegisteringHelper.registerInitialStatements();
		createTableParameters.setTableName("horbIES");
		final ArrayList<ColumnIdentifier> identifiers = new ArrayList<>();
		identifiers.add(new ColumnIdentifier("f", "VARCHAR"));
		identifiers.add(new ColumnIdentifier("e", "REAL"));
		identifiers.add(new ColumnIdentifier("d", "INT"));
		identifiers.add(new ColumnIdentifier("c", "DATE"));
		createTableParameters.setColumnDefinitions(identifiers);
		final Table table = new Table(createTableParameters);
		final InsertionParameters insertParameters = new InsertionParameters();
		insertParameters.setTableName("horbIES");
		final ArrayList<String> rowValues = new ArrayList<>();
		rowValues.add("null");
		rowValues.add("3.5");
		rowValues.add("3");
		rowValues.add("'1996-12-12'");
		final ArrayList<ArrayList<String>> values = new ArrayList<>();
		values.add(rowValues);
		insertParameters.setValues(values);
		table.insertRows(insertParameters);
		final ProtocolBufferWriter writer = new ProtocolBufferWriter();
		writer.write(table, ".","");
		final ProtocolBufferReader reader = new ProtocolBufferReader();
		final Table newTable = reader.read(table.getName(), ".", "");
		System.out.println(newTable.getName());
		System.out.println(newTable.getColumns().get("F").get(0).toString());
    }
}
