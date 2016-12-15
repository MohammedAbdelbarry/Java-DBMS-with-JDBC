package jdbc.main;

import java.io.File;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

import jdbms.sql.data.query.PrettyPrinter;
import jdbms.sql.errors.ErrorHandler;
import jdbms.sql.parsing.expressions.util.StringModifier;
import jdbms.sql.parsing.parser.ParserMain;
import jdbms.sql.util.HelperClass;

public class JDBCMain {
	private static final String DATA_DIRECTORY
	= "Data";
	private static final String AS_NULL = "";
	static {
		try {
			Class.forName("jdbc.drivers.DBDriver");
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	private static final String QUIT = "QUIT;";

	public static void main(final String[] args) {
		HelperClass.registerInitialStatements();
		String path = null;
		try {
			final CodeSource codeSource = ParserMain.class.
					getProtectionDomain().getCodeSource();
			final File jarFile = new File(
					codeSource.getLocation().toURI().getPath());
			path = jarFile.getParentFile().getPath()
					+ File.separator + DATA_DIRECTORY;
		} catch (final URISyntaxException e) {

		}
		Driver driver = null;
		boolean driverInitialized = false;
		String url = null;
		final Scanner in = new Scanner(System.in);
		while (!driverInitialized) {
			System.out.printf("Enter Your Prefered Back-End Parser:\n"
					+ "xmldb for XML and altdb for JSON\n");
			final String backendParser = in.nextLine();
			url = "jdbc:" + backendParser
					+ "://localhost";
			try {

				driver = DriverManager.getDriver(url);
				driverInitialized = true;
			} catch (final SQLException e) {
				System.out.println("The name of the backendParser was wrong");
			}
		}
		final Properties info = new Properties();
		info.put("path", new File(path).getAbsoluteFile());
		Connection connection = null;
		try {
			connection = driver.connect(url, info);
		} catch (final SQLException e) {
			//print failed to connect
		}
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (final SQLException e1) {
			//print internal error
		}
		while (true) {
			System.out.printf("sql> ");
			final StringBuilder stringBuilder = new StringBuilder();
			String sql = null;
			boolean invalid = false;
			final StringModifier modifier = new StringModifier();

			while (in.hasNextLine()) {
				stringBuilder.append(in.nextLine());
				String modifiedExpression = null;
				try {
					modifiedExpression = modifier.
							modifyString(stringBuilder.toString()).trim();
					if (modifiedExpression.indexOf(";") != -1) {
						sql = stringBuilder.substring(0,
								modifiedExpression.indexOf(";") + 1);
						break;
					}
				} catch (final IndexOutOfBoundsException e) {
					invalid = true;
					break;
				}
			}
			if (sql.equalsIgnoreCase(QUIT)) {
				try {
					connection.close();
					break;
				} catch (final SQLException e) {
					break;
				}
			}
			if (invalid) {
				ErrorHandler.printSyntaxError();
				continue;
			}
			try {
				if (statement.execute(sql)) {
					System.out.println("Query Completed Successfully");
					final ResultSet resultSet = statement.getResultSet();
					printResultSet(resultSet);
					resultSet.close();
				} else {
					if (statement.getUpdateCount() != -1) {
						System.out.printf("Updated %d Rows Successfully\n",
								statement.getUpdateCount());
					} else {
						System.out.println("Query Completed Successfully");
					}
				}
			} catch (final SQLException e) {
				printError(e.getMessage());
			}
		}
		in.close();
	}
	private static void printError(final String error) {
		System.out.println("Error: " + error);
	}
	private static void printResultSet(final ResultSet resultSet) {
		try {
			final PrettyPrinter printer = new PrettyPrinter(System.out, AS_NULL);
			final int columnCount = resultSet.getMetaData().getColumnCount();
			final int rowCount = getRowCount(resultSet);
			final String[][] output = new String[rowCount + 1][columnCount];
			final String[] columns = new String[columnCount];
			for (int i = 1 ; i <= columnCount ; i++) {
				columns[i - 1] = resultSet.getMetaData().getColumnName(i);
			}
			output[0] = columns;
			for (int i = 1 ; i <= rowCount ; i++) {
				resultSet.next();
				final String[] row = new String[columnCount];
				for (int j = 1 ; j <= columnCount ; j++) {
					final Object cell = resultSet.
							getObject(resultSet.findColumn(columns[j - 1]));
					if (cell == null) {
						row[j - 1] = null;
					} else {
						row[j - 1] = cell.toString();
					}
				}
				output[i] = row;
			}
			printer.print(output);
		} catch (final SQLException e) {
			printError("Encountered an Unexpected Error While Printing");
		}
	}
	private static int getRowCount(final ResultSet resultSet) {
		try {
			if (resultSet.last()) {
				final int result = resultSet.getRow();
				resultSet.beforeFirst();
				return result;
			} else {
				return 0;
			}

		} catch (final SQLException e) {
			printError("Error Getting the Number of Rows");
		}
		return 0;
	}
}
