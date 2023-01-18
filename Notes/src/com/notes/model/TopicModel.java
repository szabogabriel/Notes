package com.notes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.notes.serialization.SerializationProvider;

public class TopicModel extends BaseModel {

	private String name;

	private List<NoteModel> notes = new ArrayList<>();

	public TopicModel(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
		setDirty(true);
	}

	public void addNote(NoteModel note) {
		notes.add(note);
		note.addDirtyListener(this::dirtyListener);
		setDirty(true);
	}
	
	public void removeNote(NoteModel note) {
		notes.remove(note);
		setDirty(true);
	}

	public List<NoteModel> getNotes() {
		return Collections.unmodifiableList(notes);
	}

	@Override
	public String serialize(SerializationProvider provider) {
		return provider.serialize(this);
	}

	private void dirtyListener(BaseModel model) {
		if (model.isDirty()) {
			setDirty(true);
		}
	}
}
