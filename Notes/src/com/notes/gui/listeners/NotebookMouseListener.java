package com.notes.gui.listeners;

import java.awt.event.MouseEvent;

import com.notes.gui.menu.NotebookPopupMenu;
import com.notes.service.ContextHolderService;

public class NotebookMouseListener extends MouseListenerWrapper {
	
	private ContextHolderService contextHolderService;
	
	public NotebookMouseListener(ContextHolderService contextHolder) {
		this.contextHolderService = contextHolder;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			NotebookPopupMenu menu = new NotebookPopupMenu(e.getComponent(), contextHolderService);
			menu.show(e.getComponent(), e.getX(), e.getY());
		}
	}

}
