package com.notes.gui.menu;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.notes.gui.model.GuiNotebookModel;
import com.notes.service.ContextHolderService;
import com.notes.service.gui.NotebookService;

public class NotebookPopupMenu extends JPopupMenu {

	private static final long serialVersionUID = 3958557592508676480L;
	
	private JMenuItem openNotebookItem = new JMenuItem("Open notebook");
	private JMenuItem createNotebookItem = new JMenuItem("Create notebook");
	private JMenuItem renameNotebookItem = new JMenuItem("Rename notebook");
	private JMenuItem removeNotebookItem = new JMenuItem("Delete notebook");

	private JList<GuiNotebookModel> parent;
	
	private NotebookService notebookService;
	
	@SuppressWarnings("unchecked")
	public NotebookPopupMenu(Component parent, ContextHolderService contextHolder) {
		notebookService = new NotebookService((JList<GuiNotebookModel>)parent, contextHolder);
		this.parent = (JList<GuiNotebookModel>)parent;
		
		add(openNotebookItem);
		openNotebookItem.addActionListener(notebookService::openNotebookListener);
		
		add(createNotebookItem);
		createNotebookItem.addActionListener(notebookService::createNotebookListener);
		
		if (this.parent.getSelectedValue() != null) {
			add(renameNotebookItem);
			renameNotebookItem.addActionListener(notebookService::renameNotebookListener);
			
			if (this.parent.getModel().getSize() > 1) {
				add(removeNotebookItem);
				removeNotebookItem.addActionListener(notebookService::removeNotebookItem);
			}
		}
	}
	
}
