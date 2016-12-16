package jdbms.sql.data;

import java.util.ArrayList;

public class TableIdentifier {
    private final String tableName;
    private final ArrayList<ColumnIdentifier> columns;

    public TableIdentifier(final String name,
                           final ArrayList<ColumnIdentifier> columns) {
        tableName = name;
        this.columns = columns;
    }

    public String getTableName() {
        return tableName;
    }

    public ArrayList<ColumnIdentifier>
    getColumnsIdentifiers() {
        return columns;
    }

    public ArrayList<String> getColumnNames() {
        final ArrayList<String> names = new ArrayList<>();
        for (final ColumnIdentifier columnIdentifier : columns) {
            names.add(columnIdentifier.getName());
        }
        return names;
    }
}
