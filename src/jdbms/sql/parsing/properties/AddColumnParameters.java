package jdbms.sql.parsing.properties;

import jdbms.sql.data.ColumnIdentifier;

public class AddColumnParameters {
	private ColumnIdentifier columnIdentifier;
	private String tableName;
	public AddColumnParameters() {

	}

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @return the {@link ColumnIdentifier}
	 */
	public ColumnIdentifier getColumnIdentifier() {
		return columnIdentifier;
	}

	/**
	 * @param columnIdentifier the {@link ColumnIdentifier} to set
	 */
	public void setColumnIdentifier(ColumnIdentifier columnIdentifier) {
		this.columnIdentifier = columnIdentifier;
	}

}
