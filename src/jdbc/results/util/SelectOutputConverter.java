package jdbc.results.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jdbc.results.DataResultSet;
import jdbms.sql.data.ColumnIdentifier;
import jdbms.sql.data.query.SelectQueryOutput;

public class SelectOutputConverter {

	public SelectOutputConverter() {

	}

	public DataResultSet convert(final DataResultSet resultSet, final SelectQueryOutput output) {
		final ArrayList<String> columnNames = getColumnNames(output.getColumns());
		resultSet.setTableName(output.getTableName());
		resultSet.setColumns(columnNames);
		resultSet.setOutputRows(output.getData());
		resultSet.setColumnTypes(getTypesMap(columnNames, getColumnTypes(output.getColumns())));
		return resultSet;
	}

	private Map<String, Integer> getTypesMap(final ArrayList<String> names,
			final ArrayList<Integer> types) {
		final Map<String, Integer> columnTypes = new HashMap<>();
		for (int i = 0; i < names.size(); i++) {
			columnTypes.put(names.get(i).toUpperCase(), types.get(i));
		}
		return columnTypes;
	}
	private ArrayList<Integer> getColumnTypes(final ArrayList<ColumnIdentifier> columnIdentifiers) {
		final ArrayList<Integer> columnTypes = new ArrayList<>();
		for (final ColumnIdentifier columnIdentifier : columnIdentifiers) {
			columnTypes.add(columnIdentifier.getTypeNumber());
		}
		return columnTypes;
	}
	private ArrayList<String> getColumnNames(final ArrayList<ColumnIdentifier> columnIdentifiers) {
		final ArrayList<String> columnNames = new ArrayList<>();
		for (final ColumnIdentifier columnIdentifier : columnIdentifiers) {
			columnNames.add(columnIdentifier.getName());
		}
		return columnNames;
	}
}
