package chu.engine;

import chu.engine.anim.Sprite;
import chu.engine.collision.Hitbox;

public abstract class Entity implements Comparable<Entity> {
	
	public float x;
	public float y;
	public float width;
	public float height;
	public float prevX;
	public float prevY;
	public int updatePriority;
	public float renderDepth;
	public Sprite sprite;
	public Stage stage;
	public boolean willBeRemoved;
	public boolean solid;
	
	public Entity(float x, float y) {
		this.x = x;
		this.y = y;
		this.prevX = x;
		this.prevY = y;
		willBeRemoved = false;
		solid = false;
		width = 0;
		height = 0;
	}
	
	public void init() {
	
	}
	
	public void onStep() {
		if(sprite != null) sprite.update();
		prevX = x;
		prevY = y;
	}
	
	public void beginStep() {
		
	}
	
	public void endStep() {
		
	}
	
	public void render() {
		if(sprite != null) sprite.render(x, y, renderDepth);
	}
	
	//Called when the entity is removed from the stage.
	public void destroy() {
		if(stage == null) {
			flagForRemoval();
		} else {
			stage.removeEntity(this);
		}
	}
	
	//Lower numbers = higher priority.
	public int compareTo(Entity e) {
		return updatePriority - e.updatePriority;
	}
	
	public boolean willBeRemoved() {
		return willBeRemoved;
	}
	
	public void flagForRemoval() {
		willBeRemoved = true;
	}
	
}
