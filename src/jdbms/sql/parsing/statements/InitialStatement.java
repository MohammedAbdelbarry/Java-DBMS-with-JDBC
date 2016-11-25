package jdbms.sql.parsing.statements;

import jdbms.sql.parsing.properties.InputParametersContainer;

public abstract class InitialStatement implements Statement {
	protected InputParametersContainer parameters
	= new InputParametersContainer();
	public InitialStatement() {
	}
	public abstract void act();
	public InputParametersContainer getParameters() {
		return parameters;
	}
}
