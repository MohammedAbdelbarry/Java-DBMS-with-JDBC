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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class DataResultSet implements ResultSet {

	private ArrayList<String> columns;
	private ArrayList<ArrayList<String>> outputRows;
	int cursor;

	public DataResultSet() {
		// TODO Auto-generated constructor stub
		cursor = 0;
	}

	@Override
	public boolean isWrapperFor(final Class<?> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T unwrap(final Class<T> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean absolute(final int arg0) throws SQLException {
		// TODO Auto-generated method stub
		if (Math.abs(arg0) >= 1 && Math.abs(arg0) <= outputRows.size()) {
			// The Required Integer is valid
			if (arg0 > 0) {
				// POSITIVE
				cursor = arg0;
			} else {
				// NEGATIVE
				cursor = arg0 + outputRows.size();
			}
			return true;
		} else {
			// The Required Integer is invalid
			if (arg0 <= 0) {
				beforeFirst();
			} else {
				afterLast();
			}
			return false;
		}

	}

	@Override
	public void afterLast() throws SQLException {
		// TODO Auto-generated method stub
		if (outputRows.size() > 0) {
			cursor = outputRows.size() + 1;
		}
	}

	@Override
	public void beforeFirst() throws SQLException {
		// TODO Auto-generated method stub
		if (outputRows.size() > 0) {
			cursor = 0;
		}
	}

	@Override
	public void cancelRowUpdates() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearWarnings() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteRow() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public int findColumn(final String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean first() throws SQLException {
		// TODO Auto-generated method stub
		if (outputRows.size() > 0) {
			cursor = 0;
			return true;
		}
		return false;
	}

	@Override
	public Array getArray(final int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Array getArray(final String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getAsciiStream(final int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getAsciiStream(final String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getBigDecimal(final int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getBigDecimal(final String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getBigDecimal(final int arg0, final int arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getBigDecimal(final String arg0, final int arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getBinaryStream(final int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getBinaryStream(final String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Blob getBlob(final int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Blob getBlob(final String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getBoolean(final int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getBoolean(final String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public byte getByte(final int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte getByte(final String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte[] getBytes(final int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getBytes(final String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reader getCharacterStream(final int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reader getCharacterStream(final String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Clob getClob(final int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Clob getClob(final String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getConcurrency() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getCursorName() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getDate(final int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getDate(final String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getDate(final int arg0, final Calendar arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getDate(final String arg0, final Calendar arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getDouble(final int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getDouble(final String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getFetchDirection() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getFetchSize() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getFloat(final int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getFloat(final String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHoldability() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getInt(final int arg0) throws SQLException {
		// TODO Auto-generated method stub

		// Check Range
		if (arg0 < 1 || arg0 > columns.size()) {
			throw new SQLException();
		}

		// CHECK TYPE


		// CHECK NULL

		final int output = Integer.parseInt(outputRows.get(cursor).get(arg0));
		return output;
	}

	@Override
	public int getInt(final String arg0) throws SQLException {
		// TODO Auto-generated method stub
		// Check Range
		if (!columns.contains(arg0)) {
			throw new SQLException();
		}

		// Get the value of the column...
		int arg1 = 0;
		for (int i = 0; i < columns.size(); i++) {
			if (columns.get(i).equals(arg0)) {
				arg1 = i;
			}
		}

		final int output = Integer.parseInt(outputRows.get(cursor).get(arg1));
		return output;
	}

	@Override
	public long getLong(final int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getLong(final String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ResultSetMetaData getMetaData() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reader getNCharacterStream(final int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reader getNCharacterStream(final String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NClob getNClob(final int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NClob getNClob(final String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNString(final int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNString(final String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getObject(final int arg0) throws SQLException {
		// TODO Auto-generated method stub

		// Check Range
		if (arg0 < 1 || arg0 > columns.size()) {
			throw new SQLException();
		}

		return outputRows.get(cursor).get(arg0); // check if null...
	}

	@Override
	public Object getObject(final String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getObject(final int arg0, final Map<String, Class<?>> arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getObject(final String arg0, final Map<String, Class<?>> arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T getObject(final int arg0, final Class<T> arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T getObject(final String arg0, final Class<T> arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ref getRef(final int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ref getRef(final String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getRow() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public RowId getRowId(final int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RowId getRowId(final String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLXML getSQLXML(final int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLXML getSQLXML(final String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public short getShort(final int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public short getShort(final String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Statement getStatement() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getString(final int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getString(final String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Time getTime(final int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Time getTime(final String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Time getTime(final int arg0, final Calendar arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Time getTime(final String arg0, final Calendar arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timestamp getTimestamp(final int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timestamp getTimestamp(final String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timestamp getTimestamp(final int arg0, final Calendar arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timestamp getTimestamp(final String arg0, final Calendar arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getType() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public URL getURL(final int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URL getURL(final String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getUnicodeStream(final int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getUnicodeStream(final String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertRow() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isAfterLast() throws SQLException {
		// TODO Auto-generated method stub
		return (outputRows.size() > 0) && (cursor == outputRows.size() + 1);
	}

	@Override
	public boolean isBeforeFirst() throws SQLException {
		// TODO Auto-generated method stub
		return (outputRows.size() > 0) && (cursor == 0);
	}

	@Override
	public boolean isClosed() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isFirst() throws SQLException {
		// TODO Auto-generated method stub
		return (outputRows.size() > 0) && (cursor == 1);
	}

	@Override
	public boolean isLast() throws SQLException {
		// TODO Auto-generated method stub
		return (outputRows.size() > 0) && (cursor == outputRows.size());
	}

	@Override
	public boolean last() throws SQLException {
		// TODO Auto-generated method stub
		final int numberOfRows = outputRows.size();
		if (numberOfRows > 0) {
			cursor = numberOfRows;
			return true;
		}
		return false;
	}

	@Override
	public void moveToCurrentRow() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void moveToInsertRow() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean next() throws SQLException {
		// TODO Auto-generated method stub

		// Empty Table
		if (outputRows.size() == 0) {
			return false;
		}

		if (isAfterLast()) {
			return false;
		}
		cursor++;
		return !isAfterLast();
	}

	@Override
	public boolean previous() throws SQLException {
		// TODO Auto-generated method stub

		// Empty Table
		if (outputRows.size() == 0) {
			return false;
		}

		if (isBeforeFirst()) {
			return false;
		}
		cursor--;
		return !isBeforeFirst();
	}

	@Override
	public void refreshRow() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean relative(final int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rowDeleted() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rowInserted() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rowUpdated() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setFetchDirection(final int arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFetchSize(final int arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateArray(final int arg0, final Array arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateArray(final String arg0, final Array arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAsciiStream(final int arg0, final InputStream arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAsciiStream(final String arg0, final InputStream arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAsciiStream(final int arg0, final InputStream arg1, final int arg2) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAsciiStream(final String arg0, final InputStream arg1, final int arg2) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAsciiStream(final int arg0, final InputStream arg1, final long arg2) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAsciiStream(final String arg0, final InputStream arg1, final long arg2) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBigDecimal(final int arg0, final BigDecimal arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBigDecimal(final String arg0, final BigDecimal arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBinaryStream(final int arg0, final InputStream arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBinaryStream(final String arg0, final InputStream arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBinaryStream(final int arg0, final InputStream arg1, final int arg2) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBinaryStream(final String arg0, final InputStream arg1, final int arg2) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBinaryStream(final int arg0, final InputStream arg1, final long arg2) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBinaryStream(final String arg0, final InputStream arg1, final long arg2) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBlob(final int arg0, final Blob arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBlob(final String arg0, final Blob arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBlob(final int arg0, final InputStream arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBlob(final String arg0, final InputStream arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBlob(final int arg0, final InputStream arg1, final long arg2) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBlob(final String arg0, final InputStream arg1, final long arg2) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBoolean(final int arg0, final boolean arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBoolean(final String arg0, final boolean arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateByte(final int arg0, final byte arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateByte(final String arg0, final byte arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBytes(final int arg0, final byte[] arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBytes(final String arg0, final byte[] arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateCharacterStream(final int arg0, final Reader arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateCharacterStream(final String arg0, final Reader arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateCharacterStream(final int arg0, final Reader arg1, final int arg2) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateCharacterStream(final String arg0, final Reader arg1, final int arg2) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateCharacterStream(final int arg0, final Reader arg1, final long arg2) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateCharacterStream(final String arg0, final Reader arg1, final long arg2) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateClob(final int arg0, final Clob arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateClob(final String arg0, final Clob arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateClob(final int arg0, final Reader arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateClob(final String arg0, final Reader arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateClob(final int arg0, final Reader arg1, final long arg2) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateClob(final String arg0, final Reader arg1, final long arg2) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateDate(final int arg0, final Date arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateDate(final String arg0, final Date arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateDouble(final int arg0, final double arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateDouble(final String arg0, final double arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateFloat(final int arg0, final float arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateFloat(final String arg0, final float arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateInt(final int arg0, final int arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateInt(final String arg0, final int arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateLong(final int arg0, final long arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateLong(final String arg0, final long arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNCharacterStream(final int arg0, final Reader arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNCharacterStream(final String arg0, final Reader arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNCharacterStream(final int arg0, final Reader arg1, final long arg2) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNCharacterStream(final String arg0, final Reader arg1, final long arg2) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNClob(final int arg0, final NClob arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNClob(final String arg0, final NClob arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNClob(final int arg0, final Reader arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNClob(final String arg0, final Reader arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNClob(final int arg0, final Reader arg1, final long arg2) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNClob(final String arg0, final Reader arg1, final long arg2) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNString(final int arg0, final String arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNString(final String arg0, final String arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNull(final int arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNull(final String arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateObject(final int arg0, final Object arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateObject(final String arg0, final Object arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateObject(final int arg0, final Object arg1, final int arg2) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateObject(final String arg0, final Object arg1, final int arg2) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateRef(final int arg0, final Ref arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateRef(final String arg0, final Ref arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateRow() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateRowId(final int arg0, final RowId arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateRowId(final String arg0, final RowId arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateSQLXML(final int arg0, final SQLXML arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateSQLXML(final String arg0, final SQLXML arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateShort(final int arg0, final short arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateShort(final String arg0, final short arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateString(final int arg0, final String arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateString(final String arg0, final String arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateTime(final int arg0, final Time arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateTime(final String arg0, final Time arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateTimestamp(final int arg0, final Timestamp arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateTimestamp(final String arg0, final Timestamp arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean wasNull() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}
}
