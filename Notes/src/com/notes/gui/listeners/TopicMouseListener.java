package com.notes.gui.listeners;

import java.awt.event.MouseEvent;

import com.notes.gui.menu.TopicPopupMenu;

public class TopicMouseListener extends MouseListenerWrapper {
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			TopicPopupMenu menu = new TopicPopupMenu(e.getComponent());
			menu.show(e.getComponent(), e.getX(), e.getY());
		}
	}

}
