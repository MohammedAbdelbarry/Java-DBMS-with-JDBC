package jdbms.sql.parsing.properties;

public class DatabaseCreationParameters {
    private String databaseName;

    public DatabaseCreationParameters() {

    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String database) {
        this.databaseName = database;
    }

}
