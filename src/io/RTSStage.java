package io;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import chu.engine.Collidable;
import chu.engine.Entity;
import chu.engine.Stage;
import chu.engine.anim.Renderer;
import chu.engine.collision.QuadTree;

public class RTSStage extends Stage {
	
	private Texture t;
	private SelectionManager manager;
	
	public RTSStage() {
		super();
		try {
			t = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/img/bead.png"));
		} catch (IOException e) {
			
		}
		manager = new SelectionManager();
		addEntity(new Unit(100, 100));
		addEntity(manager);
	}

	@Override
	public void beginStep() {
		for (Entity e : entities) {
			e.beginStep();
		}
		processAddStack();
		processRemoveStack();
	}

	@Override
	public void onStep() {
		for (Entity e : entities) {
			e.onStep();
		}
		processAddStack();
		processRemoveStack();
		processCollisions();
	}

	@Override
	public void endStep() {
		for (Entity e : entities) {
			e.endStep();
		}
		processAddStack();
		processRemoveStack();
	}
	
	public void render() {
		Renderer.render(t, 0, 0, 1, 1, 100, 100, 200, 200, 0.0f);
		super.render();
	}
	

	private void processCollisions() {
		//Quad Tree implementation
		//*
		QuadTree qt = new QuadTree(0, new Rectangle(0, 0, 1280, 720));
		for(Entity e : entities) {
			if(e.hitbox != null) {
				qt.insert(e);
			}
		}
		
		for(Entity e : entities) {
			if(e instanceof Collidable && e.hitbox != null) {
				List<Entity> list = new ArrayList<Entity>();
				qt.retrieve(list, e);
				for(Entity other : list) {
					if(e != other && other.hitbox.intersects(e.hitbox)) {
						System.out.println(e + " " + other);
						((Collidable)e).doCollisionWith(other);
					}
				}
			}
		}
		//*/
		//Naive implementation
		
		/*
		for(Entity e : entities) {
			for(Entity other : entities) {
				if(e instanceof Collidable && e.hitbox != null && other.hitbox != null 
						&& e != other && e.hitbox.intersects(other.hitbox)) {
					System.out.println(e + " " + other);
					((Collidable)e).doCollisionWith(other);
				}
			}
		}
		//*/
	}
	
	/**
	 * When an unit is added to this stage, also add it to the
	 * stage's selection manager.
	 */
	@Override
	public void addEntity(Entity e) {
		super.addEntity(e);
		if(e instanceof Unit) {
			manager.addUnit((Unit)e);
		}
	}

}
