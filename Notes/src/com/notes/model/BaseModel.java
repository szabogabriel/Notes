package com.notes.model;

import java.util.ArrayList;
import java.util.List;

import com.notes.serialization.SerializationProvider;

public abstract class BaseModel {
	
	private boolean dirty = false;
	
	public abstract String serialize(SerializationProvider provider);
	
	private List<DirtyListener> dirtyListeners = new ArrayList<>();
	
	public void setDirty(boolean dirty) {
		this.dirty = dirty;
		dirtyListeners.stream().forEach(e -> e.dirtyStateChanged(this));
	}
	
	public boolean isDirty() {
		return dirty;
	}
	
	public void addDirtyListener(DirtyListener listener) {
		this.dirtyListeners.add(listener);
	}
	
	public void removeDirtyListener(DirtyListener listener) {
		this.dirtyListeners.remove(listener);
	}
	
}
