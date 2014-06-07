package io;

import java.util.LinkedList;
import java.util.Queue;

import chu.engine.Entity;

/**
 * Represents a unit in the game. Units take and act on
 * commands, which are stored in a command queue.
 * @author Shawn
 *
 */
public abstract class Unit extends Entity {
	
	private boolean selected;
	private Queue<Command> commandQueue;
	private Command currentCommand;
	
	public Unit(float x, float y) {
		super(x, y);
		commandQueue = new LinkedList<Command>();
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public void addCommand(Command cmd) {
		commandQueue.add(cmd);
		cmd.setUnit(this);
	}
	
	public Command getCommand() {
		return currentCommand;
	}

}
