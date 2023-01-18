package com.notes.gui.action;

import com.notes.actionQueue.Action;
import com.notes.gui.model.GuiNoteModel;
import com.notes.gui.model.GuiNoteTextBoxModel;

public class NoteTextBoxAddedAction implements Action {

	private GuiNoteModel editor;
	private GuiNoteTextBoxModel addedComponent;
	
	public NoteTextBoxAddedAction(GuiNoteModel editor, GuiNoteTextBoxModel addedComponent) {
		this.editor = editor;
		this.addedComponent = addedComponent;
	}
	
	@Override
	public void undo() {
		editor.removeNoteTextBoxModel(addedComponent);
	}

	@Override
	public void redo() {
		editor.addNoteTextBoxModel(addedComponent);
	}

}
