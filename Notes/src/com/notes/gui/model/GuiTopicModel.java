package com.notes.gui.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.notes.gui.listeners.GuiTopicModelChangeListener;
import com.notes.gui.listeners.GuiTopicModelChangeListener.Type;
import com.notes.model.NoteModel;
import com.notes.model.TopicModel;

public class GuiTopicModel extends TopicModel {
	
	private Set<GuiTopicModelChangeListener> listeners = new HashSet<>();
	
	public GuiTopicModel(TopicModel model) {
		super(model.getName());
		model.getNotes().stream().map(GuiNoteModel::new).forEach(this::addNote);
	}
	
	public List<GuiNoteModel> getGuiNotes() {
		return super.getNotes().stream().map(e -> ((GuiNoteModel)e)).collect(Collectors.toList());
	}
	
	public void setName(String name) {
		GuiTopicModel old = new GuiTopicModel(this);
		super.setName(name);
		castEvent(Type.NAME_CHANGED, old);
	}

	public void addNote(NoteModel note) {
		GuiTopicModel old = new GuiTopicModel(this);
		super.addNote(note);
		castEvent(Type.NOTE_ADDED, old);
	}
	
	public void removeNote(NoteModel note) {
		GuiTopicModel old = new GuiTopicModel(this);
		super.removeNote(note);
		castEvent(Type.NOTE_REMOVED, old);
	}
	
	public GuiNoteModel findNewGuiNoteModel(GuiTopicModel oldValue) {
		List<String> oldNotes = oldValue.getGuiNotes().stream().map(e -> e.getName()).collect(Collectors.toList());
		List<GuiNoteModel> newNotes = getGuiNotes();
		for (GuiNoteModel it : newNotes) {
			if (!oldNotes.contains(it.getName())) {
				return it;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return super.getName();
	}
	
	private void castEvent(GuiTopicModelChangeListener.Type type, GuiTopicModel old) {
		listeners.forEach(e -> e.modelChanged(type, old, this));
	}
	
	public void clearListeners() {
		listeners.clear();
	}
	
	public void addGuiTopicModelChangeListener(GuiTopicModelChangeListener listener) {
		listeners.add(listener);
	}
	
	public void removeGuiTopicModelChangeListener(GuiTopicModelChangeListener listener) {
		listeners.remove(listener);
	}

}
