package com.notes.gui;

import java.util.List;
import java.util.Set;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import com.notes.actionQueue.ActionQueueHolder;
import com.notes.gui.listeners.GuiTopicModelChangeListener;
import com.notes.gui.listeners.NoteMouseListener;
import com.notes.gui.model.GuiNoteModel;
import com.notes.gui.model.GuiNoteTextBoxModel;
import com.notes.gui.model.GuiTopicModel;

public class TabComponent extends JTabbedPane {

	private static final long serialVersionUID = -8147936167271499018L;
	
	private ActionQueueHolder actionQueueHandler;

	private ChangeListener changeListener;
	
	public TabComponent(ActionQueueHolder actionQueueHandler) {
		this.actionQueueHandler = actionQueueHandler;
		changeListener = new ChangeListener(this);
	}

	public void setModel(GuiTopicModel model) {
		this.removeAll();

		model.addGuiTopicModelChangeListener(changeListener);
		List<GuiNoteModel> notes = model.getGuiNotes();

		for (GuiNoteModel it : notes) {
			addNote(it);
		}
	}

	private void addNote(GuiNoteModel it) {
		it.clearListeners();

		NoteComponent note = new NoteComponent(actionQueueHandler, it);
		note.addMouseListener(new NoteMouseListener(it, actionQueueHandler));
		it.addModelChangeListener(note);

		Set<GuiNoteTextBoxModel> noteTextBoxModels = it.getGuiTextBoxes();
		for (GuiNoteTextBoxModel it2 : noteTextBoxModels) {
			note.noteTextBoxAdded(it2);
		}

		JScrollPane scrollPane = new JScrollPane(note);
		this.addTab(it.getName(), scrollPane);
	}
	
	private void removeNote(GuiNoteModel model) {
		this.removeTabAt(getSelectedIndex());
	}

	private static class ChangeListener implements GuiTopicModelChangeListener {
		
		private TabComponent tabComponent;
		
		public ChangeListener(TabComponent tabComponent) {
			this.tabComponent = tabComponent;
		}

		@Override
		public void modelChanged(Type type, GuiTopicModel oldModel, GuiTopicModel newModel) {
			switch (type) {
			case NAME_CHANGED:
				// Should be handled in the GuiNoteModel
				break;
			case NOTE_ADDED: {
				GuiNoteModel model = newModel.findNewGuiNoteModel(oldModel);
				if (model != null) {
					tabComponent.addNote(model);
				}
			}
				break;
			case NOTE_REMOVED: {
				GuiNoteModel model = oldModel.findNewGuiNoteModel(newModel);
				if (model != null) {
					tabComponent.removeNote(model);
				}
			}
				break;
			default:
				break;
			}
		}

	}

}
