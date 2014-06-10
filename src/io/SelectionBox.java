package io;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;

import chu.engine.Collidable;
import chu.engine.Entity;
import chu.engine.anim.Renderer;
import chu.engine.collision.Hitbox;
import chu.engine.collision.HitboxFactory;

/**
 * Manages a box which will select units
 * @author Shawn
 *
 */
public class SelectionBox extends Entity {
	private Point start;
	private Point end;
	private Rectangle box;
	
	public SelectionBox(float x, float y) {
		super(x, y);
		this.start = new Point(x, y);
		end = new Point(x, y);
		renderDepth = 0.0f;
		box = new Rectangle(x, y, 0, 0);
		hitbox = new Hitbox(box);
	}
	
	public void onStep() {
		super.onStep();
		// Negative widths/heights don't seem to work, so
		// we have to change x and y around to compensate
		end.setX(Mouse.getX());
		end.setY(Mouse.getY());
		float width = getWidth();
		float height = getHeight();
		if(width < 0) {
			box.setX(start.getX() + width);
		} else {
			box.setX(start.getX());
		}
		if(height < 0) {
			box.setY(start.getY() + height);
		} else {
			box.setY(start.getY());
		}
		box.setWidth(Math.abs(width));
		box.setHeight(Math.abs(height));
	}
	
	public float getWidth() {
		return end.getX() - start.getX();
	}
	
	public float getHeight() {
		return end.getY() - start.getY();
	}
	
	public void render() {
		Renderer.drawLine(start.getX(), start.getY(), start.getX(), end.getY(), 1, renderDepth, Color.green, Color.green);
		Renderer.drawLine(start.getX(), end.getY(), end.getX(), end.getY(), 1, renderDepth, Color.green, Color.green);
		Renderer.drawLine(end.getX(), end.getY(), end.getX(), start.getY(), 1, renderDepth, Color.green, Color.green);
		Renderer.drawLine(end.getX(), start.getY(), start.getX(), start.getY(), 1, renderDepth, Color.green, Color.green);
	}
	
}
