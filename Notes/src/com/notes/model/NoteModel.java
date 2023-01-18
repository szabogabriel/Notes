package com.notes.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.notes.serialization.SerializationProvider;

public class NoteModel extends BaseModel {
	
	private String name;
	
	private Set<NoteTextBoxModel> textBoxModels = new HashSet<>();
	
	public NoteModel(String name) {
		this.name = name;
	}
	
	public void setName(String name) {
		this.name = name;
		setDirty(true);
	}
	
	public void addNoteTextBoxModel(NoteTextBoxModel model) {
		this.textBoxModels.add(model);
		model.addDirtyListener(this::dirtyListener);
		setDirty(true);
	}
	
	public void removeNoteTextBoxModel(NoteTextBoxModel model) {
		textBoxModels.remove(model);
		setDirty(true);
	}
	
	public String getName() {
		return name;
	}
	
	public Set<NoteTextBoxModel> getTextBoxes() {
		return Collections.unmodifiableSet(textBoxModels);
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
