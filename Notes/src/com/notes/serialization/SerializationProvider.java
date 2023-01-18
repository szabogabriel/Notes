package com.notes.serialization;

import com.notes.model.NoteTextBoxModel;
import com.notes.model.NoteModel;
import com.notes.model.NotebookModel;
import com.notes.model.TopicModel;

public interface SerializationProvider {
	
	String serialize(NotebookModel model);
	
	String serialize(NoteTextBoxModel model);
	
	String serialize(NoteModel model);
	
	String serialize(TopicModel model);

}
