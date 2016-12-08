package jdbc.results;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.print.attribute.standard.RequestingUserName;

/**
 * Meta Data that describes the 
 *
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

	public void setColumnNames(ArrayList<String> columnNames) {
		this.columnNames = columnNames;
		setColumnCount(columnNames.size());
	}

	private void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}

	public void setColumnTypes(Map<String, Integer> columnTypes) {
		this.columnTypes = columnTypes;
	}

	public void setTableName(String tableName) {
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
		return columnNames.get(column);
	}

	/**
	 * @see jdbc.sql.ResultSetMetaData.ResultSetMetaData#getColumnName(int)
	 */
	@Override
	public String getColumnName(int column) {
		return columnNames.get(column);
	}

	/**
	 * @see jdbc.sql.ResultSetMetaData.ResultSetMetaData#getTableName(int)
	 */
	@Override
	public String getTableName(int column) {
		return tableName;
	}

	@Override
	public int getColumnType(int column) throws SQLException {
		String columnName = columnNames.get(column);
		return columnTypes.get(columnName);
		
	}

	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getCatalogName(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getColumnClassName(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getColumnDisplaySize(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}


	@Override
	public String getColumnTypeName(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getPrecision(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getScale(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getSchemaName(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isAutoIncrement(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isCaseSensitive(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isCurrency(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isDefinitelyWritable(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int isNullable(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isReadOnly(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isSearchable(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isSigned(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isWritable(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

}
