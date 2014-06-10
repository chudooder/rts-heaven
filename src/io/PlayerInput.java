package io;

import java.util.List;

import org.lwjgl.input.Keyboard;

import chu.engine.Game;
import chu.engine.MouseEvent;

/**
 * Gets unit commands from a keyboard and mouse.
 * @author Shawn
 *
 */
public class PlayerInput implements InputMethod {

	@Override
	public void getInput(Unit unit) {
		if(!unit.isSelected()) return;
		boolean queued = false;
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			queued = true;
		}

		List<MouseEvent> mouseEvents = Game.getMouseEvents();
		for(MouseEvent m : mouseEvents) {
			if(m.button == 1 && m.state) {
				addCommand(unit, queued, new MoveCommand(m.x, m.y));
			}
		}

	}
	
	private void addCommand(Unit unit, boolean queued, Command cmd) {
		if(queued) {
			unit.queueCommand(cmd);
		} else {
			unit.setCommand(cmd);
		}
	}

}
