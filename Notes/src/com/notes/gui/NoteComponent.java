package com.notes.gui;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;

import com.notes.actionQueue.ActionQueueHolder;
import com.notes.gui.listeners.GuiNoteModelChangeListener;
import com.notes.gui.listeners.NoteTextBoxKeyListener;
import com.notes.gui.listeners.NoteTextBoxMouseListener;
import com.notes.gui.model.GuiNoteModel;
import com.notes.gui.model.GuiNoteTextBoxModel;

public class NoteComponent extends JComponent implements GuiNoteModelChangeListener {

	private static final long serialVersionUID = -9065224349341927894L;
	
	private ActionQueueHolder actionQueueHandler;
	private GuiNoteModel model;
	
	public NoteComponent(ActionQueueHolder actionQueueHandler, GuiNoteModel model) {
		this.actionQueueHandler = actionQueueHandler;
		this.model = model;
		model.addModelChangeListener(this);
	}
	
	@Override
	public void noteTextBoxAdded(GuiNoteTextBoxModel model) {
		NoteTextBoxComponent noteTextBox = new NoteTextBoxComponent(actionQueueHandler, model);
		
		NoteTextBoxMouseListener mouseListener = new NoteTextBoxMouseListener(model, actionQueueHandler);
		noteTextBox.addMouseListener(mouseListener);
		noteTextBox.addMouseMotionListener(mouseListener);
		
		NoteTextBoxKeyListener keyListener = new NoteTextBoxKeyListener(this.model, model, actionQueueHandler);
		noteTextBox.addKeyListener(keyListener);
		
		model.addModelChangeListener(noteTextBox);
		this.add(noteTextBox);
		revalidate();
		repaint();
	}

	@Override
	public void noteTextBoxRemoved(GuiNoteTextBoxModel model) {
		NoteTextBoxComponent noteTextBox = getTextBoxToModel(model);
		if (noteTextBox != null) {
			remove(noteTextBox);
		}
		revalidate();
		repaint();
	}
	
	@Override
	public void noteNameChanged(GuiNoteModel model) {
		JTabbedPane parent = findTabbedPaneParent();
		parent.setTitleAt(parent.getSelectedIndex(), model.getName());
	}
	
	private JTabbedPane findTabbedPaneParent() {
		Component tmp = this;
		
		while (!(tmp instanceof TabComponent) && (tmp != null)) {
			tmp = tmp.getParent();
		}
		
		return (JTabbedPane)tmp;
	}

	private NoteTextBoxComponent getTextBoxToModel(GuiNoteTextBoxModel model) {
		Component[] components = getComponents();
		for (Component it : components) {
			if (it.getLocation().equals(model.getPosition()) && it.getSize().equals(model.getSize())) {
				if (it instanceof NoteTextBoxComponent) {
					NoteTextBoxComponent noteTextBox = (NoteTextBoxComponent)it;
					if (noteTextBox.getText().equals(model.getContent())) {
						return noteTextBox;
					}
				}
			}
		}
		return null;
	}

}
