package com.notes.gui.listeners;

import com.notes.gui.model.GuiNoteModel;
import com.notes.gui.model.GuiNoteTextBoxModel;

public interface GuiNoteModelChangeListener {
	
	void noteNameChanged(GuiNoteModel model);
	
	void noteTextBoxAdded(GuiNoteTextBoxModel model);
	
	void noteTextBoxRemoved(GuiNoteTextBoxModel model);

}
