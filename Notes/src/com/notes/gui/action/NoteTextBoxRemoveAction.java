package com.notes.gui.action;

import com.notes.actionQueue.Action;
import com.notes.gui.model.GuiNoteModel;
import com.notes.gui.model.GuiNoteTextBoxModel;

public class NoteTextBoxRemoveAction implements Action {
	
	private final GuiNoteModel editor;
	private final GuiNoteTextBoxModel editorText;
	
	public NoteTextBoxRemoveAction(GuiNoteModel editor, GuiNoteTextBoxModel editorText) {
		this.editor = editor;
		this.editorText = editorText;
	}

	@Override
	public void undo() {
		editor.addNoteTextBoxModel(editorText);
	}

	@Override
	public void redo() {
		editor.removeNoteTextBoxModel(editorText);
	}

}
