package com.notes.gui.listeners;

import com.notes.gui.model.GuiTopicModel;

public interface GuiTopicModelChangeListener {
	
	enum Type {
		NAME_CHANGED,
		NOTE_ADDED,
		NOTE_REMOVED,
		;
	}
	
	void modelChanged(Type type, GuiTopicModel oldModel, GuiTopicModel newModel);

}
