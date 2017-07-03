package jdbc.results;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import jdbc.statement.DBStatement;

/**
 * A JDBC Result Set
 * Implementation.
 * @author Hisham Osama
 */
public final class DataResultSet implements ResultSet {
    private static final String DATE_FORMAT
            = "yyyy-MM-dd";
    private static final String DATE_TIME_FORMAT
            = "yyyy-MM-ddHH:mm:ss";
    private ArrayList<String> columnNames;
    private ArrayList<ArrayList<String>> outputRows;
    private Map<String, Integer> columnTypes;
    private int cursor;
    private boolean isClosed;
    private MetaData metaData;
    private final DBStatement DBStatement;


    public DataResultSet(final DBStatement DBStatement) {
        this.DBStatement = DBStatement;
        this.columnNames = new ArrayList<>();
        this.outputRows = new ArrayList<>();
        this.isClosed = false;
        this.cursor = -1;
        this.columnTypes = new HashMap<>();
        this.metaData = new MetaData();
    }

    public void setTableName(final String tableName) {
        metaData.setTableName(tableName);
    }

    public void setColumns(final ArrayList<String> columns) {
        this.columnNames = columns;
        metaData.setColumnNames(columns);
    }

    public void setOutputRows(final
                              ArrayList<ArrayList<String>> outputRows) {
        this.outputRows = outputRows;
    }

    public void setColumnTypes(final
                               Map<String, Integer> columnTypes) {
        this.columnTypes = columnTypes;
        metaData.setColumnTypes(columnTypes);
    }

    public int getColumnType(final
                             int column) throws SQLException {
        final String columnName = columnNames.get(column);
        return columnTypes.get(columnName);
    }


    private boolean checkCursor() {
        return this.cursor >= 0
                && this.cursor
                < outputRows.size();
    }

    @Override
    public int getRow() throws SQLException {
        if (isClosed) {
            throw new SQLException();
        }
        if (!checkCursor()) {
            return 0;
        }
        return cursor + 1;
    }

    @Override
    public boolean absolute(int row)
            throws SQLException {
        if (isClosed) {
            throw new SQLException();
        }
        if (row == 0) {
            beforeFirst();
            return false;
        } else if (row > 0) {
            row--;
            if (row >= this.outputRows.size()) {
                afterLast();
                return false;
            } else {
                this.cursor = row;
                return true;
            }
        } else {
            row = Math.abs(row);
            if (row > this.outputRows.size()) {
                beforeFirst();
                return false;
            } else {
                row = this.outputRows.size() - row;
                this.cursor = row;
                return true;
            }
        }
    }

    @Override
    public void afterLast() throws SQLException {

        if (this.isClosed) {
            throw new SQLException();
        }

        if (outputRows.size() > 0) {
            this.cursor = outputRows.size();
        }
    }

    @Override
    public void beforeFirst() throws SQLException {

        if (isClosed) {
            throw new SQLException();
        }

        if (outputRows.size() > 0) {
            cursor = -1;
        }
    }

    @Override
    public void close() throws SQLException {
        this.isClosed = true;
        this.columnNames = null;
        this.outputRows = null;
        this.columnTypes = null;
        this.cursor = -1;
        this.metaData = null;
    }

    @Override
    public int findColumn(final String columnLabel)
            throws SQLException {

        if (isClosed) {
            throw new SQLException();
        }

        boolean foundColumn = false;
        int columnNumber = 0;
        for (int i = 0; i < columnNames.size(); i++) {
            if (columnNames.get(i).equalsIgnoreCase(
                    columnLabel)) {
                columnNumber = i + 1;
                foundColumn = true;
                break;
            }
        }
        if (!foundColumn) {
            throw new SQLException();
        }
        return columnNumber;
    }

    @Override
    public boolean first() throws SQLException {

        if (this.isClosed) {
            throw new SQLException();
        }

        if (outputRows.size() > 0) {
            this.cursor = 0;
            return true;
        }
        return false;
    }

