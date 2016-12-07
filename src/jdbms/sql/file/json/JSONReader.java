package jdbms.sql.file.json;

import jdbms.sql.data.Table;
import jdbms.sql.exceptions.ColumnAlreadyExistsException;
import jdbms.sql.exceptions.ColumnListTooLargeException;
import jdbms.sql.exceptions.ColumnNotFoundException;
import jdbms.sql.exceptions.RepeatedColumnException;
import jdbms.sql.exceptions.TypeMismatchException;
import jdbms.sql.exceptions.ValueListTooLargeException;
import jdbms.sql.exceptions.ValueListTooSmallException;
import jdbms.sql.file.FileReader;

public class JSONReader implements FileReader {

	public JSONReader() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Table parse(final String tableName, final String databaseName, final String path)
			throws ColumnAlreadyExistsException,
			RepeatedColumnException,
			ColumnListTooLargeException,
			ColumnNotFoundException,
			ValueListTooLargeException,
			ValueListTooSmallException,
			TypeMismatchException {
		// TODO Auto-generated method stub
		return null;
	}

}
