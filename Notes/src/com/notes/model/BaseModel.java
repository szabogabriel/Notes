package com.notes.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.notes.serialization.SerializationProvider;

public abstract class BaseModel {
	
	private String uuid;
	
	public BaseModel() {
		uuid = UUID.randomUUID().toString();
	}
	
	public String getUuid() {
		return uuid;
	}
	
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

	@Override
	public int hashCode() {
		return Objects.hash(uuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseModel other = (BaseModel) obj;
		return Objects.equals(uuid, other.uuid);
	}
	
}
