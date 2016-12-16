package jdbms.sql.file.json;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import jdbms.sql.data.Table;
import jdbms.sql.datatypes.SQLType;
import jdbms.sql.file.TableWriter;
import jdbms.sql.parsing.util.Constants;

/**
 * The JSON Writer class that
 * writes a table object in a file.
 * @author Mohammed Abdelbary
 */
public class JSONWriter implements TableWriter {

	/** The JSON extenstion. */
	private static final String JSON_EXTENSION
	= ".json";

	/**
	 * Instantiates a new JSON writer.
	 */
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

	/**
	 * The SQL serializer respolsible for
	 * serializing sql values.
	 */
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
}
