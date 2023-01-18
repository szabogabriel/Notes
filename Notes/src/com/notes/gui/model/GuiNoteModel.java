package com.notes.gui.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.notes.gui.listeners.GuiNoteModelChangeListener;
import com.notes.model.NoteModel;

public class GuiNoteModel extends NoteModel {
	
	private List<GuiNoteModelChangeListener> changeListeners = new ArrayList<>();

	public GuiNoteModel(String name) {
		super(name);
	}
	
	public void setName(String name) {
		super.setName(name);
		changeListeners.stream().forEach(e -> e.noteNameChanged(this));
	}
	
	public void addNoteTextBoxModel(GuiNoteTextBoxModel model) {
		super.addNoteTextBoxModel(model);
		changeListeners.stream().forEach(e -> e.noteTextBoxAdded(model));
	}
	
	public void removeNoteTextBoxModel(GuiNoteTextBoxModel model) {
		super.removeNoteTextBoxModel(model);
		changeListeners.stream().forEach(e -> e.noteTextBoxRemoved(model));
	}
	
	public GuiNoteModel(NoteModel model) {
		this(model.getName());
		model.getTextBoxes().stream().map(GuiNoteTextBoxModel::new).forEach(this::addNoteTextBoxModel);
	}
	
	public Set<GuiNoteTextBoxModel> getGuiTextBoxes() {
		return super.getTextBoxes().stream().map(e -> (GuiNoteTextBoxModel)e).collect(Collectors.toSet());
	}
	
	public void addModelChangeListener(GuiNoteModelChangeListener listener) {
		changeListeners.add(listener);
	}
	
	public void removeModelChangeListener(GuiNoteModelChangeListener listener) {
		changeListeners.remove(listener);
	}
	
	public void clearListeners() {
		changeListeners.clear();
	}

}
