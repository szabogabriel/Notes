package com.notes.model;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Objects;

import com.notes.serialization.SerializationProvider;

public class NoteTextBoxModel extends BaseModel {
	
	private Point position;
	private Dimension size;
	private String content = "";
	
	public NoteTextBoxModel(Point position, Dimension dimension, String content) {
		this.position = position;
		this.size = dimension;
		this.content = content;
	}

	public int getPositionX() {
		return (int)position.getX();
	}

	public void setPositionX(int positionX) {
		setPosition(new Point(positionX, (int)position.getY()));
		setDirty(true);
	}

	public int getPositionY() {
		return (int)position.getY();
	}

	public void setPositionY(int positionY) {
		setPosition(new Point((int)position.getX(), positionY));
		setDirty(true);
	}
	
	public Point getPosition() {
		return new Point(position);
	}
	
	public void setPosition(Point position) {
		this.position = position;
		setDirty(true);
	}

	public int getWidth() {
		return (int)size.getWidth();
	}

	public void setWidth(int width) {
		setSize(new Dimension(width, (int)size.getHeight()));
		setDirty(true);
	}

	public int getHeight() {
		return (int)size.getHeight();
	}

	public void setHeight(int height) {
		setSize(new Dimension((int)size.getWidth(), height));
		setDirty(true);
	}
	
	public Dimension getSize() {
		return new Dimension(size);
	}
	
	public void setSize(Dimension size) {
		this.size = size;
		setDirty(true);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
		setDirty(true);
	}

	@Override
	public String serialize(SerializationProvider provider) {
		return provider.serialize(this);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(getUuid());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		NoteTextBoxModel other = (NoteTextBoxModel) obj;
		return Objects.equals(getUuid(), other.getUuid());
	}
	
}
