package com.notes.gui.menu;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import com.notes.gui.model.GuiNotebookModel;
import com.notes.gui.model.GuiTopicModel;
import com.notes.model.NoteModel;
import com.notes.model.TopicModel;

public class TopicPopupMenu extends JPopupMenu {

	private static final long serialVersionUID = -3327975918248078004L;

	private JMenuItem addTopicItem = new JMenuItem("Add topic");
	private JMenuItem renameTopicItem = new JMenuItem("Rename topic");
	private JMenuItem removeTopicItem = new JMenuItem("Delete topic");

	private JList<GuiTopicModel> parent;

	@SuppressWarnings("unchecked")
	public TopicPopupMenu(Component parent) {
		this.parent = (JList<GuiTopicModel>) parent;
		add(addTopicItem);
		addTopicItem.addActionListener(this::addTopic);
		if (this.parent.getSelectedValue() != null) {
			add(renameTopicItem);
			renameTopicItem.addActionListener(this::renameTopic);
			if (this.parent.getModel().getSize() > 1) {
				add(removeTopicItem);
				removeTopicItem.addActionListener(this::removeTopic);
			}
		}
	}

	private void addTopic(ActionEvent e) {
		String topic = JOptionPane.showInputDialog(parent, "Enter the name of the new topic");
		if (topic != null) {
			NoteModel noteModel = new NoteModel("note");
			TopicModel topicModel = new TopicModel(topic);
			topicModel.addNote(noteModel);
			GuiNotebookModel model = (GuiNotebookModel) parent.getModel();
			model.addGuiTopicModel(new GuiTopicModel(topicModel));
		}
	}

	private void renameTopic(ActionEvent e) {
		String topicName = getSelected().getName();
		String newName = JOptionPane.showInputDialog(parent, "Enter new name for the topic " + topicName);
		if (newName != null) {
			getSelected().setName(newName);
		}
	}

	private void removeTopic(ActionEvent e) {
		String topicName = getSelected().getName();
		int currentSelected = parent.getSelectedIndex();
		if (parent.getModel().getSize() > 1) {
			int ret = JOptionPane.showConfirmDialog(parent, "Do you really want to remove the topic " + topicName + "?",
					"Remove topic " + topicName, JOptionPane.YES_NO_OPTION);
			if (ret == JOptionPane.YES_OPTION) {
				GuiNotebookModel notebookModel = (GuiNotebookModel) parent.getModel();
				notebookModel.removeTopic(topicName);
				if (currentSelected > 0) {
					parent.setSelectedIndex(currentSelected - 1);
				} else {
					if (parent.getModel().getSize() > 0) {
						parent.setSelectedIndex(0);
					}
				}
				parent.revalidate();
				parent.repaint();
			}
		} 
	}

	private GuiTopicModel getSelected() {
		return parent.getSelectedValue();
	}

}
