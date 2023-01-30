package com.notes.gui;

import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.ListSelectionModel;

import com.notes.gui.listeners.NotebookMouseListener;
import com.notes.gui.model.GuiAppModel;
import com.notes.gui.model.GuiNotebookModel;
import com.notes.service.ContextHolderService;

public class NotebookListComponent extends JList<GuiNotebookModel> {
	
	private static final int PREFERRED_HEIGHT_WINDOW = 768;
	private static final int PREFERRED_WIDTH_SCROLL = 180;

	private static final long serialVersionUID = -744832883388819807L;

	public NotebookListComponent(GuiAppModel model, ContextHolderService contextHolderService) {
		super(model);
		setPreferredSize(new Dimension(PREFERRED_WIDTH_SCROLL, PREFERRED_HEIGHT_WINDOW));
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		addMouseListener(new NotebookMouseListener(contextHolderService));
	}
}
