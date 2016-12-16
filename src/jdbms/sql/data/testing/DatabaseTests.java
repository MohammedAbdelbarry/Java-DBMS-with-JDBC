package jdbms.sql.data.testing;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import jdbms.sql.data.ColumnIdentifier;
import jdbms.sql.data.SQLData;
import jdbms.sql.data.query.SelectQueryOutput;
import jdbms.sql.exceptions.AllColumnsDroppingException;
import jdbms.sql.exceptions.ColumnAlreadyExistsException;
import jdbms.sql.exceptions.ColumnListTooLargeException;
import jdbms.sql.exceptions.ColumnNotFoundException;
import jdbms.sql.exceptions.DatabaseAlreadyExistsException;
import jdbms.sql.exceptions.DatabaseNotFoundException;
import jdbms.sql.exceptions.FailedToDeleteDatabaseException;
import jdbms.sql.exceptions.FailedToDeleteTableException;
import jdbms.sql.exceptions.InvalidDateFormatException;
import jdbms.sql.exceptions.RepeatedColumnException;
import jdbms.sql.exceptions.TableAlreadyExistsException;
import jdbms.sql.exceptions.TableNotFoundException;
import jdbms.sql.exceptions.TypeMismatchException;
import jdbms.sql.exceptions.ValueListTooLargeException;
import jdbms.sql.exceptions.ValueListTooSmallException;
import jdbms.sql.parsing.properties.AddColumnParameters;
import jdbms.sql.parsing.properties.DatabaseCreationParameters;
import jdbms.sql.parsing.properties.DatabaseDroppingParameters;
import jdbms.sql.parsing.properties.DropColumnParameters;
import jdbms.sql.parsing.properties.InsertionParameters;
import jdbms.sql.parsing.properties.SelectionParameters;
import jdbms.sql.parsing.properties.TableCreationParameters;
import jdbms.sql.parsing.properties.TableDroppingParameters;
import jdbms.sql.parsing.properties.UseParameters;
import jdbms.sql.util.HelperClass;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DatabaseTests {

	private SQLData data;
	//private String protocol;
	@Before
	public void setUp() throws Exception {
		//protocol = "xmldb";
		data = new SQLData();
		HelperClass.registerInitialStatements();
	}
	@After
	public void cleanUp() {

	}

	@Test
	public void test1CreateDatabase() {
		try {
			final DatabaseCreationParameters createDBParameters = new DatabaseCreationParameters();
			createDBParameters.setDatabaseName("College");
			data.createDatabase(createDBParameters);
		} catch (final DatabaseAlreadyExistsException e) {
			Assert.fail("Failed to create database");
		}
	}

	@Test
	public void test2CreateExistingDatabase() {
		try {
			final DatabaseCreationParameters createDBParameters = new DatabaseCreationParameters();
			createDBParameters.setDatabaseName("College");
			data.createDatabase(createDBParameters);
			Assert.fail("Invalid Creation of an existing database ");
		} catch (final DatabaseAlreadyExistsException e) {


		} catch (final Exception e) {
			Assert.fail("Unexpected Exception was thrown");
		}
	}

	@Test
	public void test3CreateTable() {
		try {
			setActiveDatabase("college");
			final TableCreationParameters tableParamters = new TableCreationParameters();
			tableParamters.setColumnDefinitions(getColumns());
			tableParamters.setTableName("Students");
			data.createTable(tableParamters);
		} catch (ColumnAlreadyExistsException | TableAlreadyExistsException | InvalidDateFormatException
				| IOException e) {
			Assert.fail("Failed to create table");
		}
	}

	@Test
	public void test4CreateExistingTable() {
		try {
			setActiveDatabase("college");
			final TableCreationParameters tableParamters = new TableCreationParameters();
			tableParamters.setColumnDefinitions(getColumns());
			tableParamters.setTableName("Students");
			data.createTable(tableParamters);
			Assert.fail("Created an existing table");
		} catch (ColumnAlreadyExistsException | TableAlreadyExistsException | InvalidDateFormatException
				| IOException e) {

		} catch (final Exception e) {
			Assert.fail("Unexpected Exception was thrown");
		}
	}

	@Test
	public void test5InsertInto() {
		try {
			setActiveDatabase("college");
			data.insertInto(getInsertionParameters("Students"));
		} catch (ColumnAlreadyExistsException | RepeatedColumnException | ColumnListTooLargeException
				| ColumnNotFoundException | ValueListTooLargeException | ValueListTooSmallException
				| TableNotFoundException | TypeMismatchException | InvalidDateFormatException | IOException e) {
			e.printStackTrace();
			Assert.fail("Failed to insert given data");
		}
	}

	@Test
	public void test6SelectFrom() {
		try {
			setActiveDatabase("college");
			final SelectQueryOutput output = data.selectFrom(getSelectionParameters("Students"));
			final ArrayList<ColumnIdentifier> cols = new ArrayList<>();
			cols.add(new ColumnIdentifier("ID", "INTEGER"));
			final ArrayList<ArrayList<String>> actual = output.getData();
			final ArrayList<ArrayList<String>> expected= new ArrayList<>();
			final ArrayList<String> values1 = new ArrayList<>();
			values1.add("1");
			final ArrayList<String> values2 = new ArrayList<>();
			values2.add("2");
			expected.add(values1);
			expected.add(values2);
			Assert.assertEquals(cols, output.getColumns());
			Assert.assertEquals(expected, actual);
		} catch (ColumnNotFoundException | TypeMismatchException | TableNotFoundException | ColumnAlreadyExistsException
				| RepeatedColumnException | ColumnListTooLargeException | ValueListTooLargeException
				| ValueListTooSmallException | InvalidDateFormatException | IOException e) {
			Assert.fail("False output");
		}

	}

	@Test
	public void test7AddTableColumn() {
		try {
			setActiveDatabase("college");
			final AddColumnParameters parameters = new AddColumnParameters();
			parameters.setColumnIdentifier(new ColumnIdentifier("Grade", "INTEGER"));
			parameters.setTableName("Students");
			data.addTableColumn(parameters);
		} catch (ColumnAlreadyExistsException | TableNotFoundException | RepeatedColumnException
				| ColumnListTooLargeException | ColumnNotFoundException | ValueListTooLargeException
				| ValueListTooSmallException | TypeMismatchException | InvalidDateFormatException | IOException e) {
			Assert.fail("Failed to add table column");
		}
	}

	@Test
	public void test8AddInvalidTableColumn() {
		try {
			setActiveDatabase("college");
			final AddColumnParameters parameters = new AddColumnParameters();
			parameters.setColumnIdentifier(new ColumnIdentifier("Invalid", "X"));
			parameters.setTableName("Students");
			data.addTableColumn(parameters);
			Assert.fail("Added a column with an invalid type");
		} catch (ColumnAlreadyExistsException | TableNotFoundException | RepeatedColumnException
				| ColumnListTooLargeException | ColumnNotFoundException | ValueListTooLargeException
				| ValueListTooSmallException | TypeMismatchException | InvalidDateFormatException | IOException e) {
		} catch (final Exception e) {

		}
	}

	@Test
	public void test9DropTableColumn() {
		try {
			setActiveDatabase("college");
			final DropColumnParameters parameters = new DropColumnParameters();
			final ArrayList<String> columns = new ArrayList<>();
			columns.add("Grade");
			parameters.setColumnList(columns);
			parameters.setTableName("Students");
			data.dropTableColumn(parameters);
		} catch (ColumnAlreadyExistsException | RepeatedColumnException | ColumnListTooLargeException
				| ColumnNotFoundException | ValueListTooLargeException | ValueListTooSmallException
				| TypeMismatchException | InvalidDateFormatException | TableNotFoundException
				| IOException | AllColumnsDroppingException e) {
			Assert.fail("Failed to drop column");
		}
	}

	@Test
	public void test9E_DropNonExistingTableColumn() {
		try {
			setActiveDatabase("college");
			setActiveDatabase("college");
			final DropColumnParameters parameters = new DropColumnParameters();
			final ArrayList<String> columns = new ArrayList<>();
			columns.add("Grade");
			parameters.setColumnList(columns);
			parameters.setTableName("Students");
			data.dropTableColumn(parameters);
			Assert.fail("Dropped a Non-existing column");
		} catch (ColumnAlreadyExistsException | RepeatedColumnException | ColumnListTooLargeException
				| ColumnNotFoundException | ValueListTooLargeException | ValueListTooSmallException
				| TypeMismatchException | InvalidDateFormatException | TableNotFoundException | IOException e) {
		} catch (final Exception e) {
			Assert.fail("Unexpected Exception was thrown");
		}
	}
	@Test
	public void test9F_DeleteFrom() {
		//		try {
		//			setActiveDatabase("college");
		//			DeletionParameters deleteParameters = new DeletionParameters();
		//			deleteParameters.setTableName("Students");
		//			InputParametersContainer parameter = new InputParametersContainer();
		//			parameter.setTableName("Students");
		//			BooleanExpression condition = new EqualsExpression(parameter);
		//			deleteParameters.setCondition(condition);
		//			data.deleteFrom(deleteParameters);
		//		} catch (ColumnNotFoundException | TypeMismatchException | TableNotFoundException | ColumnAlreadyExistsException
		//				| RepeatedColumnException | ColumnListTooLargeException | ValueListTooLargeException
		//				| ValueListTooSmallException | InvalidDateFormatException | IOException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
	}

	@Test
	public void test9G_DropTable() {
		setActiveDatabase("college");
		final TableDroppingParameters tableParameters = new TableDroppingParameters();
		tableParameters.setTableName("Students");
		try {
			data.dropTable(tableParameters);
		} catch (TableNotFoundException | FailedToDeleteTableException e) {
			Assert.fail("Failed to Drop Table");
		}
	}

	@Test
	public void test9H_DropNonExistingTable() {
		setActiveDatabase("college");
		final TableDroppingParameters tableParameters = new TableDroppingParameters();
		tableParameters.setTableName("Students");
		try {
			data.dropTable(tableParameters);
			Assert.fail("Dropped a non-existing table");
		} catch (TableNotFoundException | FailedToDeleteTableException e) {

		} catch (final Exception e) {
			Assert.fail("Unexpected Exception was thrown");
		}
	}

	@Test // number
	public void testDropDatabase() {
		try {
			final DatabaseDroppingParameters dropDBParameters = new DatabaseDroppingParameters();
			dropDBParameters.setDatabaseName("college");
			data.dropDatabase(dropDBParameters);
		} catch (DatabaseNotFoundException | FailedToDeleteDatabaseException | TableNotFoundException
				| FailedToDeleteTableException e) {
			Assert.fail("Failed to drop database");
		}
	}

	@Test // number
	public void testDropNonExistingDatabase() {
		try {
			final DatabaseDroppingParameters dropDBParameters = new DatabaseDroppingParameters();
			dropDBParameters.setDatabaseName("College");
			data.dropDatabase(dropDBParameters);
			Assert.fail("Dropped a non-existing database");
		} catch (DatabaseNotFoundException | FailedToDeleteDatabaseException | TableNotFoundException
				| FailedToDeleteTableException e) {

		} catch (final Exception e){
			Assert.fail("Unexpected Exception was thrown");
		}
	}

	private ArrayList<ColumnIdentifier> getColumns() {
		final ArrayList<ColumnIdentifier> columns = new ArrayList<>();
		columns.add(new ColumnIdentifier("ID", "INTEGER"));
		columns.add(new ColumnIdentifier("Name", "VARCHAR"));
		return columns;
	}

	private void setActiveDatabase(final String databaseName) {
		try {
			final UseParameters useParameters = new UseParameters();
			useParameters.setDatabaseName(databaseName);
			data.setActiveDatabase(useParameters);
		} catch (DatabaseNotFoundException | TableAlreadyExistsException e) {
			e.printStackTrace();
		}
	}

	private InsertionParameters getInsertionParameters(final String tableName) {
		final InsertionParameters parameters = new InsertionParameters();
		parameters.setTableName(tableName);
		final ArrayList<String> columnNames = new ArrayList<>();
		columnNames.add("ID");
		columnNames.add("Name");
		parameters.setColumns(columnNames);
		final ArrayList<ArrayList<String>> values = new ArrayList<>();
		final ArrayList<String> row1 = new ArrayList<>();
		row1.add("1");
		row1.add("'Bary'");
		final ArrayList<String> row2 = new ArrayList<>();
		row2.add("2");
		row2.add("'Hisham'");
		values.add(row1);
		values.add(row2);
		parameters.setValues(values);
		return parameters;
	}

	private SelectionParameters getSelectionParameters(final String tableName) {
		final SelectionParameters parameters = new SelectionParameters();
		final ArrayList<String> columns = new ArrayList<>();
		columns.add("ID");
		parameters.setColumns(columns);
		parameters.setTableName(tableName);
		return parameters;
	}
}
