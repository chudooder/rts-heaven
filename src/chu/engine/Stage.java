package chu.engine;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class Stage {
	protected TreeSet<Entity> entities;
	protected Queue<Entity> addQueue;
	protected Queue<Entity> removeQueue;
	public final String soundTrack;
	
	public Stage(String soundTrack) {
		entities = new TreeSet<Entity>(new SortByUpdate());
		addQueue = new ConcurrentLinkedQueue<Entity>();
		removeQueue = new ConcurrentLinkedQueue<Entity>();
		this.soundTrack = soundTrack;
	}
	
	public Stage() {
		this("");
	}

	public TreeSet<Entity> getAllEntities() {
		return entities;
	}
	
	public void addEntity(Entity e) {
		addQueue.add(e);
		e.willBeRemoved = false;
	}
	
	
	public void removeEntity(Entity e) {
		if(e != null) {
			e.flagForRemoval();
			if(removeQueue.contains(e)){
				return;
			}
			removeQueue.add(e);
		}
	}
	
	public void update() {
		for(Entity e : entities) {
			e.onStep();
			e.beginStep();
		}
		processAddStack();
		processRemoveStack();
	}

	public void render() {
		SortByRender comparator = new SortByRender();
		PriorityQueue<Entity> renderQueue = new PriorityQueue<Entity>(entities.size()+1, comparator);
		renderQueue.addAll(entities);
		while(!renderQueue.isEmpty()) {
			renderQueue.poll().render();
		}
	}
	
	public Entity instanceAt(int x, int y) {
		for(Entity e : entities) {
			if(e.x == x && e.y == y && !e.willBeRemoved()) return e;
		}
		return null;
	}
	
	public Entity[] allInstancesAt(int x, int y) {
		ArrayList<Entity> ans = new ArrayList<Entity>();
		for(Entity e : entities) {
			if(e.x == x && e.y == y && !e.willBeRemoved()) ans.add(e);
		}
		
		for(Entity e : addQueue) {
			if(e.x == x && e.y == y && !e.willBeRemoved()) ans.add(e);
		}
		
		Entity[] ret = new Entity[ans.size()];
		for(int i=0; i<ret.length; i++) {
			ret[i] = ans.get(i);
		}
		return ret;
	}
	
	public Collidable[] collideableAt(int x, int y) {
		ArrayList<Collidable> ans = new ArrayList<Collidable>();
		for(Entity e : entities) {
			if(e instanceof Collidable && e.x == x && e.y == y && !e.willBeRemoved()) 
				ans.add((Collidable)e);
		}
		
		for(Entity e : addQueue) {
			if(e instanceof Collidable && e.x == x && e.y == y && !e.willBeRemoved()) 
				ans.add((Collidable)e);
		}
		
		Collidable[] ret = new Collidable[ans.size()];
		for(int i=0; i<ret.length; i++) {
			ret[i] = ans.get(i);
		}
		return ret;
	}
	
	public void processAddStack() {
		while(!addQueue.isEmpty()) {
			Entity e = addQueue.poll();
			entities.add(e);
			e.stage = this;
			e.init();
		}
	}
	
	public boolean willBeRemoved(Entity e) {
		return removeQueue.contains(e);
	}
	
	public void processRemoveStack() {
		while(!removeQueue.isEmpty()) {
			Entity e = removeQueue.poll();
			entities.remove(e);
			addQueue.remove(e);		//Otherwise some weird shit happens and entities get stuck in limbo
		}
	}

	public abstract void beginStep();

	public abstract void onStep();
	
	public abstract void endStep();
	
}
