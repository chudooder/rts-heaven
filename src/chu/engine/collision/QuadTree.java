package chu.engine.collision;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import chu.engine.Collidable;
import chu.engine.Entity;

/**
 * Implements a quadtree to aid in collision detection.
 * Quadtrees recursively subdivide into quadrants to
 * reduce the amount of neighbors an object needs to
 * check for collision.
 * @author Shawn
 *
 */
public class QuadTree {
	private static int MAX_OBJECTS = 10;
	private static int MAX_LEVELS = 5;
	
	private int level;
	private List<Entity> entities;
	private Rectangle bounds;
	private QuadTree[] nodes;
	
	public QuadTree(int level, Rectangle bounds) {
		this.level = level;
		this.bounds = bounds;
		this.entities = new ArrayList<>();
		this.nodes = new QuadTree[4];
	}
	
	/**
	 * Recursively clears the tree and all its subtrees.
	 */
	public void clear() {
		entities.clear();
		for(int i=0; i<4; i++){
			if(nodes[i] != null) {
				nodes[i].clear();
				nodes[i] = null;
			}
		}
	}
	
	/**
	 * Splits the QuadTree into four subtrees.
	 */
	public void split() {
		int w = (int)(bounds.getWidth() / 2);
		int h = (int)(bounds.getHeight() / 2);
		int x = (int)bounds.getX();
		int y = (int)bounds.getY();
		
		nodes[0] = new QuadTree(level+1, new Rectangle(x+w, y, w, h));
		nodes[1] = new QuadTree(level+1, new Rectangle(x, y, w, h));
		nodes[2] = new QuadTree(level+1, new Rectangle(x, y+h, w, h));
		nodes[3] = new QuadTree(level+1, new Rectangle(x+w, y+h, w, h));
	}
	
	
	/**
	 * Find the quadrant the entity fits into.
	 * @param entity The entity to fit.
	 * @return The quadrant the entity fits into.
	 */
	private int getIndex(Entity entity) {
		Shape shape = entity.hitbox.getShape();
		float midX = bounds.getX() + bounds.getWidth()/2;
		float midY = bounds.getY() + bounds.getHeight()/2;
		boolean topQuad = shape.getMaxY() + shape.getY() < midY;
		boolean bottomQuad = shape.getY() > midY;
		if(shape.getMaxX() + shape.getX() < midX) {
			if(topQuad) {
				return 1;
			} else if(bottomQuad) {
				return 2;
			}
		} else if(shape.getX() > midX) {
			if(topQuad) {
				return 0;
			} else if(bottomQuad) {
				return 3;
			}
		}
		return -1;
	}
	
	/**
	 * Inserts an object into the QuadTree, subdividing if necessary.
	 * @param e
	 */
	
	public void insert(Entity e) {
		if(nodes[0] != null) {
			int index = getIndex(e);
			if(index != -1) {
				nodes[index].insert(e);
				return;
			}
		}
		entities.add(e);
		
		if(entities.size() > MAX_OBJECTS && level < MAX_LEVELS) {
			if(nodes[0] == null) {
				split();
			}
			int i=0;
			while(i < entities.size()) {
				int index = getIndex(entities.get(i));
				if(index != -1) {
					nodes[index].insert(entities.remove(i));
				} else {
					i++;
				}
			}
		}
	}
	
	/**
	 * @param list An empty list.
	 * @param object The object to check for collision.
	 * @return The list of objects that could potentially collide with the given object.
	 */
	public List<Entity> retrieve(List<Entity> list, Entity object) {
		int index = getIndex(object);
		if(index != -1 && nodes[0] != null) {
			nodes[index].retrieve(list, object);
		}
		list.addAll(entities);
		return list;
	}
}
