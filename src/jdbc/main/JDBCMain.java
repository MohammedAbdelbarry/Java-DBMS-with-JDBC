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

import jdbc.main.util.MainConfig;
import jdbms.sql.data.query.PrettyPrinter;
import jdbms.sql.parsing.expressions.util.StringModifier;
import jdbms.sql.parsing.parser.ParserMain;
import jdbms.sql.util.ClassRegisteringHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JDBCMain {
    private static final String AS_NULL = "";
    private static final String SYNTAX_ERROR
            = "Syntax Error";
    private static Logger logger;

    static {
        try {
            Class.forName("jdbc.drivers.DBDriver");
        } catch (final ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static final String QUIT = "QUIT;";

    public static void main(final String[] args) {
        logger = LogManager.getLogger(JDBCMain.class);
        logger.debug("Program Started");
        ClassRegisteringHelper.registerInitialStatements();
        String path;
        try {
            final CodeSource codeSource = JDBCMain.class.
                    getProtectionDomain().getCodeSource();
            final File jarFile = new File(
                    codeSource.getLocation().toURI().getPath());
            path = jarFile.getParentFile().getPath();
        } catch (final URISyntaxException e) {
            logger.fatal("Failed to get path", e);
            return;
        }
        Driver driver;
        String url;
        final Scanner in = new Scanner(System.in);
        final MainConfig mainConfig = new MainConfig();
        url = mainConfig.getString("url.prefix")
                + mainConfig.getString("protocol")
                + mainConfig.getString("url.suffix");
        try {
            driver = DriverManager.getDriver(url);
        } catch (final SQLException e) {
            logger.fatal("Failed to Load Driver,"
                    + " url(" + url + ")", e);
            System.out.println("Fatal Error, Failed to Load Driver");
            in.close();
            return;
        }
        final Properties info = new Properties();
        info.put("path", new File(path).getAbsoluteFile());
        Connection connection;
        try {
            connection = driver.connect(url, info);
        } catch (final SQLException e) {
            logger.fatal("Failed to Connect to Database, URL(" + url + "), "
                    + "Path(" + path + ")");
            printError("Failed to Connect to Database");
            return;
        }
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (final SQLException e1) {
            logger.fatal("Failed to create statement", e1);
            printError("Failed to Create Statement");
            return;
        }
        while (true) {
            System.out.printf("sql> ");
            final StringBuilder stringBuilder = new StringBuilder();
            String sql = null;
            boolean invalid = false;
            final StringModifier modifier = new StringModifier();
            while (in.hasNextLine()) {
                stringBuilder.append(in.nextLine());
                String modifiedExpression;
                try {
                    modifiedExpression = modifier.
                            modifyString(stringBuilder.toString()).trim();
                    if (modifiedExpression.contains(";")) {
                        sql = stringBuilder.substring(0,
                                modifiedExpression.indexOf(";") + 1);
                        break;
                    }
                } catch (final IndexOutOfBoundsException e) {
                    invalid = true;
                    break;
                }
            }
            long start = System.currentTimeMillis();
            if (sql.equalsIgnoreCase(QUIT)) {
                try {
                    connection.close();
                    break;
                } catch (final SQLException e) {
                    break;
                }
            }
            if (invalid) {
                printError(SYNTAX_ERROR);
                continue;
            }
            try {
                if (statement.execute(sql)) {
                    final ResultSet resultSet = statement.getResultSet();
                    long end = System.currentTimeMillis();
                    printResultSet(resultSet);
                    System.out.printf("Query Completed Successfully %d "
                                    + "Rows Were Returned\n",
                            getRowCount(resultSet));
                    System.out.printf("Execution Time: %dms\n", end - start);
                    resultSet.close();
                } else {
                    if (statement.getUpdateCount() != -1) {
                        long end = System.currentTimeMillis();
                        System.out.printf("Updated %d Rows Successfully\n",
                                statement.getUpdateCount());
                        System.out.printf("Execution Time: %dms\n", end -
                                start);
                    } else {
                        final ResultSet resultSet = statement.getResultSet();
                        long end = System.currentTimeMillis();
                        printResultSet(resultSet);
                        System.out.printf("Query Completed Successfully %d "
                                        + "Rows Were Returned\n",
                                getRowCount(resultSet));
                        System.out.printf("Execution Time: %dms\n", end -
                                start);
                        resultSet.close();
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
            final PrettyPrinter printer = new PrettyPrinter(System.out,
                    AS_NULL);
            final int columnCount = resultSet.getMetaData().getColumnCount();
            final int rowCount = getRowCount(resultSet);
            final String[][] output = new String[rowCount + 1][columnCount];
            final String[] columns = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columns[i - 1] = resultSet.getMetaData().getColumnName(i);
            }
            output[0] = columns;
            for (int i = 1; i <= rowCount; i++) {
                resultSet.next();
                final String[] row = new String[columnCount];
                for (int j = 1; j <= columnCount; j++) {
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
