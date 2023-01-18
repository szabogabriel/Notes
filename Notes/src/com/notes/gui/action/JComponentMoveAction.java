package com.notes.gui.action;

import java.awt.Point;

import com.notes.actionQueue.Action;

public class JComponentMoveAction implements Action {
	
	private final Point oldPoint;
	private final Point newPoint;
	
	private final Visible component;
	
	public JComponentMoveAction(Visible component, Point oldPoint, Point newPoint) {
		this.component = component;
		this.oldPoint = oldPoint;
		this.newPoint = newPoint;
	}

	@Override
	public void undo() {
		component.setLocation(oldPoint);
	}

	@Override
	public void redo() {
		component.setLocation(newPoint);
	}
	
}
