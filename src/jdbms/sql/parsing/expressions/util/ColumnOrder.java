package jdbms.sql.parsing.expressions.util;

public class ColumnOrder {

	private static final String ASCENDING = "ASC";
	private static final String DESCENDING = "DESC";
	private ColumnExpression column;
	private String order;

	public ColumnOrder(String columnName, String order) {
		this.column = new ColumnExpression(columnName.trim());
		this.order = order.trim();
	}

	public void setColumn(String columnName) {
		column = new ColumnExpression(columnName);
	}

	public ColumnExpression getColumn() {
		return this.column;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getOrder() {
		return this.order;
	}

	public boolean isValidColumnOrder() {
		return this.column.isValidColumnName()
				&& (this.order.equals(ASCENDING)
				|| this.order.equals(DESCENDING));
	}

	public boolean isAscending() {
		return this.order.equals(ASCENDING);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((column == null) ? 0 : column.hashCode());
		result = prime * result + ((order == null) ? 0 : order.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ColumnOrder other = (ColumnOrder) obj;
		if (column == null) {
			if (other.column != null)
				return false;
		} else if (!column.equals(other.column))
			return false;
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
			return false;
		return true;
	}
}
