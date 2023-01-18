package com.notes.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.notes.actionQueue.Action;
import com.notes.actionQueue.ActionQueueHolder;
import com.notes.gui.action.NoteTextBoxTextContentChangedAction;
import com.notes.gui.listeners.GuiNoteTextBoxModelChangeListener;
import com.notes.gui.model.GuiNoteTextBoxModel;

public class NoteTextBoxComponent extends JTextArea implements GuiNoteTextBoxModelChangeListener {

	private static final long serialVersionUID = -4001432337386921231L;

	private boolean eventHandling = false;
	
	public NoteTextBoxComponent(ActionQueueHolder actionQueueHandler, GuiNoteTextBoxModel model) {
		setFont(new Font("monospaced", Font.PLAIN, 12));
		setBounds(model.getPositionX(), model.getPositionY(), model.getWidth(), model.getHeight());
		setText(model.getContent());

		setLineWrap(true);
		setWrapStyleWord(true);
		getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				actionEvent();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				actionEvent();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				actionEvent();
			}

			private void actionEvent() {
				if (!isEventHandling()) {
					Action action = new NoteTextBoxTextContentChangedAction(model, model.getContent(), getText());
					action.redo();
					actionQueueHandler.getActionQueue().add(action);
				}
			}
		});
	}

	private boolean isEventHandling() {
		return eventHandling;
	}

	private void setSelected(boolean selected) {
		if (selected) {
			setBorder(BorderFactory.createLineBorder(Color.BLACK));
		} else {
			setBorder(null);
			int textLength = getText().length();
			super.select(textLength, textLength);
		}
	}
	
	public void setLocation(Point p) {
		super.setLocation(p);
	}
	
	public void setSize(Dimension d) {
		super.setSize(d);
	}
	
	@Override
	public void attributeChanged(GuiNoteTextBoxModel model, int attribute) {
		switch (attribute) {
		case GuiNoteTextBoxModel.ATTRIBUTE_EDITABLE:
			setEditable(model.isEditable());
			break;
		case GuiNoteTextBoxModel.ATTRIBUTE_SELECTED:
			setSelected(model.isSelected());
			break;
		case GuiNoteTextBoxModel.ATTRIBUTE_POSITION:
			setLocation(model.getPosition());
			break;
		case GuiNoteTextBoxModel.ATTRIBUTE_SIZE:
			setSize(model.getSize());
			break;
		case GuiNoteTextBoxModel.ATTRIBUTE_CONTENT:
			// TODO
			// Do we really need to watch this?
			eventHandling = true;
			if (!model.getContent().equals(getText())) {
				setText(model.getContent());
			}
			eventHandling = false;
			break;
		case GuiNoteTextBoxModel.ATTRIBUTE_CURSOR:
			setCursor(model.getCursor());
			break;
		}
	}

}
