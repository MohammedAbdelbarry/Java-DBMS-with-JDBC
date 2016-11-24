package jdbms.sql.data;

import java.util.ArrayList;
import java.util.Collection;

public class Schema {

	private Collection<TableIdentifier> tableIdentifiers;
	public Schema() {
		tableIdentifiers = new ArrayList<>();
	}

	public Schema(ArrayList<TableIdentifier> tableIdentifiers) {
		this.tableIdentifiers = tableIdentifiers;
	}

}
