package io;

/**
 * Abstract class representing an action a unit can perform.
 * @author Shawn
 *
 */
public abstract class Command {
	private Unit unit;
	
	public Command() {
		unit = null;
	}
	
	public Command(Unit unit) {
		this.unit = unit;
	}
	
	/**
	 * Sets the command's unit.
	 */
	public void setUnit(Unit u) {
		this.unit = u;
	}
	
	/**
	 * Performs an action on the command's unit.
	 */
	public abstract void doCommand();
	
	
}
