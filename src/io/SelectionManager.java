package io;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import chu.engine.Entity;
import chu.engine.Game;
import chu.engine.KeyboardEvent;
import chu.engine.MouseEvent;

public class SelectionManager extends Entity {
	
	private Set<Unit> units;
	private SelectionBox box;
	
	public SelectionManager() {
		super(0,0);
		units = new HashSet<Unit>();
	}
	
	public void beginStep() {
		List<MouseEvent> mouseEvents = Game.getMouseEvents();
		for(MouseEvent event : mouseEvents) {
			if(event.button == 0) {
				if(event.state) {
					box = new SelectionBox(Mouse.getX(), Mouse.getY());
					stage.addEntity(box);
				} else {
					box.destroy();
					box = null;
				}
			}
		}
		
		List<KeyboardEvent> keyboardEvents = Game.getKeys();
		for(KeyboardEvent event : keyboardEvents) {
			if(event.key == Keyboard.KEY_ESCAPE) {
				for(Unit unit : units) {
					unit.setSelected(false);
				}
			}
		}
	}

	public void addUnit(Unit u) {
		units.add(u);
	}
	
	public void removeUnit(Unit u) {
		units.remove(u);
	}
}
