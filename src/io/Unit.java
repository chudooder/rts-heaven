package io;

import java.util.LinkedList;
import java.util.Queue;

import org.newdawn.slick.Color;

import chu.engine.Collidable;
import chu.engine.Entity;
import chu.engine.anim.Renderer;
import chu.engine.collision.Hitbox;
import chu.engine.collision.HitboxFactory;

/**
 * Represents a unit in the game. Units take and act on
 * commands, which are stored in a command queue.
 * @author Shawn
 *
 */
public class Unit extends Entity implements Collidable {
	
	private boolean selected;
	private Queue<Command> commandQueue;
	private Command currentCommand;
	private InputMethod input;
	
	// Unit stats
	
	private float speed = 300f;
	private float health = 100f;
	private float armor = 0f;
	private float damage = 20f;
	
	public Unit(float x, float y) {
		super(x, y);
		selected = true;
		commandQueue = new LinkedList<Command>();
		
		//TODO: Beta
		input = new PlayerInput();
		
		hitbox = HitboxFactory.rectangle(6, 6);
	}
	
	public void beginStep() {
		super.beginStep();
		input.getInput(this);

		if(currentCommand == null) {
			next();
		}
		
		if(currentCommand != null)
			currentCommand.performAction(this);
	}
	
	public void render() {
		Renderer.drawSquare(x, y, 6, 0.0f, Color.red);
		
		if(selected) {
			Renderer.drawCircle(x+3, y+3, 15.0f, 12, false, Color.white);
		}
	}
	
	/**
	 * @return Whether or not the unit is selected.
	 */
	public boolean isSelected() {
		return selected;
	}
	
	/**
	 * Set whether or not the unit is selected.
	 * @param selected The new state of the unit
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	/**
	 * Sets the current command and clears the command queue.
	 * @param cmd
	 */
	public void setCommand(Command cmd) {
		currentCommand = cmd;
		commandQueue.clear();
	}
	
	/**
	 * Adds the command to the queue.
	 * @param cmd
	 */
	public void queueCommand(Command cmd) {
		commandQueue.add(cmd);
	}
	
	/**
	 * Grabs the next command.
	 */
	public void next() {
		currentCommand = commandQueue.poll();
	}
	
	/**
	 * @return The unit's current command.
	 */
	public Command getCommand() {
		return currentCommand;
	}

	/**
	 * @return The unit's speed, in pixels per second.
	 */
	public float getSpeed() {
		return speed;
	}
	
	/**
	 * @return The unit's health.
	 */
	public float getHealth() {
		return health;
	}
	
	public float getArmor() {
		return armor;
	}
	
	public float getDamage() {
		return damage;
	}

	@Override
	public void doCollisionWith(Entity entity) {
		if(entity instanceof SelectionBox) {
			setSelected(true);
		}
	}

}
