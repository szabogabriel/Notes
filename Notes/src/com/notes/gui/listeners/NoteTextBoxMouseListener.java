package com.notes.gui.listeners;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.notes.actionQueue.Action;
import com.notes.actionQueue.ActionQueueHolder;
import com.notes.gui.model.GuiNoteTextBoxModel;
import com.notes.gui.service.EditorStateType;

public class NoteTextBoxMouseListener implements MouseListener, MouseMotionListener {

	private final GuiNoteTextBoxModel editorTextModel;
	private final ActionQueueHolder actionQueueHandler;

	private Point start = null;
	private Point eventStartLocation = null;

	private Dimension eventStartSize = null;

	private EditorStateType editorState = EditorStateType.MOVE;
	
	public NoteTextBoxMouseListener(GuiNoteTextBoxModel editorTextModel, ActionQueueHolder actionQueueHandler) {
		this.editorTextModel = editorTextModel;
		this.actionQueueHandler = actionQueueHandler;
		
		editorTextModel.setEditable(false);
//		editorText.setDragEnabled(true);
	}

	/*
	 * MouseListener methods.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (e.getClickCount() == 2) {
				if (editorState == EditorStateType.MOVE) {
					editorState = EditorStateType.EDIT;
//					noteTextBoxComponent.setCursor(editorText); //TODO
					editorTextModel.setEditable(true);
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		start = new Point(e.getXOnScreen(), e.getYOnScreen());
		eventStartLocation = editorTextModel.getPosition();
		eventStartSize = editorTextModel.getSize();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			start = null;
			
			Action action = editorState.createAction(editorTextModel, eventStartLocation, eventStartSize);
			if (action != null) {
				actionQueueHandler.getActionQueue().add(action);
			}
			eventStartLocation = null;
			eventStartSize = null;
			
			if (!editorTextModel.isSelected()) {
				editorTextModel.setSelected(true);
			}
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		handleCursorChangeForResize(e);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/*
	 * MouseMotionListener methods.
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		Point current = new Point(e.getXOnScreen(), e.getYOnScreen());
		Point delta = new Point(editorTextModel.getPosition().x - (start.x - current.x),
				editorTextModel.getPosition().y - (start.y - current.y));
		editorState.mouseMove(editorTextModel, delta);
		start = current;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		handleCursorChangeForResize(e);
	}

	private void handleCursorChangeForResize(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		editorState = EditorStateType.getEditorState(x, y, editorTextModel);

		editorState.setCursor(editorTextModel);
	}
}
