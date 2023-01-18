package com.notes.gui.action;

import java.awt.Dimension;
import java.awt.Point;

import com.notes.actionQueue.Action;

public class JComponentResizeAction implements Action {
	
	private final Visible component;
	private final Point oldLocation;
	private final Point newLocation;
	private final Dimension oldSize;
	private final Dimension newSize;
	
	public JComponentResizeAction(Visible component, Point oldLocation, Point newLocation, Dimension oldSize, Dimension newSize) {
		this.component = component;
		this.oldLocation = oldLocation;
		this.newLocation = newLocation;
		this.oldSize = oldSize;
		this.newSize = newSize;
	}

	@Override
	public void undo() {
		component.setSize(oldSize);
		component.setLocation(oldLocation);
	}

	@Override
	public void redo() {
		component.setSize(newSize);
		component.setLocation(newLocation);		
	}

}
