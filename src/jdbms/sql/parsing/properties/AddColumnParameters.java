package jdbms.sql.parsing.properties;

import jdbms.sql.data.ColumnIdentifier;

/**
 * The Class AddColumnParameters.
 */
public class AddColumnParameters {

	/** The column identifier. */
	private ColumnIdentifier columnIdentifier;

	/** The table name. */
	private String tableName;

	/**
	 * Instantiates a new adds the column parameters.
	 */
	public AddColumnParameters() {

	}

	/**
	 * Gets the table name.
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * Sets the table name.
	 * @param tableName the tableName to set
	 */
	public void setTableName(final String tableName) {
		this.tableName = tableName;
	}

	/**
	 * Gets the column identifier.
	 * @return the {@link ColumnIdentifier}
	 */
	public ColumnIdentifier getColumnIdentifier() {
		return columnIdentifier;
	}

	/**
	 * Sets the column identifier.
	 * @param columnIdentifier the {@link ColumnIdentifier} to set
	 */
	public void setColumnIdentifier(final ColumnIdentifier columnIdentifier) {
		this.columnIdentifier = columnIdentifier;
	}

}
