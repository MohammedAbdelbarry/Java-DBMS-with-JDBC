package jdbms.sql;

import jdbms.sql.data.query.SelectQueryOutput;

public class DBMSConnector {

	public DBMSConnector(final String fileType) {

	}
	public int executeUpdate(final String sql) {
		return 0;
	}
	public SelectQueryOutput executeQuery(final String sql) {
		return null;
	}
	public boolean interpretUpdate(final String sql) {
		return false;
	}
	public boolean interpretQuery(final String sql) {
		return false;
	}
}
