package jdbms.sql;

import jdbms.sql.data.query.SelectQueryOutput;

public class DBMSConnector {

	public DBMSConnector(final String fileType) {

	}
	int executeUpdate(final String sql) {
		return 0;
	}
	SelectQueryOutput executeQuery(final String sql) {
		return null;
	}
	boolean interpretUpdate(final String sql) {
		return false;
	}
	boolean interpretQuery(final String sql) {
		return false;
	}
}
