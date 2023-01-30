package com.notes.service.gui;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JList;

import com.notes.gui.model.GuiAppModel;
import com.notes.gui.model.GuiNoteModel;
import com.notes.gui.model.GuiNotebookModel;
import com.notes.gui.model.GuiTopicModel;
import com.notes.model.AppModel;
import com.notes.model.NoteModel;
import com.notes.model.NotebookModel;
import com.notes.model.TopicModel;
import com.notes.service.ContextHolderService;

public class NotebookService {
	
	private ContextHolderService contextHolder;
	private JList<GuiNotebookModel> parent;
	
	public NotebookService(JList<GuiNotebookModel> parent, ContextHolderService contextHolder) {
		this.parent = parent;
		this.contextHolder = contextHolder;
	}
	
	public void openNotebook(ActionEvent e) {
		JFileChooser fc = new JFileChooser();
		int result = fc.showOpenDialog(parent);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selected = fc.getSelectedFile();
			if (selected.isFile()) {
				contextHolder.loadNotebook(selected);
			}
		}
	}
	
	public void createNotebook(ActionEvent e) {
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
	
	public void saveNotebook(ActionEvent e) {
		AppModel appModel = contextHolder.getAppModel();
		String currentlyVisibleNotebook = contextHolder.getCurrentContext();
		GuiNotebookModel selectedNotebook = appModel.getNotebooks().stream()
				.filter(f -> f.getName().equals(currentlyVisibleNotebook))
				.map(f -> (GuiNotebookModel) f)
				.findAny().orElse(null);
		
		if (selectedNotebook.isDirty()) {
			contextHolder.saveNotebook(selectedNotebook);
			appModel.clearDirty();
		}
	}
	
	public void renameNotebook(ActionEvent e) {
		//TODO
	}
	
	public void removeNotebook(ActionEvent e) {
		//TODO
	}

}
