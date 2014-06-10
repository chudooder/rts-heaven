package io;

/**
 * Abstract class representing an action a unit can perform.
 * @author Shawn
 *
 */
public interface Command {
	/**
	 * Performs an action on the given unit.
	 */
	public void performAction(Unit unit);
	
}
