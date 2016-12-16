package jdbms.sql.file.protobuff;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import jdbms.sql.data.Table;
import jdbms.sql.data.TableColumn;
import jdbms.sql.file.TableWriter;
import jdbms.sql.file.protobuff.util.TableProtos.DBTable;
import jdbms.sql.parsing.util.Constants;

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
}
