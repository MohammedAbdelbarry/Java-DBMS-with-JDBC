package jdbms.sql.parsing.statements;


import jdbms.sql.data.SQLData;
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
	 */
	public abstract void act(SQLData data);
	
	/**
	 * Gets the parameters.
	 * @return the parameters
	 */
	public InputParametersContainer getParameters() {
		return parameters;
	}
}
