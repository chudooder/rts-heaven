package chu.engine.collision;

import org.newdawn.slick.geom.*;

public class Hitbox {
	
	private Shape shape;
	
	public Hitbox(Shape shape) {
		this.shape = shape;
	}
	
	/**
	 * @return The shape of this hitbox.
	 */
	public Shape getShape() {
		return shape;
	}
	
	public void setLocation(float x, float y) {
		shape.setX(x);
		shape.setY(y);
	}
	
	
	/**
	 * @param other The other hitbox to check.
	 * @return Whether the two hitboxes intersect.
	 */
	public boolean intersects(Hitbox other) {
		return shape.intersects(other.getShape());
	}
}
