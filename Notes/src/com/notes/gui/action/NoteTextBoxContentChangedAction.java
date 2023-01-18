package com.notes.gui.action;

import com.notes.actionQueue.Action;
import com.notes.gui.NoteTextBoxComponent;

/*
 * This is a terrible approach to do text-based actions. Not yet used. 
 */
public class NoteTextBoxContentChangedAction implements Action {
	
	private NoteTextBoxComponent editorText;
	private String oldContent;
	private String newContent;
	
	public NoteTextBoxContentChangedAction(String oldContent, NoteTextBoxComponent editorText) {
		this.editorText = editorText;
		this.oldContent = oldContent;
		this.newContent = editorText.getText();
	}

	@Override
	public void undo() {
		editorText.setText(oldContent);
	}

	@Override
	public void redo() {
		editorText.setText(newContent);
	}

}
