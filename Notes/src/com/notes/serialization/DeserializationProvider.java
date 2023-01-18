package com.notes.serialization;

import java.util.List;

import com.notes.model.NoteModel;
import com.notes.model.NoteTextBoxModel;
import com.notes.model.NotebookModel;
import com.notes.model.TopicModel;

public interface DeserializationProvider {
	
	List<NotebookModel> parseNotebook(String xml);
	
	List<TopicModel> parseTopic(String xml);
	
	List<NoteModel> parseNote(String xml);
	
	List<NoteTextBoxModel> parseNoteTextBox(String xml);
}
