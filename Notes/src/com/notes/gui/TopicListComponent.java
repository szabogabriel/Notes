package com.notes.gui;

import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.ListSelectionModel;

import com.notes.gui.listeners.TopicMouseListener;
import com.notes.gui.model.GuiTopicModel;

public class TopicListComponent extends JList<GuiTopicModel> {

	private static final long serialVersionUID = -1850271911162842143L;
	
	private static final int PREFERRED_HEIGHT_WINDOW = 768;
	private static final int PREFERRED_WIDTH_SCROLL = 180;
	
	public TopicListComponent() {
		setPreferredSize(new Dimension(PREFERRED_WIDTH_SCROLL, PREFERRED_HEIGHT_WINDOW));
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		addMouseListener(new TopicMouseListener());
	}

}
