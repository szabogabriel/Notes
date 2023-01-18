package com.notes.gui.model;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.notes.gui.action.Visible;
import com.notes.gui.listeners.GuiNoteTextBoxModelChangeListener;
import com.notes.model.NoteTextBoxModel;

public class GuiNoteTextBoxModel extends NoteTextBoxModel implements Visible {

	// Attributes from NoteTextBoxModel
	public static final int ATTRIBUTE_POSITION = 1;
	public static final int ATTRIBUTE_SIZE = 2;
	public static final int ATTRIBUTE_CONTENT = 3;
	
	// Local attributes
	public static final int ATTRIBUTE_EDITABLE = 100;
	public static final int ATTRIBUTE_SELECTED = 101;
	public static final int ATTRIBUTE_CURSOR = 102;
	
	private List<GuiNoteTextBoxModelChangeListener> changeListeners = new ArrayList<>();
	
	private boolean editable = false;
	private boolean selected = false;
	
	private Cursor cursor = null;
	
	public GuiNoteTextBoxModel(NoteTextBoxModel model) {
		super(model.getPosition(), model.getSize(), model.getContent());
	}
	
	public void setEditable(boolean editable) {
		this.editable = editable;
		castAttributeChangedEvent(ATTRIBUTE_EDITABLE);
	}
	
	public boolean isEditable() {
		return editable;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
		castAttributeChangedEvent(ATTRIBUTE_SELECTED);
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public void setCursor(Cursor cursor) {
		this.cursor = cursor;
		castAttributeChangedEvent(ATTRIBUTE_CURSOR);
	}
	
	public Cursor getCursor() {
		return cursor;
	}
	
	@Override
	public void setContent(String content) {
		super.setContent(content);
		castAttributeChangedEvent(ATTRIBUTE_CONTENT);
	}
	
	public void addModelChangeListener(GuiNoteTextBoxModelChangeListener listener) {
		changeListeners.add(listener);
	}
	
	public void removeModelChangeListener(GuiNoteTextBoxModelChangeListener listener) {
		changeListeners.remove(listener);
	}
	
	public void clearListeners() {
		changeListeners.clear();
	}

	private void castAttributeChangedEvent(int attribute) {
		changeListeners.stream().forEach(e -> e.attributeChanged(this, attribute));
	}

	@Override
	public void setLocation(Point point) {
		super.setPosition(point);
		castAttributeChangedEvent(ATTRIBUTE_POSITION);
	}
	
	@Override
	public void setSize(Dimension size) {
		super.setSize(size);
		castAttributeChangedEvent(ATTRIBUTE_SIZE);
	}

	@Override
	public Point getLocation() {
		return super.getPosition();
	}
}
