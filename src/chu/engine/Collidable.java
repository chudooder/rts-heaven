package chu.engine;

import chu.engine.collision.Hitbox;

public interface Collidable {
	/**
	 * Method that deals with any collisions with the entities in
	 * the array.
	 * @param e
	 * @return
	 */

	public void doCollisionWith(Entity entity);
}
