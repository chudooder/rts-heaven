package chu.engine.collision;

import org.newdawn.slick.geom.Shape;

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
	
	
	/**
	 * @param other The other hitbox to check.
	 * @return Whether the two hitboxes intersect.
	 */
	public boolean intersects(Hitbox other) {
		return shape.intersects(other.getShape());
	}
}
