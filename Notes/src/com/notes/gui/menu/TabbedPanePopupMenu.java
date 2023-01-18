package com.notes.gui.menu;

import java.awt.event.ActionEvent;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;

import com.notes.gui.model.GuiNoteModel;
import com.notes.gui.model.GuiTopicModel;

public class TabbedPanePopupMenu extends JPopupMenu {

	private static final long serialVersionUID = 9094983425714350344L;

	private JMenuItem addNoteItem = new JMenuItem("Add note");
	private JMenuItem renameNoteItem = new JMenuItem("Rename note");
	private JMenuItem removeNoteItem = new JMenuItem("Delete note");

	private JTabbedPane parent;
	private GuiTopicModel model;

	public TabbedPanePopupMenu(JTabbedPane parent, GuiTopicModel model) {
		this.parent = parent;
		this.model = model;

		add(addNoteItem);
		addNoteItem.addActionListener(this::addNoteItemAction);
		add(renameNoteItem);
		renameNoteItem.addActionListener(this::renameNoteItemAction);

		if (parent.getTabCount() > 1) {
			add(removeNoteItem);
			removeNoteItem.addActionListener(this::removeNoteItemAction);
		}
	}

	private void addNoteItemAction(ActionEvent e) {
		String note = JOptionPane.showInputDialog(parent, "Enter the name of the new note");
		if (note != null) {
			GuiNoteModel guiNoteModel = new GuiNoteModel(note);
			model.addNote(guiNoteModel);
		}
	}

	private void renameNoteItemAction(ActionEvent e) {
		String noteName = parent.getTitleAt(parent.getSelectedIndex());
		String newName = JOptionPane.showInputDialog(parent, "Enter new name for the note " + noteName);

		GuiNoteModel noteModel = model.getGuiNotes().stream().filter(ex -> ex.getName().equals(noteName)).findAny()
				.orElse(null);
		if (noteModel != null && newName != null) {
			noteModel.setName(newName);
		}
	}

	private void removeNoteItemAction(ActionEvent e) {
		String noteName = parent.getTitleAt(parent.getSelectedIndex());
		if (noteName != null) {
			int ret = JOptionPane.showConfirmDialog(parent, "Do you really want to remove the note " + noteName + "?",
					"Remove note " + noteName, JOptionPane.YES_NO_OPTION);
			if (ret == JOptionPane.YES_OPTION) {
				GuiNoteModel noteModel = model.getGuiNotes().stream().filter(ex -> ex.getName().equals(noteName))
						.findAny().orElse(null);

				model.removeNote(noteModel);
			}
		}
	}
}
