package jdbms.sql.parsing.properties;

public class DatabaseDroppingParameters {
	private String databaseName;
	public DatabaseDroppingParameters() {

	}
	public String getDatabaseName() {
		return databaseName;
	}
	public void setDatabaseName(String database) {
		this.databaseName = database;
	}

}