    @Override
    public Date getDate(int columnIndex)
            throws SQLException {

        if (isClosed) {
            throw new SQLException();
        }

        if (columnIndex < 1 || columnIndex
                > columnNames.size()
                || !checkCursor()) {
            throw new SQLException();
        }

        columnIndex--;
        final String columnLabel
                = columnNames.get(columnIndex).toUpperCase();
        if (columnTypes.get(columnLabel.toUpperCase())
                == null) {
            throw new SQLException();
        }
        if (columnTypes.get(columnLabel)
                != Types.DATE) {
            throw new SQLException();
        }
        if (outputRows.get(cursor).get(columnIndex)
                == null) {
            return null;
        }
        try {
            final String value
                    = outputRows.get(cursor).get(columnIndex);
            final SimpleDateFormat dateFormat
                    = new SimpleDateFormat(DATE_FORMAT);
            dateFormat.setLenient(false);
            final java.util.Date date = dateFormat.parse(
                    value.replaceAll("\\s", "")
                            .replaceAll("['\"]", ""));
            return new Date(date.getTime());
        } catch (final ParseException e) {
            throw new SQLException();
        }
    }

    @Override
    public Date getDate(final String columnLabel)
            throws SQLException {

        if (isClosed) {
            throw new SQLException();
        }
        if (columnLabel == null) {
            throw new SQLException();
        }
        int columnIndex = findColumn(columnLabel);
        if (columnIndex < 1 || columnIndex
                > columnNames.size()
                || !checkCursor()) {
            throw new SQLException();
        }
        columnIndex--;
        if (columnTypes.get(columnLabel.toUpperCase())
                == null) {
            throw new SQLException();
        }
        if (columnTypes.get(columnLabel.toUpperCase())
                != Types.DATE) {
            throw new SQLException();
        }
        if (outputRows.get(cursor).get(columnIndex)
                == null) {
            return null;
        }
        try {
            final String value
                    = outputRows.get(cursor).get(columnIndex);
            final SimpleDateFormat dateFormat
                    = new SimpleDateFormat(DATE_FORMAT);
            dateFormat.setLenient(false);
            final java.util.Date date = dateFormat.parse(
                    value.replaceAll("\\s", "")
                            .replaceAll("['\"]", ""));
            return new Date(date.getTime());
        } catch (final ParseException e) {
            throw new SQLException();
        }
    }

    @Override
    public Timestamp getTimestamp(int columnIndex)
            throws SQLException {

        if (isClosed) {
            throw new SQLException();
        }

        if (columnIndex < 1 || columnIndex
                > columnNames.size()
                || !checkCursor()) {
            throw new SQLException();
        }
        columnIndex--;
        final String columnLabel
                = columnNames.get(columnIndex).toUpperCase();
        if (columnTypes.get(columnLabel.toUpperCase())
                == null) {
            throw new SQLException();
        }
        if (columnTypes.get(columnLabel)
                != Types.TIMESTAMP) {
            throw new SQLException();
        }
        if (outputRows.get(cursor).get(columnIndex)
                == null) {
            return null;
        }

        try {
            final String value
                    = outputRows.get(cursor).get(columnIndex);
            final SimpleDateFormat dateFormat
                    = new SimpleDateFormat(DATE_TIME_FORMAT);
            dateFormat.setLenient(false);
            final java.util.Date date = dateFormat.parse(
                    value.replaceAll("\\s", "")
                            .replaceAll("['\"]", ""));
            return new Timestamp(date.getTime());
        } catch (final ParseException e) {
            throw new SQLException();
        }
    }

    @Override
    public Timestamp getTimestamp(final String columnLabel)
            throws SQLException {

        if (isClosed) {
            throw new SQLException();
        }
        if (columnLabel == null) {
            throw new SQLException();
        }
        int columnIndex = findColumn(columnLabel);
        if (columnIndex < 1 || columnIndex
                > columnNames.size()
                || !checkCursor()) {
            throw new SQLException();
        }
        columnIndex--;
        if (columnTypes.get(columnLabel.toUpperCase())
                == null) {
            throw new SQLException();
        }
        if (columnTypes.get(columnLabel.toUpperCase())
                != Types.TIMESTAMP) {
            throw new SQLException();
        }
        if (outputRows.get(cursor).get(columnIndex)
                == null) {
            return null;
        }
        try {
            final String value
                    = outputRows.get(cursor).get(columnIndex);
            final SimpleDateFormat dateFormat
                    = new SimpleDateFormat(DATE_TIME_FORMAT);
            dateFormat.setLenient(false);
            final java.util.Date date = dateFormat.parse(
                    value.replaceAll("\\s", "")
                            .replaceAll("['\"]", ""));
            return new Timestamp(date.getTime());
        } catch (final ParseException e) {
            throw new SQLException();
        }
    }


