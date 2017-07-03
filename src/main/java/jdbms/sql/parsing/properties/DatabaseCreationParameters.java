package jdbms.sql.parsing.properties;

/**
 * The Class DatabaseCreationParameters.
 */
public class DatabaseCreationParameters {

    /** The database name. */
    private String databaseName;

    /**
     * Instantiates a new database creation parameters.
     */
    public DatabaseCreationParameters() {

    }

    /**
     * Gets the database name.
     * @return the database name
     */
    public String getDatabaseName() {
        return databaseName;
    }

    /**
     * Sets the database name.
     * @param database the new database name
     */
    public void setDatabaseName(final String database) {
        this.databaseName = database;
    }

}
