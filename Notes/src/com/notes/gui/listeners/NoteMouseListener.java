package com.notes.gui.listeners;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.notes.actionQueue.Action;
import com.notes.actionQueue.ActionQueueHolder;
import com.notes.gui.action.NoteTextBoxAddedAction;
import com.notes.gui.model.GuiNoteModel;
import com.notes.gui.model.GuiNoteTextBoxModel;
import com.notes.model.NoteTextBoxModel;

public class NoteMouseListener implements MouseListener {
	
	private final GuiNoteModel model;
	
	private final ActionQueueHolder actionQueueHandler;
	
	public NoteMouseListener(GuiNoteModel model, ActionQueueHolder actionQueueHandler) {
		this.model = model;
		this.actionQueueHandler = actionQueueHandler;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			switch (e.getClickCount()) {
			case 1:
				for (GuiNoteTextBoxModel component : model.getGuiTextBoxes()) {
					component.setEditable(false);
					component.setSelected(false);
				}
				break;
			case 2:
				Point mousePoint = e.getPoint();
				GuiNoteTextBoxModel noteTextBoxModel = new GuiNoteTextBoxModel(new NoteTextBoxModel(mousePoint, new Dimension(300, 200), ""));
				Action action = new NoteTextBoxAddedAction(model, noteTextBoxModel);
				action.redo();
				actionQueueHandler.getActionQueue().add(action);
				break;
			}
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