    @Override
    public float getFloat(int columnIndex)
            throws SQLException {

        if (isClosed) {
            throw new SQLException();
        }

        if (columnIndex < 1 || columnIndex
                > columnNames.size()
                || !checkCursor()) {
            throw new SQLException();
        }
        columnIndex--;
        final String columnName
                = columnNames.get(columnIndex).toUpperCase();
        if (columnTypes.get(columnName.toUpperCase())
                == null) {
            throw new SQLException();
        }
        if (columnTypes.get(columnName)
                != Types.FLOAT) {
            throw new SQLException();
        }
        if (outputRows.get(cursor).get(columnIndex)
                == null) {
            return 0;
        }

        try {
            final float output
                    = Float.parseFloat(outputRows.
                    get(cursor).get(columnIndex));
            return output;
        } catch (final Exception e) {
            throw new SQLException();
        }
    }

    @Override
    public float getFloat(final String columnLabel)
            throws SQLException {

        if (isClosed) {
            throw new SQLException();
        }
        if (columnLabel == null) {
            throw new SQLException();
        }
        int columnIndex = findColumn(columnLabel);
        if (columnIndex < 1 || columnIndex
                > columnNames.size()
                || !checkCursor()) {
            throw new SQLException();
        }
        columnIndex--;
        if (columnTypes.get(columnLabel.toUpperCase())
                == null) {
            throw new SQLException();
        }
        if (columnTypes.get(columnLabel.toUpperCase())
                != Types.FLOAT) {
            throw new SQLException();
        }
        if (outputRows.get(cursor).get(columnIndex)
                == null) {
            return 0;
        }
        try {
            final float output
                    = Float.parseFloat(outputRows.get(
                    cursor).get(columnIndex));
            return output;
        } catch (final Exception e) {
            throw new SQLException();
        }
    }

    @Override
    public int getInt(int columnIndex)
            throws SQLException {
        if (isClosed) {
            throw new SQLException();
        }

        if (columnIndex < 1 || columnIndex
                > columnNames.size()
                || !checkCursor()) {
            throw new SQLException();
        }
        columnIndex--;
        final String columnName
                = columnNames.get(columnIndex).toUpperCase();
        if (columnTypes.get(columnName.toUpperCase())
                == null) {
            throw new SQLException();
        }
        if (columnTypes.get(columnName)
                != Types.INTEGER) {
            throw new SQLException();
        }
        if (outputRows.get(cursor).get(columnIndex)
                == null) {
            return 0;
        }
        try {
            final int output
                    = Integer.parseInt(outputRows.get(
                    cursor).get(columnIndex));
            return output;
        } catch (final Exception e) {
            throw new SQLException();
        }
    }

    @Override
    public int getInt(final String columnLabel)
            throws SQLException {
        if (isClosed) {
            throw new SQLException();
        }
        if (columnLabel == null) {
            throw new SQLException();
        }
        int columnIndex = findColumn(columnLabel);
        if (columnIndex < 1 || columnIndex
                > columnNames.size()
                || !checkCursor()) {
            throw new SQLException();
        }
        columnIndex--;
        if (columnTypes.get(columnLabel.toUpperCase())
                == null) {
            throw new SQLException();
        }
        if (columnTypes.get(columnLabel.toUpperCase())
                != Types.INTEGER) {
            throw new SQLException();
        }
        if (outputRows.get(cursor).get(columnIndex)
                == null) {
            return 0;
        }
        try {
            final int output
                    = Integer.parseInt(outputRows.get(
                    cursor).get(columnIndex));
            return output;
        } catch (final Exception e) {
            throw new SQLException();
        }
    }

