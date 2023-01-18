package com.notes.gui.action;

import org.apache.commons.lang3.StringUtils;

import com.notes.actionQueue.Action;
import com.notes.gui.model.GuiNoteTextBoxModel;

/*
 * This class is a temporary solution. By no means optimal. Only deltas, offset and text length
 * should be stored, instead of the old and new contents.
 */
public class NoteTextBoxTextContentChangedAction implements Action {

	private final GuiNoteTextBoxModel model;
	private final String oldText;
	private String newText;

	public NoteTextBoxTextContentChangedAction(GuiNoteTextBoxModel model, String oldText, String newText) {
		this.model = model;
		this.oldText = oldText;
		this.newText = newText;
	}

	public boolean mergable(Action action) {
		if (action instanceof NoteTextBoxTextContentChangedAction) {
			NoteTextBoxTextContentChangedAction tmp = (NoteTextBoxTextContentChangedAction) action;
			String t1 = newText;
			String t2 = tmp.newText;
			String dif = StringUtils.difference(t1, t2);

			if ((t1.length() < t2.length()) && (dif.charAt(0) >= 'a' && dif.charAt(0) <= 'z'
					|| dif.charAt(0) >= 'A' && dif.charAt(0) <= 'Z' || dif.charAt(0) >= '0' && dif.charAt(0) <= '9')) {
				return true;
			}

		}
		return false;
	}

	public void merge(Action action) {
		newText = ((NoteTextBoxTextContentChangedAction)action).newText;
	}

	@Override
	public void undo() {
		model.setContent(oldText);
	}

	@Override
	public void redo() {
		model.setContent(newText);
	}

}
