package jdbms.sql.parsing.parser;

import jdbms.sql.data.SQLData;
import jdbms.sql.errors.ErrorHandler;
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
import jdbms.sql.parsing.statements.InitialStatement;
import jdbms.sql.parsing.statements.util.InitialStatementFactory;

public class Parser {

	public Parser() {

	}
	public void parse(final String normalizedInput, final SQLData data) {
		boolean correctSyntax = false;
		for (final String key : InitialStatementFactory.
				getInstance().getRegisteredStatements()) {
			final InitialStatement statement =
					InitialStatementFactory.
					getInstance().createStatement(key);
			boolean interpreted;
			try {
				interpreted = statement.interpret(normalizedInput);
			} catch (final Exception e) {
				ErrorHandler.printSyntaxError();
				break;
			}
			correctSyntax = correctSyntax || interpreted;
			if (interpreted) {
				try {
					statement.act(data);
					if (statement.getQueryOutput() != null) {
						statement.getQueryOutput().printOutput();
						System.out.println("Query Completed Successfully");
					} else {
						System.out.println("Update Completed Successfully");
					}
					System.out.println(statement.getNumberOfUpdates());
				} catch (final ColumnNotFoundException e) {
					ErrorHandler.
					printColumnNotFoundError(e.getMessage());
				} catch (final TypeMismatchException e) {
					ErrorHandler.
					printTypeMismatchError();
				} catch (final TableNotFoundException e) {
					ErrorHandler.
					printTableNotFoundError(e.getMessage());
				} catch (final ColumnAlreadyExistsException e) {
					ErrorHandler.
					printColumnAlreadyExistsError(e.getMessage());
				} catch (final RepeatedColumnException e) {
					ErrorHandler.
					printRepeatedColumnError();
				} catch (final ColumnListTooLargeException e) {
					ErrorHandler.
					printColumnListTooLargeError();
				} catch (final ValueListTooLargeException e) {
					ErrorHandler.
					printValueListTooLargeError();
				} catch (final ValueListTooSmallException e) {
					ErrorHandler.
					printValueListTooSmallError();
				} catch (final TableAlreadyExistsException e) {
					ErrorHandler.
					printTableAlreadyExistsError(e.getMessage());
				} catch (final DatabaseAlreadyExistsException e) {
					ErrorHandler.
					printDatabaseAlreadyExistsError(e.getMessage());
				} catch (final DatabaseNotFoundException e) {
					ErrorHandler.
					printDatabaseNotFoundError(e.getMessage());
				} catch (final FailedToDeleteDatabaseException e) {
					ErrorHandler.
					printFailedToDeleteDatabase(e.getMessage());
				} catch (final FailedToDeleteTableException e) {
					ErrorHandler.
					printFailedToDeleteTable(e.getMessage());
				} catch (final InvalidDateFormatException e) {
					ErrorHandler.printInvalidDateError(e.getMessage());
				}  catch (final Exception e) {
					ErrorHandler.
					printInternalError();
					break;
				}
			}
		}
		if (!correctSyntax) {
			ErrorHandler.printSyntaxError();
		}
	}
}