    @Override
    public String getString(int columnIndex)
            throws SQLException {
        if (isClosed) {
            throw new SQLException();
        }

        if (columnIndex < 1 || columnIndex
                > columnNames.size()
                || !checkCursor()) {
            throw new SQLException();
        }
        columnIndex--;
        final String columnName
                = columnNames.get(
                columnIndex).toUpperCase();
        if (columnTypes.get(columnName.toUpperCase())
                == null) {
            throw new SQLException();
        }
        if (columnTypes.get(columnName)
                != Types.VARCHAR) {
            throw new SQLException();
        }
        if (outputRows.get(cursor).get(columnIndex)
                == null) {
            return null;
        }
        return outputRows.get(cursor
        ).get(columnIndex);
    }

    @Override
    public String getString(final String columnLabel)
            throws SQLException {
        if (isClosed) {
            throw new SQLException();
        }
        if (columnLabel == null) {
            throw new SQLException();
        }
        int columnIndex = findColumn(columnLabel);
        if (columnIndex < 1 || columnIndex
                > columnNames.size()
                || !checkCursor()) {
            throw new SQLException();
        }
        columnIndex--;
        if (columnTypes.get(columnLabel.toUpperCase())
                == null) {
            throw new SQLException();
        }
        if (columnTypes.get(columnLabel.toUpperCase())
                != Types.VARCHAR) {
            throw new SQLException();
        }
        if (outputRows.get(cursor).get(columnIndex)
                == null) {
            return null;
        }
        return outputRows.get(cursor
        ).get(columnIndex);
    }

    @Override
    public Object getObject(final int columnIndex)
            throws SQLException {
        if (isClosed) {
            throw new SQLException();
        }

        if (columnIndex < 1 || columnIndex
                > columnNames.size()
                || !checkCursor()) {
            throw new SQLException();
        }
        if (outputRows.get(cursor).get(
                columnIndex - 1) == null) {
            return null;
        }
        final int type
                = metaData.getColumnType(columnIndex);
        switch (type) {
            case Types.VARCHAR:
                return getString(columnIndex);
            case Types.INTEGER:
                return getInt(columnIndex);
            case Types.FLOAT:
                return getFloat(columnIndex);
            case Types.DOUBLE:
                return getDouble(columnIndex);
            case Types.BIGINT:
                return getLong(columnIndex);
            case Types.DATE:
                return getDate(columnIndex);
            case Types.TIMESTAMP:
                return getTimestamp(columnIndex);
            default:
                throw new SQLException();
        }
    }

    @Override
    public ResultSetMetaData getMetaData()
            throws SQLException {
        if (isClosed) {
            throw new SQLException();
        }
        return metaData;
    }

    @Override
    public Statement getStatement()
            throws SQLException {
        if (isClosed) {
            throw new SQLException();
        }
        return DBStatement;
    }

    @Override
    public boolean isAfterLast()
            throws SQLException {

        if (isClosed) {
            throw new SQLException();
        }
        return (outputRows.size() > 0)
                && (cursor == outputRows.size());
    }

    @Override
    public boolean isBeforeFirst()
            throws SQLException {

        if (isClosed) {
            throw new SQLException();
        }
        return (outputRows.size() > 0)
                && (cursor == -1);
    }

    @Override
    public boolean isClosed()
            throws SQLException {
        return isClosed;
    }

    @Override
    public boolean isFirst()
            throws SQLException {

        if (isClosed) {
            throw new SQLException();
        }
        return (outputRows.size() > 0)
                && (cursor == 0);
    }

    @Override
    public boolean isLast() throws SQLException {

        if (isClosed) {
            throw new SQLException();
        }

        return (outputRows.size() > 0)
                && (cursor == outputRows.size() - 1);
    }

    @Override
    public boolean last() throws SQLException {

        if (isClosed) {
            throw new SQLException();
        }

        if (outputRows.size() > 0) {
            cursor = outputRows.size() - 1;
            return true;
        }
        return false;
    }

    @Override
    public boolean next() throws SQLException {

        if (isClosed) {
            throw new SQLException();
        }

        if (outputRows.size() == 0 || isAfterLast()) {
            return false;
        }

        if (isLast()) {
            cursor++;
            return false;
        }
        cursor++;
        return true;
    }

    @Override
    public boolean previous() throws SQLException {

        if (isClosed) {
            throw new SQLException();
        }

        if (outputRows.size() == 0 || isBeforeFirst()) {
            return false;
        }
        if (isFirst()) {
            cursor--;
            return false;
        }
        cursor--;
        return true;
    }

