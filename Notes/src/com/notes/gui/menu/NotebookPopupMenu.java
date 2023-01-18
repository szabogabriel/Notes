package com.notes.gui.menu;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.notes.gui.model.GuiAppModel;
import com.notes.gui.model.GuiNoteModel;
import com.notes.gui.model.GuiNotebookModel;
import com.notes.gui.model.GuiTopicModel;
import com.notes.model.NoteModel;
import com.notes.model.NotebookModel;
import com.notes.model.TopicModel;
import com.notes.service.ContextHolderService;

public class NotebookPopupMenu extends JPopupMenu {

	private static final long serialVersionUID = 3958557592508676480L;
	
	private JMenuItem openNotebookItem = new JMenuItem("Open notebook");
	private JMenuItem createNotebookItem = new JMenuItem("Create notebook");
	private JMenuItem renameNotebookItem = new JMenuItem("Rename notebook");
	private JMenuItem removeNotebookItem = new JMenuItem("Delete notebook");

	private ContextHolderService contextHolder;
	private JList<GuiNotebookModel> parent;
	
	@SuppressWarnings("unchecked")
	public NotebookPopupMenu(Component parent, ContextHolderService contextHolder) {
		this.parent = (JList<GuiNotebookModel>)parent;
		this.contextHolder = contextHolder;
		
		add(openNotebookItem);
		openNotebookItem.addActionListener(this::openNotebookListener);
		
		add(createNotebookItem);
		createNotebookItem.addActionListener(this::createNotebookListener);
		
		if (this.parent.getSelectedValue() != null) {
			add(renameNotebookItem);
			renameNotebookItem.addActionListener(this::renameNotebookListener);
			
			if (this.parent.getModel().getSize() > 1) {
				add(removeNotebookItem);
				removeNotebookItem.addActionListener(this::removeNotebookItem);
			}
		}
	}
	
	private void openNotebookListener(ActionEvent e) {
		JFileChooser fc = new JFileChooser();
		int result = fc.showOpenDialog(parent);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selected = fc.getSelectedFile();
			if (selected.isFile()) {
				contextHolder.loadNotebook(selected);
			}
		}
	}
	
	private void createNotebookListener(ActionEvent e) {
		JFileChooser fc = new JFileChooser();
		int result = fc.showSaveDialog(parent);
		if (result == JFileChooser.APPROVE_OPTION) {
			File newFile = fc.getSelectedFile();
			if (!newFile.getName().endsWith("xnotes")) {
				newFile = new File(newFile.getAbsolutePath() + ".xnotes");
			}
			String modelName = newFile.getName().split("\\\\.")[0];
			GuiNotebookModel newNotebook = new GuiNotebookModel(new NotebookModel(modelName));
			GuiTopicModel newTopic = new GuiTopicModel(new TopicModel("Default"));
			GuiNoteModel noteModel = new GuiNoteModel(new NoteModel("Default"));
			
			newTopic.addNote(noteModel);
			newNotebook.addGuiTopicModel(newTopic);
			
			GuiAppModel appModel = (GuiAppModel)parent.getModel();
			appModel.addNotebook(newNotebook, newFile);
			
			parent.setSelectedIndex(0);
		}
	}
	
	private void renameNotebookListener(ActionEvent e) {
		//TODO
	}
	
	private void removeNotebookItem(ActionEvent e) {
		//TODO
	}
}
