package com.notes.gui.action;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;

public interface Visible {
	
	void setLocation(Point point);
	
	void setSize(Dimension dimension);
	
	Point getLocation();
	
	Dimension getSize();
	
	void setCursor(Cursor cursor);
	
}
