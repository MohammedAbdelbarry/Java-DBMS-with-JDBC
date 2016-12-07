package jdbms.sql.parsing.statements;


import jdbms.sql.data.SQLData;
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
import jdbms.sql.parsing.properties.InputParametersContainer;

/**
 * The Class Initial Statement Class.
 */
public abstract class InitialStatement
implements Statement {

	protected InputParametersContainer parameters
	= new InputParametersContainer();

	/**
	 * Instantiates a new initial statement.
	 */
	public InitialStatement() {
	}

	/**
	 * Act, performs the sql command using the data paramaters.
	 * @param data the data
	 * @throws ValueListTooSmallException
	 * @throws ValueListTooLargeException
	 * @throws ColumnListTooLargeException
	 * @throws RepeatedColumnException
	 * @throws ColumnAlreadyExistsException
	 * @throws TableNotFoundException
	 * @throws TypeMismatchException
	 * @throws ColumnNotFoundException
	 * @throws TableAlreadyExistsException
	 * @throws DatabaseAlreadyExistsException
	 * @throws DatabaseNotFoundException
	 * @throws FailedToDeleteDatabaseException
	 * @throws FailedToDeleteTableException
	 * @throws InvalidDateFormatException
	 */
	public abstract void act(SQLData data)
			throws ColumnNotFoundException,
			TypeMismatchException,
			TableNotFoundException,
			ColumnAlreadyExistsException,
			RepeatedColumnException,
			ColumnListTooLargeException,
			ValueListTooLargeException,
			ValueListTooSmallException,
			TableAlreadyExistsException,
			DatabaseAlreadyExistsException,
			DatabaseNotFoundException,
			FailedToDeleteDatabaseException,
			FailedToDeleteTableException,
			InvalidDateFormatException;
	/**
	 * Gets the parameters.
	 * @return the parameters
	 */
	public InputParametersContainer getParameters() {
		return parameters;
	}
}
