package com.notes.gui.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.notes.actionQueue.Action;
import com.notes.actionQueue.ActionQueueHolder;
import com.notes.gui.action.NoteTextBoxRemoveAction;
import com.notes.gui.model.GuiNoteModel;
import com.notes.gui.model.GuiNoteTextBoxModel;

public class NoteTextBoxKeyListener implements KeyListener {

	private final GuiNoteModel editor;
	private final GuiNoteTextBoxModel editorText;

	private ActionQueueHolder actionQueueHandler;

	public NoteTextBoxKeyListener(GuiNoteModel editor, GuiNoteTextBoxModel editorText,
			ActionQueueHolder actionQueueHandler) {
		this.editor = editor;
		this.editorText = editorText;
		this.actionQueueHandler = actionQueueHandler;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_DELETE && editorText.isSelected() && !editorText.isEditable()) {
			Action action = new NoteTextBoxRemoveAction(editor, editorText);
			action.redo();
			actionQueueHandler.getActionQueue().add(action);
			e.consume();
		}
	}

}