    @Override
    public long getLong(int columnIndex)
            throws SQLException {
        if (isClosed) {
            throw new SQLException();
        }

        if (columnIndex < 1 || columnIndex
                > columnNames.size()
                || !checkCursor()) {
            throw new SQLException();
        }
        columnIndex--;
        final String columnName
                = columnNames.get(columnIndex).toUpperCase();
        if (columnTypes.get(columnName.toUpperCase())
                == null) {
            throw new SQLException();
        }
        if (columnTypes.get(columnName)
                != Types.BIGINT) {
            throw new SQLException();
        }
        if (outputRows.get(cursor).get(columnIndex)
                == null) {
            return 0;
        }
        try {
            final long output
                    = Long.parseLong(outputRows.get(
                    cursor).get(columnIndex));
            return output;
        } catch (final Exception e) {
            throw new SQLException();
        }
    }

    @Override
    public long getLong(final String columnLabel)
            throws SQLException {
        if (isClosed) {
            throw new SQLException();
        }
        if (columnLabel == null) {
            throw new SQLException();
        }
        int columnIndex = findColumn(columnLabel);
        if (columnIndex < 1 || columnIndex
                > columnNames.size()
                || !checkCursor()) {
            throw new SQLException();
        }
        columnIndex--;
        if (columnTypes.get(columnLabel.toUpperCase())
                == null) {
            throw new SQLException();
        }
        if (columnTypes.get(columnLabel.toUpperCase())
                != Types.BIGINT) {
            throw new SQLException();
        }
        if (outputRows.get(cursor).get(columnIndex)
                == null) {
            return 0;
        }
        try {
            final long output
                    = Long.parseLong(outputRows.get(
                    cursor).get(columnIndex));
            return output;
        } catch (final Exception e) {
            throw new SQLException();
        }
    }

    @Override
    public double getDouble(int columnIndex)
            throws SQLException {
        if (isClosed) {
            throw new SQLException();
        }

        if (columnIndex < 1 || columnIndex
                > columnNames.size()
                || !checkCursor()) {
            throw new SQLException();
        }
        columnIndex--;
        final String columnName
                = columnNames.get(
                columnIndex).toUpperCase();
        if (columnTypes.get(columnName.toUpperCase())
                == null) {
            throw new SQLException();
        }
        if (columnTypes.get(columnName)
                != Types.DOUBLE) {
            throw new SQLException();
        }
        if (outputRows.get(cursor).get(columnIndex)
                == null) {
            return 0;
        }
        try {
            final double output
                    = Double.parseDouble(outputRows.get(
                    cursor).get(columnIndex));
            return output;
        } catch (final Exception e) {
            throw new SQLException();
        }
    }

    @Override
    public double getDouble(final String columnLabel)
            throws SQLException {
        if (isClosed) {
            throw new SQLException();
        }
        if (columnLabel == null) {
            throw new SQLException();
        }
        int columnIndex = findColumn(columnLabel);
        if (columnIndex < 1 || columnIndex
                > columnNames.size()
                || !checkCursor()) {
            throw new SQLException();
        }
        columnIndex--;
        if (columnTypes.get(columnLabel.toUpperCase())
                == null) {
            throw new SQLException();
        }
        if (columnTypes.get(columnLabel.toUpperCase())
                != Types.DOUBLE) {
            throw new SQLException();
        }
        if (outputRows.get(cursor).get(columnIndex)
                == null) {
            return 0;
        }
        try {
            final double output
                    = Double.parseDouble(outputRows.get(
                    cursor).get(columnIndex));
            return output;
        } catch (final Exception e) {
            throw new SQLException();
        }
    }

