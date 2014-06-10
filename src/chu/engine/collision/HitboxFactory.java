package chu.engine.collision;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;

/**
 * Factory for hitboxes, duh
 * @author Shawn
 *
 */
public class HitboxFactory {
	public static Hitbox rectangle(float width, float height) {
		return new Hitbox(new Rectangle(0, 0, width, height));
	}
	
	public static Hitbox circle(float radius) {
		return new Hitbox(new Circle(0, 0, radius));
	}
}
