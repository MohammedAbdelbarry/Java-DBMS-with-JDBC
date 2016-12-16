package jdbms.sql.data.util;

import java.util.ArrayList;
import java.util.Comparator;

import jdbms.sql.datatypes.SQLType;

public class SQLComparator<E extends Comparable<E>, T extends SQLType<E>>
        implements Comparator<ArrayList<SQLType<?>>> {
    int colIndex;

    public SQLComparator(final int columnIndex) {
        this.colIndex = columnIndex;
    }

    @Override
    public int compare(final ArrayList<SQLType<?>> row1,
                       final ArrayList<SQLType<?>> row2) {

        @SuppressWarnings("unchecked")
        final T cell1 = (T) row1.get(colIndex);
        @SuppressWarnings("unchecked")
        final T cell2 = (T) row2.get(colIndex);
        return cell1.compareTo(cell2);
    }

}
