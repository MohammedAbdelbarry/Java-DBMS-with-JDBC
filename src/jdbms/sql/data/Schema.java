package jdbms.sql.data;

import java.util.ArrayList;
import java.util.Collection;

public class Schema {

	private Collection<TableIdentifier> tableIdentifiers;
	public Schema(String databaseName) {
		tableIdentifiers = new ArrayList<>();
	}

	public Schema(ArrayList<TableIdentifier> tableIdentifiers) {
		this.tableIdentifiers = tableIdentifiers;
	}
}
