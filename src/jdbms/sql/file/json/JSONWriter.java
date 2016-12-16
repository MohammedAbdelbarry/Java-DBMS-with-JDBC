package jdbms.sql.file.json;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import jdbms.sql.data.ColumnIdentifier;
import jdbms.sql.data.Table;
import jdbms.sql.datatypes.SQLType;
import jdbms.sql.exceptions.ColumnAlreadyExistsException;
import jdbms.sql.exceptions.ColumnListTooLargeException;
import jdbms.sql.exceptions.ColumnNotFoundException;
import jdbms.sql.exceptions.InvalidDateFormatException;
import jdbms.sql.exceptions.RepeatedColumnException;
import jdbms.sql.exceptions.TypeMismatchException;
import jdbms.sql.exceptions.ValueListTooLargeException;
import jdbms.sql.exceptions.ValueListTooSmallException;
import jdbms.sql.file.TableWriter;
import jdbms.sql.parsing.properties.InsertionParameters;
import jdbms.sql.parsing.properties.TableCreationParameters;
import jdbms.sql.parsing.util.Constants;
import jdbms.sql.util.HelperClass;

public class JSONWriter implements TableWriter {
	private static final String JSON_EXTENSION
	= ".json";
	public JSONWriter() {

	}

	@Override
	public void write(final Table table,
			final String databaseName, final String path)
					throws IOException {
		final Gson gson = new GsonBuilder()
				.registerTypeAdapter(SQLType.class,
						new SQLTypeSerializer())
				.setPrettyPrinting().create();
		final String json = gson.toJson(table);
		final File jsonFile = new File(path
				+ databaseName + File.separator
				+ table.getName().toLowerCase()
				+ JSON_EXTENSION);
		if (!jsonFile.exists()) {
			jsonFile.createNewFile();
		}
		final FileOutputStream outputStream
		= new FileOutputStream(jsonFile);
		outputStream.write(json.getBytes());
		outputStream.close();
	}
	private class SQLTypeSerializer
	implements JsonSerializer<SQLType<?>> {

		@Override
		public JsonElement serialize(final SQLType<?> sqlValue,
				final Type type,
				final JsonSerializationContext jsonSerializationContext) {
			final JsonElement element
			= jsonSerializationContext.serialize(
					sqlValue, sqlValue.getClass());
			String value = sqlValue.toString();
			if (value.equals("")) {
				value = Constants.NULL_INDICATOR;
			}
			element.getAsJsonObject().addProperty("value", value);
			return element;
		}
	}
	public static void main(final String[] args) throws ColumnAlreadyExistsException,
	InvalidDateFormatException, RepeatedColumnException,
	ColumnListTooLargeException, ColumnNotFoundException,
	ValueListTooLargeException, ValueListTooSmallException,
	TypeMismatchException, IOException {
		final TableCreationParameters createTableParameters = new TableCreationParameters();
		HelperClass.registerInitialStatements();
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
		rowValues.add("\"gss\"");
		rowValues.add("3.5");
		rowValues.add("3");
		rowValues.add("'1996-12-12'");
		final ArrayList<ArrayList<String>> values = new ArrayList<>();
		values.add(rowValues);
		insertParameters.setValues(values);
		table.insertRows(insertParameters);
		final JSONWriter obj = new JSONWriter();
		obj.write(table, ".","");
		final JSONReader reader = new JSONReader();
		final Table newTable = reader.read(table.getName(), ".", "");
		System.out.println(newTable.getName());
		System.out.println(newTable.getColumns().get("F").get(0).toString());
	}
}
