package io;

import org.newdawn.slick.geom.Point;

import chu.engine.Game;

public class MoveCommand implements Command {
	
	private Point target;
	
	public MoveCommand(Point point) {
		target = point;
	}
	
	public MoveCommand(float x, float y) {
		this(new Point(x, y));
	}

	@Override
	public void performAction(Unit unit) {
		double dist = Math.sqrt(Math.pow(unit.y - target.getY(), 2) + Math.pow(unit.x - target.getX(), 2));
		if(dist < Game.getDeltaSeconds() * unit.getSpeed()) {
			unit.x = target.getX();
			unit.y = target.getY();
			unit.next();
			return;
		}
		double angle = Math.atan2(target.getY() - unit.y, target.getX() - unit.x);
		unit.x += Game.getDeltaSeconds() * unit.getSpeed() * Math.cos(angle);
		unit.y += Game.getDeltaSeconds() * unit.getSpeed() * Math.sin(angle);
	}

}
