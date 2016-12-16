package jdbc.results;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A JDBC Result Set Meta Data
 * Implementation.
 * @author Ahmed Moustafa El-Naggar
 */
public class MetaData implements ResultSetMetaData {

	private ArrayList<String> columnNames;
	private Map<String, Integer> columnTypes;
	private String tableName;
	private int columnCount;

	public MetaData() {
		columnNames = new ArrayList<>();
		columnTypes = new HashMap<>();
		tableName = null;
		columnCount = -1;
	}
	private boolean checkColumnIndex(final int col) {
		return col >= 1 && col <= columnNames.size();
	}
	public void setColumnNames(final ArrayList<String> columnNames) {
		this.columnNames = columnNames;
		setColumnCount(columnNames.size());
	}

	private void setColumnCount(final int columnCount) {
		this.columnCount = columnCount;
	}

	public void setColumnTypes(final Map<String, Integer> columnTypes) {
		this.columnTypes = columnTypes;
	}

	public void setTableName(final String tableName) {
		this.tableName = tableName;
	}
	/**
	 * @see jdbc.sql.ResultSetMetaData.ResultSetMetaData#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return columnCount;
	}

	/**
	 * @see jdbc.sql.ResultSetMetaData.ResultSetMetaData#getColumnLabel(int)
	 */
	@Override
	public String getColumnLabel(int column) {
		if (!checkColumnIndex(column)) {
			return null;
		}
		return columnNames.get(--column);
	}

	/**
	 * @see jdbc.sql.ResultSetMetaData.ResultSetMetaData#getColumnName(int)
	 */
	@Override
	public String getColumnName(int column) {
		if (!checkColumnIndex(column)) {
			return null;
		}
		return columnNames.get(--column);
	}

	/**
	 * @see jdbc.sql.ResultSetMetaData.ResultSetMetaData#getTableName(int)
	 */
	@Override
	public String getTableName(final int column) {
		return tableName;
	}

	@Override
	public int getColumnType(int column) throws SQLException {
		if (!checkColumnIndex(column)) {
			throw new SQLException();
		}
		final String columnName = columnNames.get(--column).toUpperCase();
		return columnTypes.get(columnName);
	}

	@Override
	public boolean isWrapperFor(final Class<?> arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T unwrap(final Class<T> arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getCatalogName(final int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getColumnClassName(final int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getColumnDisplaySize(final int column) throws SQLException {
		throw new UnsupportedOperationException();
	}


	@Override
	public String getColumnTypeName(final int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getPrecision(final int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getScale(final int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getSchemaName(final int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isAutoIncrement(final int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isCaseSensitive(final int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isCurrency(final int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isDefinitelyWritable(final int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int isNullable(final int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isReadOnly(final int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isSearchable(final int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isSigned(final int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isWritable(final int column) throws SQLException {
		throw new UnsupportedOperationException();
	}
}
