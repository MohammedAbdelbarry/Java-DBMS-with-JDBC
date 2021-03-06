package jdbms.sql.file.json;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

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
import jdbms.sql.file.TableReader;
import jdbms.sql.parsing.util.Constants;

/**
 * The JSON reader class that
 * reads a table object from a file
 * and returns this object.
 * @author Mohammed Abdelbary
 */
public class JSONReader implements TableReader {

    /**
     * The JSON extension.
     */
    private static final String JSON_EXTENSION
            = ".json";

    /**
     * Instantiates a new JSON reader.
     */
    public JSONReader() {

    }

    @Override
    public Table read(final String tableName,
                      final String databaseName, final String path)
            throws ColumnAlreadyExistsException,
            RepeatedColumnException,
            ColumnListTooLargeException,
            ColumnNotFoundException,
            ValueListTooLargeException,
            ValueListTooSmallException,
            TypeMismatchException, IOException {
        final Gson gson = new GsonBuilder().
                registerTypeAdapter(TableColumn.class,
                        new TableColumnDeserializier()).create();
        final File jsonFile = new File(path
                + databaseName.toLowerCase() + File.separator
                + tableName.toLowerCase() + JSON_EXTENSION);
        final BufferedReader reader
                = new BufferedReader(new java.io.FileReader(jsonFile));
        final StringBuilder json = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
            json.append(line);
        }
        reader.close();
        return gson.fromJson(json.toString(), Table.class);
    }

    /**
     * The deserializer class that converts json
     * elements to sql values.
     */
    private class TableColumnDeserializier
            implements JsonDeserializer<TableColumn> {

        /**
         * The Constant NAME_FIELD.
         */
        private static final String NAME_FIELD
                = "columnName";

        /**
         * The Constant TYPE_FIELD.
         */
        private static final String TYPE_FIELD
                = "columnType";

        /**
         * The Constant VALUES_FIELD.
         */
        private static final String VALUES_FIELD
                = "values";

        /**
         * The Constant VALUE_FIELD.
         */
        private static final String VALUE_FIELD
                = "value";

        @Override
        public TableColumn deserialize(final JsonElement json,
                                       final Type type,
                                       final JsonDeserializationContext
                                               jsonDeserializationContext)
                throws JsonParseException {
            final JsonObject jsonObject = json.getAsJsonObject();
            final String columnName
                    = jsonObject.get(NAME_FIELD).getAsString();
            final String columnType
                    = jsonObject.get(TYPE_FIELD).getAsString();
            final TableColumn column
                    = new TableColumn(columnName, columnType);
            final JsonArray values = jsonObject.get(VALUES_FIELD)
                    .getAsJsonArray();
            for (final JsonElement sqlObject : values) {
                String value = sqlObject.getAsJsonObject()
                        .get(VALUE_FIELD).getAsString();
                if (value.equals(Constants.NULL_INDICATOR)) {
                    value = null;
                }
                try {
                    column.add(value);
                } catch (final InvalidDateFormatException e) {

                }
            }
            return column;
        }
    }
}