    @Override
    public boolean isWrapperFor(final
                                Class<?> arg0) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T unwrap(final Class<T> arg0)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void cancelRowUpdates()
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clearWarnings()
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteRow()
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Array getArray(final int arg0)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Array getArray(final String arg0)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public InputStream getAsciiStream(final
                                      int arg0) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public InputStream getAsciiStream(final
                                      String arg0) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public BigDecimal getBigDecimal(final
                                    int arg0) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public BigDecimal getBigDecimal(final
                                    String arg0) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public BigDecimal getBigDecimal(final int
                                            arg0, final int arg1) throws
            SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public BigDecimal getBigDecimal(final String
                                            arg0, final int arg1) throws
            SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public InputStream getBinaryStream(final int
                                               arg0) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public InputStream getBinaryStream(final String
                                               arg0) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Blob getBlob(final int arg0)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Blob getBlob(final String arg0)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getBoolean(final int arg0)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getBoolean(final String arg0)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte getByte(final int arg0)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte getByte(final String arg0)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte[] getBytes(final int arg0)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte[] getBytes(final String arg0)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Reader getCharacterStream(final
                                     int arg0) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Reader getCharacterStream(final
                                     String arg0) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Clob getClob(final int arg0)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Clob getClob(final String arg0)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getConcurrency()
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getCursorName()
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Date getDate(final int arg0,
                        final Calendar arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Date getDate(final String arg0,
                        final Calendar arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getFetchDirection()
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getFetchSize()
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getHoldability()
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Reader getNCharacterStream(final
                                      int arg0) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Reader getNCharacterStream(final
                                      String arg0) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public NClob getNClob(final int arg0)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public NClob getNClob(final String
                                  arg0) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getNString(final int
                                     arg0) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getNString(final String
                                     arg0) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getObject(final String
                                    arg0) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getObject(final int
                                    arg0, final
                            Map<String, Class<?>> arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getObject(final String
                                    arg0, final
                            Map<String, Class<?>> arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T getObject(final int
                                   arg0, final Class<T>
                                   arg1) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T getObject(final
                           String arg0, final
                           Class<T> arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Ref getRef(final int
                              arg0) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Ref getRef(final String
                              arg0) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public RowId getRowId(final int
                                  arg0) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public RowId getRowId(final String
                                  arg0) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public SQLXML getSQLXML(final int
                                    arg0) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public SQLXML getSQLXML(final String
                                    arg0) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public short getShort(final int
                                  arg0) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public short getShort(final String
                                  arg0) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Time getTime(final int
                                arg0) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Time getTime(final String
                                arg0) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Time getTime(final int arg0,
                        final Calendar arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Time getTime(final String
                                arg0, final Calendar arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getType()
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public URL getURL(final
                      int arg0) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public URL getURL(final String
                              arg0) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public InputStream getUnicodeStream(final
                                        int arg0) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public InputStream getUnicodeStream(final
                                        String arg0) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public SQLWarning getWarnings()
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void insertRow()
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void moveToCurrentRow()
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void moveToInsertRow()
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void refreshRow()
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean relative(final
                            int arg0) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean rowDeleted()
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean rowInserted()
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean rowUpdated()
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setFetchDirection(final
                                  int arg0) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setFetchSize(final
                             int arg0) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateArray(final int
                                    arg0, final Array
                                    arg1) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateArray(final
                            String arg0, final
                            Array arg1) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateAsciiStream(final int
                                          arg0, final InputStream
                                          arg1) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateAsciiStream(final String
                                          arg0, final InputStream arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateAsciiStream(final int
                                          arg0, final InputStream
                                          arg1, final int arg2)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateAsciiStream(final
                                  String arg0, final
                                  InputStream arg1,
                                  final int arg2)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateAsciiStream(
            final int arg0, final
    InputStream arg1, final
            long arg2) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateAsciiStream(
            final String arg0,
            final InputStream arg1,
            final long arg2)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateBigDecimal(final
                                 int arg0, final
                                 BigDecimal arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateBigDecimal(final
                                 String arg0, final
                                 BigDecimal arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateBinaryStream(
            final int arg0, final
    InputStream arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateBinaryStream(final
                                   String arg0, final
                                   InputStream arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateBinaryStream(
            final int arg0, final
    InputStream arg1, final int
                    arg2) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateBinaryStream(
            final String arg0, final
    InputStream arg1, final
            int arg2) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateBinaryStream(final
                                   int arg0, final
                                   InputStream arg1, final
                                   long arg2) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateBinaryStream(final
                                   String arg0, final InputStream
                                           arg1, final long arg2)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateBlob(final int
                                   arg0, final Blob arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateBlob(final String
                                   arg0, final Blob arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateBlob(final int
                                   arg0, final InputStream
                                   arg1) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateBlob(final String
                                   arg0, final InputStream arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateBlob(final int
                                   arg0, final InputStream
                                   arg1, final long arg2)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateBlob(final
                           String arg0, final
                           InputStream arg1,
                           final long arg2)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateBoolean(final
                              int arg0, final
                              boolean arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateBoolean(final
                              String arg0, final
                              boolean arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateByte(final
                           int arg0, final
                           byte arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateByte(final
                           String arg0, final
                           byte arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateBytes(final
                            int arg0, final
                            byte[] arg1) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateBytes(final
                            String arg0,
                            final byte[] arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateCharacterStream(
            final int arg0,
            final Reader arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateCharacterStream(
            final String arg0,
            final Reader arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateCharacterStream(
            final int arg0,
            final Reader arg1,
            final int arg2)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateCharacterStream(
            final String arg0,
            final Reader arg1,
            final int arg2)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateCharacterStream(
            final int arg0,
            final Reader arg1,
            final long arg2)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateCharacterStream(
            final String arg0,
            final Reader arg1,
            final long arg2)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateClob(final int
                                   arg0, final Clob arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateClob(final
                           String arg0, final
                           Clob arg1) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateClob(final int
                                   arg0, final Reader arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateClob(final String
                                   arg0, final Reader arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateClob(final int
                                   arg0, final Reader
                                   arg1, final long
                                   arg2) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateClob(final String
                                   arg0, final Reader
                                   arg1, final long
                                   arg2) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateDate(final int
                                   arg0, final Date
                                   arg1) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateDate(final String
                                   arg0, final Date arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateDouble(final int
                                     arg0, final double arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateDouble(final String
                                     arg0, final double arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateFloat(final int
                                    arg0, final float arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateFloat(final String
                                    arg0, final float arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateInt(final int
                                  arg0, final int arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateInt(final String
                                  arg0, final int arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateLong(final int
                                   arg0, final long arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateLong(final String
                                   arg0, final long arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateNCharacterStream(
            final int arg0, final
    Reader arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateNCharacterStream(
            final String arg0, final
    Reader arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateNCharacterStream(
            final int arg0, final
    Reader arg1, final long
                    arg2) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateNCharacterStream(
            final String arg0, final
    Reader arg1, final long arg2)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateNClob(final int
                                    arg0, final NClob arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateNClob(final String
                                    arg0, final NClob arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateNClob(final int
                                    arg0, final Reader arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateNClob(final String
                                    arg0, final Reader arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateNClob(final int arg0,
                            final Reader arg1,
                            final long arg2)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateNClob(final String
                                    arg0, final Reader arg1,
                            final long arg2)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateNString(final int
                                      arg0, final String arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateNString(final String
                                      arg0, final String arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateNull(final int
                                   arg0) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateNull(final String
                                   arg0) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateObject(final int
                                     arg0, final Object arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateObject(final String
                                     arg0, final Object arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateObject(final int
                                     arg0, final Object arg1,
                             final int arg2)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateObject(final
                             String arg0, final
                             Object arg1, final
                             int arg2)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateRef(final
                          int arg0, final
                          Ref arg1) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateRef(final
                          String arg0, final
                          Ref arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateRow()
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateRowId(
            final int arg0,
            final RowId arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateRowId(final
                            String arg0, final
                            RowId arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateSQLXML(final int
                                     arg0, final SQLXML arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateSQLXML(final
                             String arg0, final SQLXML
                                     arg1) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateShort(final int
                                    arg0, final short arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateShort(final
                            String arg0, final
                            short arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateString(final
                             int arg0, final String
                                     arg1) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateString(final
                             String arg0, final
                             String arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateTime(final int
                                   arg0, final Time arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateTime(final
                           String arg0, final
                           Time arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateTimestamp(
            final int arg0,
            final Timestamp arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateTimestamp(final
                                String arg0,
                                final Timestamp
                                        arg1) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean wasNull()
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Timestamp getTimestamp(
            final int arg0,
            final Calendar arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Timestamp getTimestamp(
            final String arg0,
            final Calendar arg1)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

}
