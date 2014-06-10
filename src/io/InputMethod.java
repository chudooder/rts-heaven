package io;


/**
 * Represents
 * @author Shawn
 *
 */
public interface InputMethod {
	/**
	 * Get the next command(s) for this unit.
	 * @param unit
	 * @return
	 */
	public void getInput(Unit unit);
}
